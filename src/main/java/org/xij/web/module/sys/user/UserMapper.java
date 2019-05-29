package org.xij.web.module.sys.user;

import org.apache.ibatis.annotations.Param;
import org.xij.annotation.MybatisMapper;
import org.xij.web.module.base.Mapper;

import java.util.Date;

@MybatisMapper
public interface UserMapper extends Mapper<User> {
    Integer resetPassword(@Param("id") String id, @Param("password") String password, @Param("updateBy") String updateBy, @Param("updateDept") String updateDept, @Param("updateTime") Date updateTime);

    User queryPwdById(String id);

    Integer checkRepeat(String account);
}