package com.lsk.sms.response;

import com.lsk.sms.response.annotation.FormatDate;
import com.lsk.sms.util.ReflectionUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Aspect
@Order(2)
@Component
public class DateFormatAspect {
    private static final Logger log = LoggerFactory.getLogger(DateFormatAspect.class);

    @Pointcut("@annotation(com.lsk.sms.response.annotation.FormatDate)")
    public void formatDate() {}

    private String getterNameToFieldName(String getterName) {
        String replaced = getterName.replace("get", "");
        char firstChar = replaced.charAt(0);
        firstChar += 32;    // 大写转小写
        return replaced.replace(replaced.charAt(0), firstChar);
    }

    private Map<String, Object> processEntity(Class<?> type, Object instance, DateFormat dateFormat) throws Exception {
        Method[] methods = type.getDeclaredMethods();       // 获取返回类所有方法的对象
        Map<String, Object> result = new HashMap<>();
        for (Method method : methods) {
            if (method.getName().startsWith("get")) {                   // 判断方法是不是getter方法
                Object value = method.invoke(instance);             // 调用方法获取值
                // 获取getter对应的参数名
                String key = getterNameToFieldName(method.getName());
                if (value instanceof Date) {                            // 如果值是Date类型，那么进行格式化
                    Date date = (Date) value;
                    String formatted = dateFormat.format(date);
                    result.put(key, formatted);
                } else {                                                // 其他类型不管
                    result.put(key, value);
                }
            }
        }
        return result;
    }


    @Around("formatDate()")
    public Object formatDate(ProceedingJoinPoint pjp) {
        try {
            FormatDate formatDate = ReflectionUtil.getAnnotation(pjp, FormatDate.class);
            String format = formatDate.value();                    // 格式
            DateFormat dateFormat = new SimpleDateFormat(format);   // 格式化器对象
            Object methodResult = pjp.proceed();                    // 执行方法获得返回的内容
            Class<?> methodResultType = methodResult.getClass();    // 获取方法返回内容的类型
            String resultTypePackage = methodResultType.getPackage().getName(); // 返回类型的包名
            // 仅可以处理实体类和实体类的列表
            // 实体类的情况
            if (resultTypePackage.equals("com.lsk.sms.model")) {
                return processEntity(methodResultType, methodResult, dateFormat);
            } else if (methodResult instanceof List) {       // 列表的情况
                List<?> resultList = (List<?>) methodResult;
                List<Map<String, Object>> result = new ArrayList<>();
                if (resultList.size() == 0) {                // 为空列表直接返回
                    return methodResult;
                }
                Class<?> type = resultList.get(0).getClass(); // 列表元素的类型
                for (Object instance : resultList) {          // 遍历列表，处理每一个元素
                    result.add(processEntity(type, instance, dateFormat));
                }
                return result;
            } else {
                log.debug(methodResult.toString());
                return methodResult;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
