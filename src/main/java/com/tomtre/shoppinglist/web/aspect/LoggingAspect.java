package com.tomtre.shoppinglist.web.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class LoggingAspect {
    private final static Logger logger = Logger.getLogger(LoggingAspect.class.getName());

    @Pointcut("execution(* com.tomtre.shoppinglist.web.controller.*.*(..))")
    private void forWebControllerPackage() {
    }

    @Pointcut("execution(* com.tomtre.shoppinglist.web.restcontroller.*.*(..))")
    private void forRestControllerPackage() {
    }


    @Pointcut("execution(* com.tomtre.shoppinglist.web.service.*.*(..))")
    private void forServicePackage() {
    }

    @Pointcut("execution(* com.tomtre.shoppinglist.web.dao.*.*(..))")
    private void forDaoPackage() {
    }

    @Pointcut("forWebControllerPackage() || forRestControllerPackage() || forServicePackage() || forDaoPackage()")
    private void forAppFlow() {
    }

    @Before("forAppFlow()")
    public void beforeAdvice(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        logger.info("before calling method: " + methodName);
        Object[] methodArgs = joinPoint.getArgs();
        for (int i = 0; i < methodArgs.length; i++) {
            logger.info("methodArgs[" + i + "]: " + methodArgs[i]);
        }
    }

    @AfterReturning(pointcut = "forAppFlow()", returning = "result")
    public void afterReturningAdvice(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().toShortString();
        logger.info("after returning from method: " + methodName);
        logger.info("result: " + result);
    }
}
