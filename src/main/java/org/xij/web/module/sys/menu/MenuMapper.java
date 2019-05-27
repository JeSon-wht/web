package org.xij.web.module.sys.menu;

import org.xij.annotation.MybatisMapper;
import org.xij.web.module.base.Mapper;
import org.xij.web.module.base.TreeMapper;

import java.util.List;

@MybatisMapper
public interface MenuMapper extends Mapper<Menu>, TreeMapper<Menu> {
    List<Menu> query(Menu menu);

    Integer delete(String id);

    Menu queryById(String id);
}
