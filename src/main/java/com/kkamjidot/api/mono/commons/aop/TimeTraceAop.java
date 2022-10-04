package com.kkamjidot.api.mono.commons.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
public class TimeTraceAop {
    @Around("execution(* com.kkamjidot.api.mono..*(..)) && !target(com.kkamjidot.api.mono.config.SpringConfig)")    // && !target(com.kkamjidot.api.mono.config.SpringConfig)
    public Object excute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        log.info("START: {}", joinPoint.toString());
        try {
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            log.info("END: {} {}ms", joinPoint.toString(), timeMs);
        }
    }
}
