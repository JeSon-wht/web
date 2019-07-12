package org.xij.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.xij.annotation.MybatisMapper;


@SpringBootApplication(scanBasePackages = {"org.xij.web"})
@MapperScan(annotationClass = MybatisMapper.class, basePackages = {"org.xij.web.module"})
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}