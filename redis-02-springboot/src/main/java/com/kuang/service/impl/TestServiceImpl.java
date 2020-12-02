package com.kuang.service.impl;

import com.kuang.mapper.TestMapper;
import com.kuang.pojo.Test;
import com.kuang.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Override
    public List<Test> findAll() {
        return testMapper.findAll();

    }
}
