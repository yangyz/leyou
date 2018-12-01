package com.leyou.test.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Author: 98050
 * @Time: 2018-11-29 11:34
 * @Feature:
 */
@RefreshScope
@Configuration
public class TestIp {
    @Value("${ip.t.w}")
    private String ip;

    @Value("${leyou.filter.allowPaths}")
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
