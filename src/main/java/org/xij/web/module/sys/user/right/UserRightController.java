package org.xij.web.module.sys.user.right;

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
@RequestMapping(value = "user/right", method = RequestMethod.POST)
public class UserRightController {
    private UserRightService service;

    public UserRightController(UserRightService service) {
        this.service = service;
    }

    @RequestMapping(value = "query")
    @Pageable
    public Result query(String userId, @RequestParam(value = "own", defaultValue = "1") String own, String name, String type) {
        List<Right> rights = service.query(userId, own, name, type);
        return Result.ok(rights);
    }

    @RequestMapping(value = "grant")
    public Result grant(String userId, @RequestParam(value = "rightIds") List<String> rightIds) {
        service.grant(userId, rightIds);
        return Result.OK;
    }

    @RequestMapping(value = "cancel")
    public Result cancel(String userId, @RequestParam(value = "rightIds") List<String> rightIds) {
        service.cancel(userId, rightIds);
        return Result.OK;
    }
}
