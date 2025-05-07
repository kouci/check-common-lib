package com.tonorganisation.check_common_lib.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import java.util.Arrays;

/**
 * AuditAspect provides an aspect for auditing method calls in REST controllers.
 * It uses the @Around advice to intercept method execution of classes annotated
 * with @RestController. The aspect checks if auditing is enabled in the
 * application properties (via AuditProperties) and logs details such as the
 * method signature and returned result.
 * <p>
 * This allows developers to track and monitor method calls in REST endpoints,
 * providing insights into the behavior of the application.
 * <p>
 * Key Features:
 * - Enables or disables auditing based on a configuration property.
 * - Logs method signatures and return values for observed methods.
 * - Provides a mechanism to capture and analyze audit-related information.
 * <p>
 * Dependencies:
 * - Requires AuditProperties to determine if auditing is enabled.
 * - Utilizes Spring AOP annotations such as @Aspect and @Around.
 * <p>
 * Note:
 * Auditing can be toggled globally using the "check-common.audit-enable" property
 * in the application configuration file.
 */
@Component
@Aspect
@Slf4j
public class AuditAspect {

    /**
     * Audit Aspect
     *
     * @param joinPoint : Point join
     * @return : Object
     * @throws Throwable : Throw an exception or error
     */


    @Around("@within(org.springframework.web.bind.annotation.RestController)")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {

        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();


        log.info("[AUDIT] Appel de : {} avec les paramètres {}", methodName, Arrays.deepToString(args));


        Object result = joinPoint.proceed();


        log.info("[AUDIT] Résultat de {} : {}", methodName, result);

        return result;
    }
}
