package com.kuang.mapper;

import com.kuang.pojo.Test;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ygl
 * @description
 * @date 2020/12/1 19:57
 */
@Mapper
public interface TestMapper {
    List<Test> findAll();
}
