package org.xij.web.module.sys.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xij.util.Result;
import org.xij.web.core.AuthContext;

import java.util.HashMap;
import java.util.Map;

@ResponseBody
@Controller
@RequestMapping("auth")
public class AuthController {

    @RequestMapping(value = "info", method = RequestMethod.POST)
    public Result queryInfo() {
        String userName = AuthContext.get().getUserName();
        return Result.ok(userName);
    }
}
