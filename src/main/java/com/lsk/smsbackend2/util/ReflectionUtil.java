package com.lsk.smsbackend2.util;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public final class ReflectionUtil {
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy MM dd HH mm ss");

    public static <T extends Annotation> T getAnnotation(ProceedingJoinPoint pjp, Class<T> type) {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method targetMethod = methodSignature.getMethod();
        return targetMethod.getAnnotation(type);
    }

    private static String getterToFieldName (String getterName) {
        log.debug(getterName);
        String removedGet = getterName.replace("get", "");
        char firstChar = removedGet.charAt(0);
        char firstCharLowerCase = (char) (firstChar + 32);
        return removedGet.replace(firstChar, firstCharLowerCase);
    }

    public static Map<String, Object> objectToMap(Object o) {
        try {
            if (o instanceof Map) {
                return (Map<String, Object>) o;
            }
            Map<String, Object> result = new HashMap<>();
            Class<?> clazz = o.getClass();
            log.debug(clazz.getName());
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().startsWith("get")) {
                    method.setAccessible(true);
                    String fieldName = getterToFieldName(method.getName());
                    Object fieldValue = method.invoke(o, null);
                    if (fieldValue instanceof Date) {
                        Date dateValue = (Date) fieldValue;
                        String formatted = SIMPLE_DATE_FORMAT.format(dateValue);
                        result.put(fieldName, formatted);
                    } else {
                        result.put(fieldName, fieldValue);
                    }
                }
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
