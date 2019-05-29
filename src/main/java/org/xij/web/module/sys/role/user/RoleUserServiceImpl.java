package org.xij.web.module.sys.role.user;

import org.springframework.stereotype.Service;
import org.xij.web.core.AuthContext;
import org.xij.web.core.AuthInfo;
import org.xij.web.module.sys.right.Right;

import java.util.List;

@Service
public class RoleUserServiceImpl implements RoleUserService {
    private RoleUserMapper mapper;

    public RoleUserServiceImpl(RoleUserMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Right> query(String roleId, String own, String account, String name) {
        return mapper.query(roleId, own, account, name);
    }

    @Override
    public Integer grant(String roleId, List<String> userIds) {
        AuthInfo authInfo = AuthContext.get();
        return mapper.insert(roleId, userIds,authInfo.getUserId(),authInfo.getDeptId());
    }

    @Override
    public Integer cancel(String roleId, List<String> userIds) {
        return mapper.delete(roleId, userIds);
    }
}
