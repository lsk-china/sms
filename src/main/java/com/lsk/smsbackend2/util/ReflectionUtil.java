package com.lsk.smsbackend2.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public final class ReflectionUtil {
    public static <T extends Annotation> T getAnnotation(ProceedingJoinPoint pjp, Class<T> type) {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method targetMethod = methodSignature.getMethod();
        return targetMethod.getAnnotation(type);
    }

    private static String getterToFieldName (String getterName) {
        String removedGet = getterName.replace("get", "");
        char firstChar = removedGet.charAt(0);
        char firstCharLowerCase = (char) (firstChar + 32);
        return removedGet.replace(firstChar, firstCharLowerCase);
    }

    public static Map<String, Object> objectToMap(Object o) {
        try {
            Map<String, Object> result = new HashMap<>();
            Class<?> clazz = o.getClass();
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().startsWith("get")) {
                    method.setAccessible(false);
                    String fieldName = getterToFieldName(method.getName());
                    Object fieldValue = method.invoke(o, null);
                    result.put(fieldName, fieldValue);
                }
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
