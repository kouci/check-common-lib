package com.tonorganisation.check_common_lib.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class AuditAspect {

    @Around("execution(* com.tonorganisation..*(..))")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {

        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();


        log.info("[AUDIT] Appel de : {} avec les paramètres {}", methodName, Arrays.deepToString(args));


        Object result = joinPoint.proceed();


        log.info("[AUDIT] Résultat de {} : {}", methodName, result);

        return result;
    }
}
