package org.xij.web.module.sys.user.role;

import org.springframework.stereotype.Service;
import org.xij.web.module.sys.role.Role;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    private UserRoleMapper mapper;

    public UserRoleServiceImpl(UserRoleMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Role> query(String userId, String own, String name) {
        return mapper.query(userId, own, name);
    }

    @Override
    public Integer grant(String userId, List<String> roleIds) {
        return mapper.insert(userId, roleIds);
    }

    @Override
    public Integer cancel(String userId, List<String> roleIds) {
        return mapper.delete(userId, roleIds);
    }
}
