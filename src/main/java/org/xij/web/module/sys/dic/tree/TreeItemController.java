package org.xij.web.module.sys.dic.tree;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xij.util.Result;
import org.xij.util.Strings;
import org.xij.web.core.AuthContext;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping(value = "tree", method = RequestMethod.POST)
public class TreeItemController {
    private TreeItemService service;

    public TreeItemController(TreeItemService service) {
        this.service = service;
    }

    @RequestMapping("dic/children/{pId}")
    public Result queryChildren(@PathVariable("pId") String pId) {
        List<TreeItem> children = service.queryChildren(pId);
        return Result.ok(children);
    }

    @RequestMapping("dic/{pId}")
    public Result queryTree(@PathVariable("pId") String pId) {
        List<TreeItem> tree = service.queryTree(pId);
        return Result.ok(tree);
    }

    @RequestMapping("menu/children/{pId}")
    public Result queryMenuChildren(@PathVariable("pId") String pId) {
        List<TreeItem> tree = service.queryMenuChildren(pId);
        return Result.ok(tree);
    }

    @RequestMapping("menu/{pId}")
    public Result queryMenuTree(@PathVariable("pId") String pId) {
        List<TreeItem> tree = service.queryMenuTree(pId);
        return Result.ok(tree);
    }

    @RequestMapping("dept/children/{pId}")
    public Result queryDeptChildren(@PathVariable("pId") String pId) {
        if (Strings.isBlank(pId) || "-".equals(pId)) {
            pId = AuthContext.get().getRightDeptId();
        }
        List<TreeItem> tree = service.queryDeptChildren(pId);
        return Result.ok(tree);
    }

    @RequestMapping("dept/{pId}")
    public Result queryDeptTree(@PathVariable("pId") String pId) {
        List<TreeItem> tree = service.queryDeptTree(pId);
        return Result.ok(tree);
    }
}
