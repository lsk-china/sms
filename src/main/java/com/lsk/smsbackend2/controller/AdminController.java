package com.lsk.smsbackend2.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsk.smsbackend2.model.User;
import com.lsk.smsbackend2.response.Response;
import com.lsk.smsbackend2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/personList")
    public Object personList (Integer page) {
        Page<User> result = userService.queryAllUser(page);
        return Response.ok(result);
    }
}
