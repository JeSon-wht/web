package org.xij.web.module.sys.dept;

import org.apache.ibatis.annotations.Param;
import org.xij.annotation.MybatisMapper;
import org.xij.web.module.base.Mapper;
import org.xij.web.module.base.TreeMapper;


@MybatisMapper
public interface DeptMapper extends Mapper<Dept>, TreeMapper<Dept> {
    Integer updateDocSeq(@Param("docSeq") int docSeq, @Param("deptId") String deptId);
}
