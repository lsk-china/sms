package com.lsk.smsbackend2.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public final class ReflectionUtil {
    public static <T extends Annotation> T getAnnotation(ProceedingJoinPoint pjp, Class<T> type) {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method targetMethod = methodSignature.getMethod();
        return targetMethod.getAnnotation(type);
    }
}
