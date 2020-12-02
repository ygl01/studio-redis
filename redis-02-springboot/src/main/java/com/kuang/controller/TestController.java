package com.kuang.controller;

import com.kuang.pojo.Test;
import com.kuang.service.TestService;
import com.kuang.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

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
    private RedisUtil redisUtil;

    @Autowired
    private TestService testService;

    @GetMapping("/intoRedis")
    private void intoRedis(){
        List<Test> all = testService.findAll();
        System.out.println("大小："+all.size());
        for (int i=0;i<all.size();i++){
            Test test = all.get(i);
            String s = test.toString();
            redisUtil.set(s,i);
        }
        System.out.println("结束");
    }

    @GetMapping("/test")
    private void test(){
        redisUtil.set("key01", "value01");
        System.out.println(redisUtil.get("key01"));
        
    }

    @GetMapping("/likeByOne")
    private Set test1(String pattern){
        Set keys = redisTemplate.keys("*" + pattern + "*");
        System.out.println("这个值是："+keys);
        return keys;
    }


}
