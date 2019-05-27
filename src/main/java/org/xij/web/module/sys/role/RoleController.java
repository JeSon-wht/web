package org.xij.web.module.sys.role;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.xij.util.Result;
import org.xij.web.module.base.BaseController;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("role")
public class RoleController extends BaseController<Role, RoleService> {
    private RoleService roleService;

    public RoleController(RoleService roleService) {
        super(roleService);
    }

    @RequestMapping(value = {"save"}, method = {RequestMethod.POST})
    public Result save(Role role) {
        try {
            Integer num = service.save(role);
            return num == 1 ? Result.ok(role.getId()) : Result.resp(51, num);
        } catch (Throwable t) {
            Map map = new HashMap();
            map.put("error",t.getMessage());
            return Result.ok(map);
        }
    }

    @RequestMapping(value = {"update"}, method = {RequestMethod.POST})
    public Result update(Role role) {
        try {
            Integer num = service.update(role);
            return num == 1 ? Result.ok(role.getId()) : Result.resp(51, num);
        } catch (Throwable t) {
            Map map = new HashMap();
            map.put("error",t.getMessage());
            return Result.ok(map);
        }
    }
}
