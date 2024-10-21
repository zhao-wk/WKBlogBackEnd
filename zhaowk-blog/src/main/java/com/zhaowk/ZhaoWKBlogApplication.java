package com.zhaowk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zhaowk.mapper")
public class ZhaoWKBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZhaoWKBlogApplication.class, args);
    }
}
