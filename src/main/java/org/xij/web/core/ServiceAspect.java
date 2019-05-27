package org.xij.web.core;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;
import org.xij.annotation.Cache;
import org.xij.util.*;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class ServiceAspect {
    private static final Logger LOG = LoggerFactory.getLogger(ServiceAspect.class);
    private static final StringManager MANAGER = StringManager.getManager(ServiceAspect.class);

    private ExpressionParser parser = new SpelExpressionParser();
    private Map<String, Expression> expressionMap = new HashMap<>();

    @Pointcut("execution(* org.xij.web.module..*Mapper.*(..))")
    public void servicePointcut() {
    }

    @Around(value = "servicePointcut()")
    public Object serviceAround(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        Cache cache = method.getAnnotation(Cache.class);
        if (null == cache)
            return joinPoint.proceed();

        String key = cache.value();
        if (Strings.isBlank(key)) {
            return joinPoint.proceed();
        }

        Expression expression = expressionMap.get(key);
        if (null == expression) {
            expression = parser.parseExpression(key);
            expressionMap.put(key, expression);
        }

        Object[] args = joinPoint.getArgs();
        key = expression.getValue(args, String.class);

        if (Strings.isBlank(key)) {
            if (LOG.isTraceEnabled())
                LOG.trace(MANAGER.getString("cache.nokey", signature, JSON.toString(args)));
            return joinPoint.proceed();
        }

        if (cache.user()) {
            key = AuthContext.get().getUserId() + key;
        }

        Object value = Caches.getObject(key);

        if (value == null) {
            value = joinPoint.proceed();
            Caches.set(key, value, cache.time());
        } else {
            if (LOG.isTraceEnabled())
                LOG.trace("using cache {}: {}", signature, JSON.toString(args));
        }
        return value;
    }
}