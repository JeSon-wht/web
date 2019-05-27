package org.xij.web.module.sys.user.role;

import org.apache.ibatis.annotations.Param;
import org.xij.annotation.MybatisMapper;
import org.xij.web.module.sys.role.Role;

import java.util.List;

@MybatisMapper
public interface UserRoleMapper {
    List<Role> query(@Param("userId") String userId, @Param("own") String own, @Param("name") String name);

    Integer insert(@Param("userId") String userId, @Param("roleIds") List<String> roleIds);

    Integer delete(@Param("userId") String userId, @Param("roleIds") List<String> roleIds);
}
