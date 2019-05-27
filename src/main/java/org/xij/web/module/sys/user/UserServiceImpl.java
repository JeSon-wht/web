package org.xij.web.module.sys.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xij.util.Id;
import org.xij.util.StringManager;
import org.xij.util.XIJException;
import org.xij.web.core.AuthContext;
import org.xij.web.core.AuthInfo;
import org.xij.web.module.base.BusinessEntity;
import org.xij.web.module.base.ServiceImpl;

@Service
public class UserServiceImpl extends ServiceImpl<User, UserMapper> implements UserService {
    public UserServiceImpl(UserMapper mapper) {
        super(mapper);
    }

    private static final StringManager MANAGER = StringManager.getManager(ServiceImpl.class);

    @Override
    public Integer resetPassword(String id, String password) {
        if (password == null) {
            password = Id.generateId();
        }
        return mapper.resetPassword(id, password);
    }

    @Override
    public Integer alterPassword(String id, String oldPassword, String newPassword) {
        User user = mapper.queryPwdById(id);
        if (!oldPassword.equals(user.getPassword())) {
            throw new RuntimeException("原密码不正确");
        }
        return mapper.resetPassword(id, newPassword);
    }

    @Override
    @Transactional
    public Integer save(User user) {
        Integer row = mapper.checkRepeat(user.getAccount());
        if (row > 0) {
            throw new RuntimeException("账号已存在!");
        }

        if (user.getId() != null) {
            XIJException.throwEx(1, MANAGER.getString("pk", new Object[]{user.getClass().getName(), user.getId()}));
        }

        user.setId(Id.generateId());
        if (user instanceof BusinessEntity) {
            BusinessEntity entity = (BusinessEntity) user;
            AuthInfo authInfo = AuthContext.get();
            entity.setCreateBy(authInfo.getUserId());
            entity.setCreateDept(authInfo.getDeptId());
        }

        return mapper.insert(user);
    }
}
