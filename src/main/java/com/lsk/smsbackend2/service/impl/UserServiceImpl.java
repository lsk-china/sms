package com.lsk.smsbackend2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsk.smsbackend2.mapper.UserMapper;
import com.lsk.smsbackend2.model.User;
import com.lsk.smsbackend2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Value("${paging.items-per-page}")
    private Integer itemsPerPage;

    @Autowired
    private UserMapper userMapper;

    @Override
    public User queryUserByName(String username) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("name", username));
    }

    @Override
    public Page<User> queryAllUser(Integer pageId) {
        Page<User> page = new Page<>(pageId, itemsPerPage);
        return userMapper.selectPage(page, null);
    }
}
