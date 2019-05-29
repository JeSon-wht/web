package org.xij.web.module.sys.role.right;

import org.apache.ibatis.annotations.Param;
import org.xij.annotation.MybatisMapper;
import org.xij.web.module.sys.right.Right;

import java.util.List;

@MybatisMapper
public interface RoleRightMapper {
    List<Right> query(@Param("roleId") String roleId, @Param("own") String own, @Param("name") String name, @Param("type") String type);

    Integer insert(@Param("roleId") String roleId, @Param("rightIds") List<String> rightIds, @Param("createBy") String createBy, @Param("createDept") String createDept);

    Integer delete(@Param("roleId") String roleId, @Param("rightIds") List<String> rightIds);
}
