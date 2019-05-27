package org.xij.web.module.sys.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xij.util.Id;
import org.xij.util.Result;
import org.xij.web.core.AuthContext;
import org.xij.web.module.base.BaseController;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@ResponseBody
@RequestMapping("user")
public class UserController extends BaseController<User, UserService> {
    public UserController(UserService userService) {
        super(userService);
    }

    @RequestMapping("pwd/reset")
    public void resetPassword(String id, HttpServletResponse response) {
        String password = Id.generateId();
        service.resetPassword(id, password);
        response.setContentType("application/x-download");

        response.addHeader("Content-Disposition", "attachment;filename=password.txt");
        try (PrintWriter w = response.getWriter()) {
            w.print(password);
            w.flush();
        } catch (IOException e) {
            e.getMessage();
        }
    }

    @RequestMapping("pwd/alter")
    public Result alterPassword(String id, String oldPassword, String newPassword) {
        try {
            Integer num = service.alterPassword(id, oldPassword, newPassword);
            return Result.ok(num);
        } catch (Exception e) {
            return Result.ok(e.getMessage());
        }
    }

    @RequestMapping(
            value = {"auth/{id}"},
            method = {RequestMethod.POST}
    )
    public Result queryInfoById(@PathVariable("id") String id) {
        User user = service.queryById(id);
        return Result.ok(user);
    }

    @RequestMapping(
            value = {"auth"},
            method = {RequestMethod.POST}
    )
    public Result publicQueryInfoById() {
        String id = AuthContext.get().getUserId();
        User user = service.queryById(id);
        return Result.ok(user);
    }

    @RequestMapping("pwd/publicalter")
    public Result publicAlterPassword(String oldPassword, String newPassword) {
        try {
            String id = AuthContext.get().getUserId();
            Integer num = service.alterPassword(id, oldPassword, newPassword);
            return Result.ok(num);
        } catch (Exception e) {
            return Result.ok(e.getMessage());
        }
    }

    @RequestMapping(
            value = {"publicupdate"},
            method = {RequestMethod.POST}
    )
    public Result publicUpdate(User user) {
        String id = AuthContext.get().getUserId();
        user.setId(id);
        this.service.update(user);
        return Result.OK;
    }

    @RequestMapping(value = {"save"}, method = {RequestMethod.POST})
    public Result save(User user) {
        try {
            Integer num = service.save(user);
            return num == 1 ? Result.ok(user.getId()) : Result.resp(51, num);
        }catch (Throwable t){
            Map map = new HashMap();
            map.put("error",t.getMessage());
            return Result.ok(map);
        }
    }
}