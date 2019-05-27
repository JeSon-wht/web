package org.xij.web.module.sys.auth;

import org.xij.annotation.Cache;
import org.xij.annotation.MybatisMapper;
import org.xij.web.module.sys.right.Right;
import org.xij.web.module.sys.user.User;

import java.util.List;

@MybatisMapper
public interface AuthMapper {
    User login(String account);

    @Cache(value = "'RoleFuncRight' + #root[0]")
    List<Right> queryRoleFuncRight(String roleId);
}
