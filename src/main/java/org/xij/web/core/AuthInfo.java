package org.xij.web.core;

import java.util.Map;

public interface AuthInfo {
    String getUserId();

    String getDeptId();

    String getDeptCode();

    String getRightDeptId();

    String getRightDeptCode();

    String getUserName();

    String getDeptName();

    Map<String, String> getRoles();

    boolean isSuperAdmin();
}
