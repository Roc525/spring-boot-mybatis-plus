package com.mbatisplus.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mbatisplus.mybatis.mapper")
//@Import({MultipartResolverConfig.class})
//@ComponentScan(basePackages = {"com.mbatisplus.mybatis.config"})
public class MybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisApplication.class, args);
    }

}
