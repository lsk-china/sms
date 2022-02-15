package com.lsk.sms.response;

import com.google.gson.Gson;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Aspect
@Component
public class ResponseAspect {
    private static final Logger log = LoggerFactory.getLogger(ResponseAspect.class);
    private static final int ITEMS_PER_PAGE = 10;

    @Pointcut("@annotation(com.lsk.sms.response.JsonReturn)")
    public void jsonReturn() {}

    @Pointcut("@annotation(com.lsk.sms.response.Pagination)")
    public void pagination() {}

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

    @Around("pagination()")
    public Object pagination(ProceedingJoinPoint pjp) {
        try {
            Object result = pjp.proceed();
            if (! (result instanceof List)) {
                throw new RuntimeException("Unsupported type");
            }
            List<Object> resultList = (List<Object>) result;
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String pageStr = request.getParameter("page");
            if (pageStr == null || pageStr.equals("")) {
                throw new RuntimeException("Page is empty");
            }
            int page = Integer.parseInt(pageStr);
            int dbResultCount = resultList.size();
            int totalPages = (int) Math.ceil(dbResultCount / ((double) ITEMS_PER_PAGE));
            if (page > totalPages) {
                throw new RuntimeException("Page too big");
            }
            int indexStart = (page - 1) * ITEMS_PER_PAGE;
            int indexEnd = page * ITEMS_PER_PAGE;
            if ((indexEnd - 1) > resultList.size()) {
                indexEnd = resultList.size() - 1;
            }
            List<Object> pagedResult = resultList.subList(indexStart, indexEnd);
            Map<String, Object> resp = new HashMap<>();
            resp.put("totalPages", totalPages);
            resp.put("paged", pagedResult);
            return resp;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
}
