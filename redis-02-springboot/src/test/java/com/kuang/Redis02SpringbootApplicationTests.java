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


import java.util.Map;
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
        //获取redis连接  且将当前库中所有key value删除
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        connection.flushDb();
//        redisUtil.set("key01", "value01");
//        System.out.println(redisUtil.get("key01"));
        redisUtil.hset("key01","10000","1022");
        redisUtil.hset("key01","10001","1023");

        //通过外层key拿到下面的对象，外层key一般都是已知的
        Map<Object, Object> entries = redisTemplate.opsForHash().entries("key01");
        //假设该变量就是前端传来的value
        String valueName= "022";
        //遍历缓存对象
        for (Object value : entries.keySet()) {
            //如果value是对象直接强转对象即可
            String o = (String) entries.get(value);
            System.out.println("查询的O值："+o);
            //字符串在缓存中取出来有的时候会多出一对双引号，可以debug看一下，把引号去掉
            o = o.replace("\"", "");
            //用假设前端的value和对象下的value相比较，相同则取出对应key即可
            if (o.matches(".*"+valueName+".*")){
                System.out.println(valueName + "的key值："+value);
            }
        }
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
