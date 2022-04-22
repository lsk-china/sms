package com.lsk.smsbackend2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class Beans {
    @Value("${redis.password}")
    private String redisPassword;

    @Bean
    public JedisPool jedisPool () {
        return new JedisPool(new JedisPoolConfig(), "localhost", 6379, 5000, redisPassword, 3);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
