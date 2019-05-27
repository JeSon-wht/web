package org.xij.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {
    int time() default 15;

    boolean user() default true;

    String value();
}
