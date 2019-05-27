package org.xij.web.module.sys.dic.tree;

import org.apache.ibatis.annotations.Param;
import org.xij.annotation.MybatisMapper;
import org.xij.web.module.base.TreeMapper;

import java.util.List;

/**
 * @author XiongKai
 * @version 0.0.1
 * @since 2016年12月16日 下午2:24:13
 */
@MybatisMapper
public interface TreeItemMapper extends TreeMapper<TreeItem> {
    List<TreeItem> queryTree(String pId);

    List<TreeItem> queryMenuTree(@Param("pId") String pId);

    List<TreeItem> queryMenuChildren(String pId);

    List<TreeItem> queryDeptTree(String pId);

    List<TreeItem> queryDeptChildren(String pId);
}
