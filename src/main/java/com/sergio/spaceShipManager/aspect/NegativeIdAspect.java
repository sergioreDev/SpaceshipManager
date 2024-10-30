package com.sergio.spaceShipManager.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class NegativeIdAspect {

    private static final Logger logger = LoggerFactory.getLogger(NegativeIdAspect.class);

    @Before("execution(* com.sergio.spaceShipManager.controller.*Controller.*ById(..)) && args(id,..)")
    public void logNegativeId(JoinPoint joinPoint, Long id) {
        if(id < 0){
            String className = joinPoint.getTarget().getClass().getSimpleName();
            String methodName = joinPoint.getSignature().getName();
            logger.warn("Attempt to access an entity with negative id in method " + methodName + " of class " + className + ": " + id);
        }
    }
}
