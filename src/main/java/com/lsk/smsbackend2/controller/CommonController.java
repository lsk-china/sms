package com.lsk.smsbackend2.controller;

import com.lsk.smsbackend2.response.Response;
import com.lsk.smsbackend2.service.PaymentService;
import com.lsk.smsbackend2.service.UserService;
import com.lsk.smsbackend2.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/")
public class CommonController {
    @Autowired
    private UserService userService;
    @Autowired
    private PaymentService paymentService;

    @GetMapping("/loginInfo")
    public Object loginInfo () {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = SecurityUtil.currentUsername();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Map<String, Object> resp = new HashMap<>();
        resp.put("name", username);
        resp.put("authorities", authorities);
        return Response.ok(resp);
    }

    @PostMapping("/changePassword")
    public Object changePassword(String newPassword) {
        userService.updatePassword(newPassword);
        return Response.ok("Success");
    }

    @GetMapping("/changeUsername")
    public Object changeUsername(String newUsername) {
        userService.updateUsername(newUsername);
        return Response.ok("Success");
    }

    @GetMapping("/payments")
    public Object payments(Integer page) {
        return Response.ok(paymentService.allPayments(page));
    }

    @GetMapping("/name")
    public Object username(Integer id) {
        return Response.ok(userService.name(id));
    }
}
