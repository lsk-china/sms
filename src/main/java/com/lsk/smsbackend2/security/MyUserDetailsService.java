package com.lsk.smsbackend2.security;

import com.lsk.smsbackend2.mapper.UserMapper;
import com.lsk.smsbackend2.model.User;
import com.lsk.smsbackend2.redis.RedisDao;
import com.lsk.smsbackend2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || username.equals("")) {
            throw new UsernameNotFoundException("Username is empty");
        }
        User user = userService.queryUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        redisDao.set(username + "-ID", user.getId().toString());
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + user.getRole());
        grantedAuthorities.add(grantedAuthority);
        String passwordEncoded = passwordEncoder.encode(user.getPassword());
        return new org.springframework.security.core.userdetails.User(username, passwordEncoded, grantedAuthorities);
    }
}
