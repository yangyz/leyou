package com.leyou.review;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: 98050
 * @Time: 2018-11-26 14:36
 * @Feature: 评论微服务
 */
@SpringBootApplication
@EnableDiscoveryClient
public class LyReviewApplication {

    public static void main(String[] args) {
        SpringApplication.run(LyReviewApplication.class, args);
    }
}
