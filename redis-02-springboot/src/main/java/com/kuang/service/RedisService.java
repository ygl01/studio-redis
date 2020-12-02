package com.kuang.service;

import java.util.List;

/**
 * @author ygl
 * @description
 * @date 2020/12/2 13:44
 */
public interface RedisService {
    void insertKey(List<String> keys,String value);
}
