package com.lsk.sms.response;

import com.google.gson.Gson;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class ResponseAspect {
    private static final Logger log = LoggerFactory.getLogger(ResponseAspect.class);

    @Pointcut("@annotation(com.lsk.sms.response.JsonReturn)")
    public void jsonReturn(){}

    public static String constructResponse(int code, String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", code);
        response.put("message", message);
        response.put("data", data);
        return new Gson().toJson(response);
    }

    @Around("jsonReturn()")
    public Object jsonReturnAspect(ProceedingJoinPoint pjp) {
        try {
            Object data = pjp.proceed();
            return constructResponse(200, "Success", data);
        } catch (StatusCode statusCode) {
            return constructResponse(statusCode.getCode(), statusCode.getMessage(), null);
        } catch (Throwable t) {
            log.error("Caught exception: ", t);
            return constructResponse(500, t.getMessage(), null);
        }
    }
}
