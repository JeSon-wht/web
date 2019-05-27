package org.xij.web.core;

public class AuthContext {
    private static final ThreadLocal<AuthInfo> SUBJECT = new ThreadLocal<>();

    public static AuthInfo get() {
        return SUBJECT.get();
    }

    static void set(AuthInfo authInfo) {
        SUBJECT.set(authInfo);
    }
}