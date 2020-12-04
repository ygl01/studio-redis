package com.kuang.service;

import com.kuang.pojo.Test1;

import java.util.List;

/**
 * @author ygl
 * @description
 * @date 2020/12/1 19:51
 */
public interface TestService {
    List<Test1> findAll();
    void start1();
    void start2();
}
