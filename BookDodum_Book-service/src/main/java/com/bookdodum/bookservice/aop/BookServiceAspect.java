package com.bookdodum.bookservice.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;


@Slf4j
public class BookServiceAspect {

    @Aspect
    @Order(2)
    static class PerformanceAspect {
        @Around("com.bookdodum.bookservice.aop.Pointcuts.repositoryMethod()")
        public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
            // Advice
            long startTime = System.currentTimeMillis();

            Object result = joinPoint.proceed();

            long endTime = System.currentTimeMillis();
            double executionTimeInSeconds = (endTime - startTime) / 1000.0; // 밀리초를 초로 변환


            log.info(
                    "{} executed in {} ms",
                    joinPoint.getSignature(), executionTimeInSeconds
            );

            return result;
        }

        @Before("com.bookdodum.bookservice.aop.Pointcuts.repositoryMethod()")
        public void doBefore() {
            long startTime = System.currentTimeMillis();
        }

        @After()
        
    }
    
    @Aspect
    @Order(1)
    static class TxAspect {
        @Around("com.bookdodum.bookservice.aop.Pointcuts.repositoryMethod()")
        public Object txManagement(ProceedingJoinPoint joinPoint) throws Throwable {

            Object proceed = joinPoint.proceed();

            return proceed;
        }
    }
}