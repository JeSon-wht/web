package org.xij.web.module.sys.dic.tree;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TreeItemServiceImpl implements TreeItemService {
    private TreeItemMapper mapper;

    public TreeItemServiceImpl(TreeItemMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<TreeItem> queryChildren(String pId) {
        return mapper.queryChildren(pId);
    }

    @Override
    public List<TreeItem> queryTree(String pId) {
        return mapper.queryTree(pId);
    }

    @Override
    public List<TreeItem> queryMenuChildren(String pId) {
        return mapper.queryMenuChildren(pId);
    }

    @Override
    public List<TreeItem> queryMenuTree(String pId) {
        return mapper.queryMenuTree(pId);
    }

    @Override
    public List<TreeItem> queryDeptChildren(String pId) {
        return mapper.queryDeptChildren(pId);
    }

    @Override
    public List<TreeItem> queryDeptTree(String pId) {
        return mapper.queryDeptTree(pId);
    }
}
