package com.lsk.sms.util;

import com.lsk.sms.response.StatusCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public final class SecurityUtil {
    public static String currentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof String) {
            throw new StatusCode(403, "User not login");
        }
        return ((UserDetails) principal).getUsername();
    }
}
