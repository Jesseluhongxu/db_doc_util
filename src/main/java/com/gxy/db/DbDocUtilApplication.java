package com.gxy.db;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.gxy.db.mapper")
public class DbDocUtilApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbDocUtilApplication.class, args);
    }

}

