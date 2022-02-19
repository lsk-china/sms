package com.lsk.sms.response;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Aspect()
@Order(1)
@Component
public class PaginationAspect {
    private static final Logger log = LoggerFactory.getLogger(PaginationAspect.class);
    private static final int ITEMS_PER_PAGE = 10;

    @Pointcut("@annotation(com.lsk.sms.response.annotation.Pagination)")
    public void pagination() {}

    @Around("pagination()")
    public Object pagination(ProceedingJoinPoint pjp) {
        try {
            Object result = pjp.proceed();
            if (!(result instanceof List)) {
                log.debug(result.toString());
                throw new RuntimeException("Unsupported type: " + result.getClass().getName());
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
            if (totalPages == 0) {
                Map<String, Object> resp = new HashMap<>();
                resp.put("totalPages", 1);
                resp.put("paged", new ArrayList<>());
                return resp;
            }
            if (page > totalPages) {
                throw new RuntimeException("Page too big");
            }
            int indexStart = (page - 1) * ITEMS_PER_PAGE;
            int indexEnd = page * ITEMS_PER_PAGE;
            if ((indexEnd - 1) > resultList.size()) {
                indexEnd = resultList.size();
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
