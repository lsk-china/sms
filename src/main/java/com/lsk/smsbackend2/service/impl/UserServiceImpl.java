package com.lsk.smsbackend2.service.impl;

import java.util.UUID;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsk.smsbackend2.mapper.UserMapper;
import com.lsk.smsbackend2.redis.RedisDao;
import com.lsk.smsbackend2.model.User;
import com.lsk.smsbackend2.service.UserService;
import com.lsk.smsbackend2.util.HashUtil;
import com.lsk.smsbackend2.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Value("${paging.items-per-page}")
    private Integer itemsPerPage;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisDao redisDao;

    @Override
    public User queryUserByName(String username) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("name", username));
    }

    @Override
    public Page<User> queryAllUser(Integer pageId) {
        Page<User> page = new Page<>(pageId, itemsPerPage);
        return userMapper.selectPage(page, null);
    }

    @Override
    public void updatePassword(String newPassword) {
        String newPasswordHash = HashUtil.sha256String(newPassword);
        String username = SecurityUtil.currentUsername();
        Integer id = redisDao.getInteger(username + "-ID");
        User newUser = new User();
        newUser.setId(id);
        newUser.setPassword(newPasswordHash);
        userMapper.updateById(newUser);
    }

    @Override
    public Integer createPerson(String username, String password, String role) {
        User user = new User();
        user.setName(username);
        user.setPassword(HashUtil.sha256String(password));
        user.setRole(role);
        user.setUuid(UUID.randomUUID().toString());
        userMapper.insert(user);
        return userMapper.selectOne(new QueryWrapper<User>().eq("uuid", user.getUuid())).getId();
    }

    @Override
    public void updateUsername(String newUsername) {
        String username = SecurityUtil.currentUsername();
        Integer id = redisDao.getInteger(username + "-ID");
        User user = new User();
        user.setId(id);
        user.setName(newUsername);
        userMapper.updateById(user);
    }

    @Override
    public void grantPerson(Integer targetUID, String role) {
        User user = new User();
        user.setId(targetUID);
        user.setRole(role);
        userMapper.updateById(user);
    }

    @Override
    public void deletePerson(Integer id) {
        userMapper.deleteById(id);
    }

    @Override
    public String name(Integer id) {
        return userMapper.selectById(id).getName();
    }
}
