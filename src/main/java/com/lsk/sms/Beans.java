package com.lsk.sms;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class Beans {
    @Bean
    public Gson gson() { return new Gson(); }
    @Bean
    public JedisPool jedisPool() {
        return new JedisPool(new JedisPoolConfig(), "localhost", 6379, 5000, "redisadmin", 3);
    }

}
