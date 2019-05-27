package org.xij.web.module.sys.role.right;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xij.annotation.Pageable;
import org.xij.util.Result;
import org.xij.web.module.sys.right.Right;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping(value = "role/right", method = RequestMethod.POST)
public class RoleRightController {
    private RoleRightService service;

    public RoleRightController(RoleRightService service) {
        this.service = service;
    }

    @Pageable
    @RequestMapping(value = "query")
    public Result query(String roleId, @RequestParam(value = "own") String own, String name, String type) {
        List<Right> rights = service.query(roleId, own, name, type);
        return Result.ok(rights);
    }

    @RequestMapping(value = "grant")
    public Result grant(String roleId, @RequestParam("rightIds") List<String> rightIds) {
        service.grant(roleId, rightIds);
        return Result.OK;
    }

    @RequestMapping(value = "cancel")
    public Result cancel(String roleId, @RequestParam("rightIds") List<String> rightIds) {
        service.cancel(roleId, rightIds);
        return Result.OK;
    }
}
