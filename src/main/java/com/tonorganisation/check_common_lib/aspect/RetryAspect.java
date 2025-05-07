package com.tonorganisation.check_common_lib.aspect;

import com.tonorganisation.check_common_lib.annotation.RetryOnfailure;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.aspectj.lang.reflect.MethodSignature;
import java.lang.reflect.Method;


/**
 * Aspect implementation that handles retrying logic for methods annotated with {@link RetryOnfailure}.
 * The retry mechanism utilizes configuration properties and annotation attributes to determine
 * maximum retry attempts, delay between attempts, and exceptions to include or exclude in the retry cycle.
 *
 * This aspect enables integration of retry logic without requiring the methods themselves to include
 * while specific method-level settings can be applied using the {@link RetryOnfailure} annotation.
 *
 * The retry functionality checks whether it is enabled via application-defined properties. If disabled,
 * the annotated methods execute with no retry logic applied. Retry cycles are managed based on the
 * maximum attempts and delay specified, and exceptions are evaluated against include and exclude lists.
 */

@Slf4j
@Aspect
@Component
public class RetryAspect {
    /**
     *
     * @param joinPoint : Point of execution of the cibled method.
     * @return : ProceedingJoinPoint
     * @throws Throwable : Throwable object
     */



    @Around("@within(org.springframework.web.bind.annotation.RestController)")
    public Object retryMethod(ProceedingJoinPoint joinPoint) throws Throwable {

        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        RetryOnfailure retryOnFailure = method.getAnnotation(RetryOnfailure.class);

        if (retryOnFailure == null) {
            return joinPoint.proceed();
        }

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


    /**
     *
     * @param include : List of throwable object included from the retry process
     * @param ex : The intercepted exception
     * @return : boolean
     */
    private boolean isIncluded(Throwable ex, Class<? extends Throwable>[] include) {
        for (Class<? extends Throwable> clazz : include) {
            if (clazz.isInstance(ex)) {
                return true;
            }
        }
        return false;
    }


    /**
     *
     * @param exclude : List of throwable object excluded from the retry process
     * @param ex : The intercepted exception
     * @return : boolean
     */

    private boolean isExcluded(Throwable ex, Class<? extends Throwable>[] exclude) {
        for (Class<? extends Throwable> clazz : exclude) {
            if (clazz.isInstance(ex)) {
                return true;
            }
        }
        return false;
    }
}
