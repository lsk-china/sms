package com.lsk.sms.security;

import com.lsk.sms.response.ResponseAspect;
import com.lsk.sms.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().forEach(ele -> log.debug(ele.getAuthority()));
        log.debug(httpServletRequest.getRequestURI());
        log.debug(e.getMessage());
        PrintWriter printWriter = httpServletResponse.getWriter();
        printWriter.write(ResponseAspect.constructResponse(403, "Forbidden", null));
    }
}
