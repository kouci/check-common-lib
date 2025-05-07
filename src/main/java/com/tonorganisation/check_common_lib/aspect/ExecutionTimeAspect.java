package com.tonorganisation.check_common_lib.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


/**
 * LogExecutionTimeAspect is an aspect for logging the execution time of methods
 * within Spring controllers annotated with @RestController. It uses the @Around
 * advice to intercept method invocations and log timing-related information.
 *
 * The aspect works based on a configurable property (logExecutionTime) provided
 * by LogExecutionTimeProperties to enable or disable the execution time logging
 * globally for the application.
 *
 * Targets:
 * - Methods within classes annotated with @RestController.
 *
 * Features:
 * - Logs the start and end timestamps of method execution.
 * - Records and logs the total time elapsed during method execution.
 * - Includes the method name and result in the log output when logging is active.
 *
 * Implementation Details:
 * - The logging mechanism is conditionally enabled based on the value of
 *   logExecutionTimeProperties.isLogExecutionTime().
 * - Utilizes Spring AOP for method interception.
 *
 * Dependencies:
 * - Requires LogExecutionTimeProperties for determining if logging is active.
 * - Relies on Lombok's @Slf4j for logging functionality.
 *
 * Exceptions:
 * - Passes any exceptions thrown by the intercepted methods up to the caller.
 *
 * Constraints:
 * - Logging is effective only when logExecutionTime is set to true in the application
 *   configuration.
 */

@Aspect
@Component
@Slf4j
public class ExecutionTimeAspect {


    /**
     *
     * @param joinPoint : Joint point for Log execution time
     * @return : Object
     * @throws Throwable : Throw an exception, error
     */



    @Around("@within(org.springframework.web.bind.annotation.RestController)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();

        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();

        log.info("[EXECUTION TIME] Méthode {} exécutée en {} ms", methodName, (end - start));

        return result;
    }
}
