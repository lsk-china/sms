package com.lsk.smsbackend2.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Jedis;

@Component
public class RedisDao {
    @Autowired
    private JedisPool jedisPool;

    public void set(String key, String value, int expire) {
        try(Jedis jedis = jedisPool.getResource()){
            jedis.set(key, value);
            jedis.expire(key, expire);
        }
    }
    public void set(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set(key, value);
        }
    }
    public String get(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.get(key);
        }
    }
    public boolean has(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.exists(key);
        }
    }
}
