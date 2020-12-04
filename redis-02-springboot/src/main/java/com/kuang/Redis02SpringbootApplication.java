package com.kuang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Redis02SpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(Redis02SpringbootApplication.class, args);
    }

}
