package com.lsk.smsbackend2.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsk.smsbackend2.model.User;

public interface UserService {
    User queryUserByName(String username);

    Page<User> queryAllUser(Integer pageId);

    void updatePassword(String newPassword);

    Integer createPerson(String username, String password, String role);

    void updateUsername(String newUsername);

    void grantPerson(Integer targetUID, String role);

    void deletePerson(Integer id);

    String name(Integer id);

    void updatePasswordFor(Integer targetID, String newPassword);
}
