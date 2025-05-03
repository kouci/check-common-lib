package com.tonorganisation.check_common_lib.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface  RetryOnfailure {
    int maxAttempts() default 3;
    long delayMs() default 500;
    Class<? extends Throwable>[] include() default {};
    Class<? extends Throwable>[] exclude() default {};
}
