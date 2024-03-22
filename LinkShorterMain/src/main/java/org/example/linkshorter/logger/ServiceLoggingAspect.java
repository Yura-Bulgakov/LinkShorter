package org.example.linkshorter.logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceLoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("@annotation(ServiceLogging)")
    public void logMethodCall(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        logger.info("Метод '{}' вызван с параметрами: {}", methodName, args);
    }

    @AfterThrowing(value = "@annotation(ServiceLogging)", throwing = "exception")
    public void logException(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getSignature().getName();
        logger.error("Исключение выбрашено в методе '{}': {}", methodName, exception.getMessage());
    }
}
