package com.zhaowk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.zhaowk.mapper")
@EnableScheduling
@EnableSwagger2
public class ZhaoWKBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZhaoWKBlogApplication.class, args);
    }
}
