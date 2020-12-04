package com.kuang.controller;

import com.kuang.service.RedisService;
import com.kuang.service.TestService;
import com.kuang.utils.RandomUtil;
import com.kuang.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    //存入value中，是hash类型，查询数据库，导入到redis中，采用逐条查询
    @GetMapping("/intoRedis")
    private void intoRedis(){
        testService.start1();
    }
    //将值存入到key中，是set类型，导入到redis中，采用逐条查询
    @GetMapping("/intoRedis2")
    private void intoRedis2(){
        testService.start2();
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
            //用假设前端的value和对象下的value相比较，相同则添加到list集合中，然后返回
            if (o.matches(".*"+pattern+".*")){
                System.out.println("值："+o);
//                String key01 =(String) redisUtil.hget("key01", o);
//                System.out.println("key01:"+key01);
                listNew.add(o);
            }
        }
        System.out.println("总条数："+list.size());
        Date date2 = new Date();
        System.out.println("结束时间："+date1);
        System.out.println("结束时间："+date2);
        return listNew;
    }
    //将值存入到key中进行查询
    @GetMapping("/likeByOne2")
    private Set test2(String pattern){
        Date date1 = new Date();
        //模糊查询
        Set keys = redisTemplate.keys("*" + pattern + "*");
        Date date2 = new Date();
        System.out.println("结束时间："+date1);
        System.out.println("结束时间："+date2);
        return keys;
    }


}
