package com.lsk.sms.security;

import com.lsk.sms.dao.PersonDao;
import com.lsk.sms.dao.StudentDao;
import com.lsk.sms.model.Person;
import com.lsk.sms.model.Student;
import com.lsk.sms.redis.RedisDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component("userDetailsService")
@Slf4j
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private PersonDao personDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private StudentDao studentDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personDao.queryPersonByName(username);
        if (person == null) {
            throw new UsernameNotFoundException("Unknown person");
        }
        redisDao.set(username + "-ID", person.getId().toString());
        if ("STUDENT".equals(person.getRole())) {
            Student student = studentDao.queryStudentByPersonID(person.getId());
            redisDao.set(person.getName() + "-STUDENTID", student.getId().toString());
        }
        log.info(person.getPassword());
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + person.getRole());
        grantedAuthorities.add(grantedAuthority);
        String password = passwordEncoder.encode(person.getPassword());
        log.info(person.getRole());
        return new User(username,password,grantedAuthorities);
    }
}
