package com.kkamjidot.api.mono.commons.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//@Slf4j
@Aspect
@Component
public class LoggingAndTimeTraceAop {
    private static Logger log;

    @Pointcut("execution(* com.kkamjidot.api.mono.controller..*Controller.*(..))")
    public void logginForController() {

    }

    @Around("logginForController()")    // && !target(com.kkamjidot.api.mono.config.SpringConfig)
    public Object excute(ProceedingJoinPoint joinPoint) throws Throwable {
        Object requestResult = null;
        setLogger(joinPoint.getSignature().getDeclaringType());

        log.info("START: {}", joinPoint);
        log.info("args: {}", joinPoint.getArgs());
        long start = System.currentTimeMillis();
        try {
            requestResult = joinPoint.proceed();
            return requestResult;
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            log.info("END: {} {}ms", joinPoint.toString(), timeMs);
            log.info("response: {}", requestResult);
        }
    }

    private void setLogger(Class<?> clazz) {
        log = LoggerFactory.getLogger(clazz);
    }
}
