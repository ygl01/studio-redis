package com.kuang.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author ygl
 * @description
 * @date 2020/10/10 19:32
 */
@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private String name;
    private int age;
}
