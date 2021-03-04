package com.javainuse.config;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import  org.aspectj.lang.JoinPoint;

@Configuration
@EnableAspectJAutoProxy

public class AspectConfig {

    private static final Logger logger = LoggerFactory.getLogger(AspectConfig.class);

    @Before("@within(org.springframework.transaction.annotation.Transactional)")
    private void startAspect(JoinPoint joinpoint)
   {
       logger.info("Before Transactional");


    }

    @AfterReturning("@within(org.springframework.transaction.annotation.Transactional)")
    private void afterAspect(JoinPoint joinpoint)
    {
        logger.info("After Transactional");



    }

}
