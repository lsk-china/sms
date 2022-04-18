package com.lsk.smsbackend2.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsk.smsbackend2.model.User;

public interface UserService {
    User queryUserByName(String username);

    Page<User> queryAllUser(Integer pageId);
}
