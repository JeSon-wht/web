package org.xij.web.module.sys.dic.tree;

import org.xij.web.module.base.TreeService;

import java.util.List;

public interface TreeItemService extends TreeService<TreeItem> {
    List<TreeItem> queryTree(String pId);

    List<TreeItem> queryMenuTree(String pId);

    List<TreeItem> queryMenuChildren(String pId);

    List<TreeItem> queryDeptTree(String pId);

    List<TreeItem> queryDeptChildren(String pId);
}
