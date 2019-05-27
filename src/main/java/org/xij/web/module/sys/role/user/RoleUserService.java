package org.xij.web.module.sys.role.user;

import org.xij.web.module.sys.right.Right;

import java.util.List;

public interface RoleUserService {
    List<Right> query(String roleId, String own, String account, String name);

    Integer grant(String roleId, List<String> userIds);

    Integer cancel(String roleId, List<String> userIds);
}
