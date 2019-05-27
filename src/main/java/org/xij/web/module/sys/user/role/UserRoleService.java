package org.xij.web.module.sys.user.role;

import org.xij.web.module.sys.role.Role;

import java.util.List;

public interface UserRoleService {
    List<Role> query(String userId, String own, String name);

    Integer grant(String userId, List<String> roleIds);

    Integer cancel(String userId, List<String> roleIds);
}
