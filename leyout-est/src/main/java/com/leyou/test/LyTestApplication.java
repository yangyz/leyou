package com.leyou.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @Author: 98050
 * @Time: 2018-11-28 18:12
 * @Feature:
 */
@SpringBootApplication
public class LyTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(LyTestApplication.class,args);
    }
}
