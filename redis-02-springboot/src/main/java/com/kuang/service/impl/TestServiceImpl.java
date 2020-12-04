package com.kuang.service.impl;

import com.kuang.mapper.TestMapper;
import com.kuang.pojo.Test1;
import com.kuang.service.TestService;
import com.kuang.utils.RandomUtil;
import com.kuang.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author ygl
 * @description
 * @date 2020/12/1 19:56
 */
@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private TestMapper testMapper;

    @Autowired
    private TestService testService;

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RandomUtil randomUtil;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public List<Test1> findAll() {
        return testMapper.findAll();

    }

    @Override
    //定时任务
    @Scheduled(cron = "0 8 0 1-31 * ? ")
    public void start1() {
        System.out.println("开始、、、、");
        Date date1 = new Date();
        List<Test1> all = testService.findAll();
        //获取redis连接  且将当前库中所有key value删除
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
//        connection.flushDb();
//        String str= JSON.toJSONString(all);
//        redisUtil.hset("key01","10001",str);

        System.out.println("大小："+all.size());
        for (int i=0;i<all.size();i++){
            Test1 test = all.get(i);
            String s = test.toString();
            String itemID = randomUtil.getItemID(10);
            redisUtil.hset("key01",itemID,s);
        }
        Date date2 = new Date();
        System.out.println("结束时间："+date1);
        System.out.println("结束时间："+date2);
    }

    @Override
    //定时任务
    @Scheduled(cron = "0 8 1 1-31 * ? ")
    public void start2() {
        Date date1 = new Date();
        List<Test1> all = testService.findAll();
        //获取redis连接  且将当前库中所有key value删除
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        //connection.flushDb();

        System.out.println("大小："+all.size());
        for (int i=0;i<all.size();i++){
            Test1 test = all.get(i);
            String s = test.toString();
            redisUtil.set(s,"你好");
        }
        Date date2 = new Date();
        System.out.println("结束时间："+date1);
        System.out.println("结束时间："+date2);
    }
}
