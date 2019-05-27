package org.xij.web.module.sys.user;

import org.xij.web.module.base.Service;

public interface UserService extends Service<User> {
    Integer resetPassword(String id, String password);
    Integer alterPassword(String id, String oldPassword, String newPassword);
}
