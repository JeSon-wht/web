package org.xij.web.core;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.util.Date;

public class Jwts {
    private static Algorithm ALGORITHM;

    static void init(String secret) {
        if (null == ALGORITHM) {
            ALGORITHM = Algorithm.HMAC256(secret);
        } else {
            throw new RuntimeException("Jwts already initialized");
        }
    }

    public static String getToken(String userId, long expiration) {
        long now = System.currentTimeMillis();
        return JWT.create()
                .withSubject(userId)
                .withIssuedAt(new Date(now))
                .withExpiresAt(new Date(now + expiration))
                .sign(ALGORITHM);
    }

    public static String getSubject(String token) {
        return JWT.decode(token).getSubject();
    }

    public static boolean validate(String token) {
        if (token == null)
            return false;
        try {
            JWT.require(ALGORITHM).build().verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }
}
