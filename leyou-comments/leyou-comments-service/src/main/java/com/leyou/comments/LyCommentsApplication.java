package com.leyou.comments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: 98050
 * @Time: 2018-11-29 15:41
 * @Feature:
 */
@SpringBootApplication
@EnableDiscoveryClient
public class LyCommentsApplication {
    public static void main(String[] args) {
        SpringApplication.run(LyCommentsApplication.class,args);
    }
}
