package com.kuang.service.impl;

import com.kuang.mapper.TestMapper;
import com.kuang.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ygl
 * @description 批量存取数据
 * @date 2020/12/2 13:45
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public void insertKey(List<String> keys, String value) {
        //批量get数据
        List<Object> list = redisTemplate.executePipelined(new RedisCallback<String>() {
                @Override
                public String doInRedis(RedisConnection connection) throws DataAccessException {
                    for (String key : keys) {
                        connection.get(key.getBytes());
                    }
                    return null;
                }
            }
        );

        //批量set数据
        redisTemplate.executePipelined(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                for (int i=0;i<keys.size();i++) {
                    connection.set(keys.get(i).getBytes(),value.getBytes());
                }
                return null;
            }
        });


    }


}
