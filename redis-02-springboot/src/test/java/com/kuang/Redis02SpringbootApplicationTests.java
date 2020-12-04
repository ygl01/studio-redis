package com.kuang;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuang.pojo.Test1;
import com.kuang.pojo.User;
import com.kuang.service.TestService;
import com.kuang.utils.RedisUtil;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;


import java.io.PipedInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@SpringBootTest
class Redis02SpringbootApplicationTests {

    @Autowired
    //注意添加这个注解指定我们自己编写的redisTemplate
    @Qualifier("redisTemplate1")
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private TestService testService;

    @Test
    public void selectPipellined(){
        System.out.println("开始查询");
        long l = System.currentTimeMillis();
        List<String> keys = new ArrayList<>();

        for (int i = 0; i < 478385; i++) {
            keys.add(("pipel:" + i));
            System.out.println("第一次："+i);
        }
        redisTemplate.executePipelined(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                for (String key : keys) {
                    connection.get(key.getBytes());
                    System.out.println("第二次："+key);
                }
                System.out.println("大小："+redisTemplate.keys("*").size());
                return null;
            }
        });
        long l1 = System.currentTimeMillis();

        System.out.println("结束："+(l1-l));
    }

    @Test
    public void testPipellined(){
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        connection.flushDb();
        System.out.println("开始查询数据库");
        List<Test1> all = testService.findAll();
        System.out.println("大小："+all.size());
        System.out.println("开始导入redis");
        long l = System.currentTimeMillis();
//        for (int i = 0;i<all.size();i++){
//            Test1 test1 = all.get(i);
//            String s = JSON.toJSONString(test1);
//            redisUtil.set(("iii"+i),s);
//
//        }
        redisTemplate.executePipelined(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                for (int i = 0; i < all.size(); i++) {
                    Test1 test1 = all.get(i);
                    String s = JSON.toJSONString(test1);
                    connection.set(("pipel:" + i).getBytes(),s.getBytes() );
                }

                return null;
            }
        });
        long l1 = System.currentTimeMillis();
        System.out.println("结束："+(l1-l));
    }

    @Test
    public void test3(){
        //获取redis连接
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        //清空数据库中当前库的所有数据，类似在redis客户端中操作FLUSHDB操作
        connection.flushDb();

        redisUtil.set("age",18);
        System.out.println(redisUtil.get("age"));

        //这个是操作字符串的set方法
        //redisTemplate.opsForValue().set("name","ygl");

        //打印获取的key为name的value值
        //System.out.println(redisTemplate.opsForValue().get("name"));

    }

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
        String str= JSON.toJSONString(user);
//        String jsonUser = new ObjectMapper().writeValueAsString(user);
        redisTemplate.opsForValue().set("user",str);
        System.out.println(redisTemplate.opsForValue().get("user"));
    }

}
