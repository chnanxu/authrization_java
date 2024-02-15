package com.chen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@MapperScan("com.chen.mapper")
@EnableCaching
public class Chen_Application {

    public static void main(String[] args) {
        SpringApplication.run(Chen_Application.class, args);
    }


}
