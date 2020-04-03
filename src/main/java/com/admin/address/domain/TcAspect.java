package com.admin.address.domain;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class TcAspect {
//로그찍기

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* com.admin.address.*.controller.*Controller.*(..))")
    public void beforeLog(JoinPoint jp) {
        logger.info("[" + jp.getTarget().getClass().getSimpleName() + " " + jp.getSignature().getName() + " START]");

    }

    @After("execution(* com.admin.address.*.controller.*Controller.*(..))")
    public void afterLog(JoinPoint jp) {
        logger.info("[" + jp.getTarget().getClass().getSimpleName() + " " + jp.getSignature().getName() + " END]");

    }

}


