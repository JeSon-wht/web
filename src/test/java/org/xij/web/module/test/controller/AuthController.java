package org.xij.web.module.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xij.util.Result;

@Controller
@RequestMapping("auth")
public class AuthController {

    @ResponseBody
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Result needLogin() {
        return Result.ok(true);
    }
}
