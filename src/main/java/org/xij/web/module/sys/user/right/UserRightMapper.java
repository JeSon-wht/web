package org.xij.web.module.sys.user.right;

import org.apache.ibatis.annotations.Param;
import org.xij.annotation.MybatisMapper;
import org.xij.web.module.sys.right.Right;

import java.util.List;

@MybatisMapper
public interface UserRightMapper {
    List<Right> query(@Param("userId") String userId, @Param("own") String own, @Param("name") String name,@Param("type") String type);

    Integer insert(@Param("userId") String userId, @Param("rightIds") List<String> rightIds);

    Integer delete(@Param("userId") String userId, @Param("rightIds") List<String> rightIds);
}
