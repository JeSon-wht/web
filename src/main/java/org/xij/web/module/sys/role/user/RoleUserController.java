package org.xij.web.module.sys.role.user;

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
@RequestMapping(value = "role/user", method = RequestMethod.POST)
public class RoleUserController {
    private RoleUserService service;

    public RoleUserController(RoleUserService service) {
        this.service = service;
    }

    @Pageable
    @RequestMapping(value = "query")
    public Result query(String roleId, @RequestParam(value = "own") String own, String account, String name) {
        List<Right> rights = service.query(roleId, own, account, name);
        return Result.ok(rights);
    }

    @RequestMapping(value = "grant")
    public Result grant(String roleId, @RequestParam("userIds") List<String> userIds) {
        service.grant(roleId, userIds);
        return Result.OK;
    }

    @RequestMapping(value = "cancel")
    public Result cancel(String roleId, @RequestParam("userIds") List<String> userIds) {
        service.cancel(roleId, userIds);
        return Result.OK;
    }
}
