package org.xij.web.core;

import org.xij.util.Result;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {
    Result login(String account, String password, String ip);
    Result check(String module, String func, HttpServletRequest request);
    Result parseToken(String token);
    String refreshToken();
}
