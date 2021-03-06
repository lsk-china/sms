package com.lsk.smsbackend2.util;

import com.lsk.smsbackend2.response.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Slf4j
public final class SecurityUtil {
    public static String currentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.debug(principal.toString());
        if (principal instanceof String) {
            throw new StatusCode(403, "User not login");
        }
        return ((UserDetails) principal).getUsername();
    }
    public static boolean isStudent() {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        return authorities.contains(new SimpleGrantedAuthority("ROLE_STUDENT"));
    }
}
