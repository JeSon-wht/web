package org.xij.web.module.sys.user.right;

import org.xij.web.module.sys.right.Right;

import java.util.List;

public interface UserRightService {
    List<Right> query(String userId, String own, String name, String type);

    Integer grant(String userId, List<String> rightIds);

    Integer cancel(String userId, List<String> rightIds);
}
