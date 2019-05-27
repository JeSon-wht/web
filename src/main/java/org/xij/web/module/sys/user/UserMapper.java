package org.xij.web.module.sys.user;

import org.apache.ibatis.annotations.Param;
import org.xij.annotation.MybatisMapper;
import org.xij.web.module.base.Mapper;

@MybatisMapper
public interface UserMapper extends Mapper<User> {
    Integer resetPassword(@Param("id") String id, @Param("password") String password);

    User queryPwdById(String id);

    Integer checkRepeat(String account);
}