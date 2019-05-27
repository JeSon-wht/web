package org.xij.web.module.sys.user.role;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xij.annotation.Pageable;
import org.xij.util.Result;
import org.xij.web.module.sys.role.Role;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping(value = "user/role", method = RequestMethod.POST)
public class UserRoleController {
    private UserRoleService service;

    public UserRoleController(UserRoleService service) {
        this.service = service;
    }

    @RequestMapping(value = "query")
    @Pageable
    public Result query(String userId, @RequestParam(value = "own", defaultValue = "1") String own, String name) {
        List<Role> roles = service.query(userId, own, name);
        return Result.ok(roles);
    }

    @RequestMapping(value = "grant")
    public Result grant(String userId, @RequestParam(value = "roleIds") List<String> roleIds) {
        service.grant(userId, roleIds);
        return Result.OK;
    }

    @RequestMapping(value = "cancel")
    public Result cancel(String userId, @RequestParam(value = "roleIds") List<String> roleIds) {
        service.cancel(userId, roleIds);
        return Result.OK;
    }
}