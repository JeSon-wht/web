package org.xij.web.module.sys.role.user;

import org.apache.ibatis.annotations.Param;
import org.xij.annotation.MybatisMapper;
import org.xij.web.module.sys.right.Right;

import java.util.List;

@MybatisMapper
public interface RoleUserMapper {
    List<Right> query(@Param("roleId") String roleId, @Param("own") String own, @Param("account") String account, @Param("name") String name);

    Integer insert(@Param("roleId") String roleId, @Param("userIds") List<String> userIds, @Param("createBy") String createBy, @Param("createDept") String createDept);

    Integer delete(@Param("roleId") String roleId, @Param("userIds") List<String> userIds);
}
