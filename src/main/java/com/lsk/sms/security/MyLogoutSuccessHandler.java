package com.lsk.sms.security;

import com.lsk.sms.response.ResponseAspect;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class MyLogoutSuccessHandler implements LogoutHandler {
    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        try {
            PrintWriter writer = httpServletResponse.getWriter();
            writer.write(ResponseAspect.constructResponse(200, "Success", "Success"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
