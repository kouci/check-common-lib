package com.tonorganisation.check_common_lib.aspect;



import com.tonorganisation.check_common_lib.annotation.RetryOnfailure;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.aspectj.lang.reflect.MethodSignature;


import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component

public class RetryAspect {

    @Around("@annotation(com.tonorganisation.check_common_lib.annotation.RetryOnFailure)")
    public Object retryMethod(ProceedingJoinPoint joinPoint) throws Throwable {

        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        RetryOnfailure retryOnFailure = method.getAnnotation(RetryOnfailure.class);

        int maxAttempts = retryOnFailure.maxAttempts();
        long delayMs = retryOnFailure.delayMs();
        Class<? extends Throwable>[] include = retryOnFailure.include();
        Class<? extends Throwable>[] exclude = retryOnFailure.exclude();

        int attempts = 0;
        Throwable lastException = null;

        while (attempts < maxAttempts) {
            try {
                return joinPoint.proceed();
            } catch (Throwable ex) {
                attempts++;
                lastException = ex;

                if (isExcluded(ex, exclude)) {
                    log.warn("L'exception {} est exclue des tentatives de retry, arrêt immédiat", ex.getClass().getName());
                    throw ex;
                }


                if (isIncluded(ex, include) || include.length == 0) {
                    log.warn("Tentative {} échouée, réessayer après {} ms", attempts, delayMs);
                    if (attempts < maxAttempts) {
                        Thread.sleep(delayMs);
                    }
                } else {
                    throw ex;
                }
            }
        }


        log.error("Échec après {} tentatives", maxAttempts);
        if (lastException != null) {
            throw lastException;
        }

        return null;
    }


    private boolean isIncluded(Throwable ex, Class<? extends Throwable>[] include) {
        for (Class<? extends Throwable> clazz : include) {
            if (clazz.isInstance(ex)) {
                return true;
            }
        }
        return false;
    }


    private boolean isExcluded(Throwable ex, Class<? extends Throwable>[] exclude) {
        for (Class<? extends Throwable> clazz : exclude) {
            if (clazz.isInstance(ex)) {
                return true;
            }
        }
        return false;
    }
}
