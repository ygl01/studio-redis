package com.kuang;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuang.pojo.User;
import com.kuang.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;


import com.kuang.config.RedisConfig.*;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;


import java.util.Set;

@SpringBootTest
class Redis02SpringbootApplicationTests {

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void test1(){
        redisUtil.set("key01", "value01");
        System.out.println(redisUtil.get("key01"));
    }

    @Test
    void contextLoads() {
//        redisTemplate.opsForValue().set("key1","value1");
//        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
//        connection.flushDb();
//
//        Object key1 = redisTemplate.opsForValue().get("key1");
//        System.out.println("key："+key1);
//        Set keys = redisTemplate.keys("*");
//
//        int size = keys.size();
//        System.out.println("222:"+size);
//        System.out.println("111："+keys);
//        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
//        connection.flushAll();
//        redisTemplate.opsForValue().set("key1","狂神说Java");
//        Object key1 = redisTemplate.opsForValue().get("key1");
//        System.out.println(redisTemplate.opsForValue().get("key1"));
//        System.out.println(key1);


    }

    @Test
    public void test() throws JsonProcessingException {
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        connection.flushDb();
        User user = new User("狂神说", 3);
//        String jsonUser = new ObjectMapper().writeValueAsString(user);
        redisTemplate.opsForValue().set("user",user);
        System.out.println(redisTemplate.opsForValue().get("user"));
    }

}
