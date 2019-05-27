package org.xij.web.module.base;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TreeMapper<T extends TreeEntity> {
    List<T> queryChildren(@Param("pId") String pId);
}
