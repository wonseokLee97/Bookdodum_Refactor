package com.bookdodum.bookservice.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    @Pointcut("execution(* com.bookdodum.bookservice.repository..*.*(..))")
    public void repositoryMethod() {}

    @Pointcut("execution(* com.bookdodum.bookservice.repository..*.*(..))")
    public void doTxMethod() {}
}
