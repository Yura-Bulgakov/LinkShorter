package org.example.linkshorter.logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerLoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("within(@org.springframework.stereotype.Controller *)")
    public void logControllerMethodStart(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        logger.info("Контроллер {} обрабатывает {} запрос", className, methodName);
    }
}
