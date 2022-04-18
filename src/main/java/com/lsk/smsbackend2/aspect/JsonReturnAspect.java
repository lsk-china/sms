package com.lsk.smsbackend2.aspect;

import com.lsk.smsbackend2.response.Response;
import org.apache.ibatis.javassist.compiler.ProceedHandler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class JsonReturnAspect {
    @Pointcut("execution(Object com.lsk.smsbackend2.controller.*.*(..))")
    public void pointcut() {}

    @Around("pointcut()")
    public Object around (ProceedingJoinPoint pjp) {
        try {
            Response response = (Response) pjp.proceed();
            return response.build();
        } catch (Throwable e) {
            return Response.error(e).build();
        }
    }
}
