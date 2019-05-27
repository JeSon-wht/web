package org.xij.web.module.sys.role.right;

import org.xij.web.module.sys.right.Right;

import java.util.List;

public interface RoleRightService {
    List<Right> query(String roleId, String own, String name, String type);

    Integer grant(String roleId, List<String> rightIds);

    Integer cancel(String roleId, List<String> rightIds);
}
