package com.kuang.controller;

import com.alibaba.fastjson.JSON;
import com.kuang.pojo.Test;
import com.kuang.service.RedisService;
import com.kuang.service.TestService;
import com.kuang.utils.RandomUtil;
import com.kuang.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author ygl
 * @description
 * @date 2020/12/1 19:15
 */
@RestController
@RequestMapping("/redis")
public class TestController {
    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    @Autowired
    private RandomUtil randomUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private TestService testService;

    @Autowired
    private RedisService redisService;

    //存入value中，是hash类型
    @GetMapping("/intoRedis")
    private void intoRedis(){
        Date date1 = new Date();
        List<Test> all = testService.findAll();
        //获取redis连接  且将当前库中所有key value删除
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
//        connection.flushDb();
//        String str= JSON.toJSONString(all);
//        redisUtil.hset("key01","10001",str);

        System.out.println("大小："+all.size());
        for (int i=0;i<all.size();i++){

            Test test = all.get(i);
            String s = test.toString();
            String itemID = randomUtil.getItemID(10);
            redisUtil.hset("key01",itemID,s);
        }

        Date date2 = new Date();
        System.out.println("结束时间："+date1);
        System.out.println("结束时间："+date2);
    }
    //将值存入到key中，是set类型
    @GetMapping("/intoRedis2")
    private void intoRedis2(){
        Date date1 = new Date();
        List<Test> all = testService.findAll();
        //获取redis连接  且将当前库中所有key value删除
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
//        connection.flushDb();

        System.out.println("大小："+all.size());
        for (int i=0;i<all.size();i++){
            Test test = all.get(i);
            String s = test.toString();
            redisUtil.set(s,"你好");
        }
        Date date2 = new Date();
        System.out.println("结束时间："+date1);
        System.out.println("结束时间："+date2);
    }

    @GetMapping("/test")
    private void test(){
        long l = redisUtil.sSet("key01", "1", "2", "3");
        System.out.println("长度："+l);
        redisUtil.sGet("key01");
        System.out.println();
        
    }

    @GetMapping("/likeByOne")
    private List test1(String pattern){
        Date date1 = new Date();
        //模糊查询
//        Set keys = redisTemplate.keys("*" + pattern + "*");

        //通过外层key拿到下面的对象，外层key一般都是已知的
        Map<String, String> entries = redisTemplate.opsForHash().entries("key01");
        List<String> list = new ArrayList<>();
        List<String> listNew = new ArrayList<>();
        //遍历缓存对象
        for (String value : entries.keySet()) {
            //如果value是对象直接强转对象即可
            String o = (String) entries.get(value);
            //字符串在缓存中取出来有的时候会多出一对双引号，可以debug看一下，把引号去掉
            o = o.replace("\"", "");
            //用假设前端的value和对象下的value相比较，相同则取出对应key即可
            if (o.matches(".*"+pattern+".*")){
                list.add(value);
            }
        }
        String str= JSON.toJSONString(list);
        Map<Object, Object> hmget = redisUtil.hmget(str);
        for (String l:list){
            String key01 =(String) redisUtil.hget("key01", l);
            listNew.add(key01);
        }
        System.out.println("集合："+hmget);
        System.out.println("总条数："+listNew.size());
        Date date2 = new Date();
        System.out.println("结束时间："+date1);
        System.out.println("结束时间："+date2);
        return listNew;
//        System.out.println("这个值是："+keys);
//        return keys;
    }
    //将值存入到key中进行查询
    @GetMapping("/likeByOne2")
    private Set test2(String pattern){
        Date date1 = new Date();
        //模糊查询
        Set keys = redisTemplate.keys("*" + pattern + "*");
//        System.out.println("这个值是："+keys);
        Date date2 = new Date();
        System.out.println("结束时间："+date1);
        System.out.println("结束时间："+date2);
        return keys;
    }


}
