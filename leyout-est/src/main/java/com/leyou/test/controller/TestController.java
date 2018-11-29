package com.leyou.test.controller;

import com.leyou.test.config.TestIp;
import com.leyou.test.mapper.BrandMapper;
import com.leyou.test.pojo.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: 98050
 * @Time: 2018-11-28 19:13
 * @Feature:
 */
@RestController
@RefreshScope
@RequestMapping("test")
public class TestController {

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private TestIp testIp;

    @Value("${ip.t.w}")
    private String ip;

    @GetMapping("list")
    public ResponseEntity<List<Brand>> test(){
        System.out.println(testIp.getIp());
        //System.out.println(testIp.getPath());
        List<String> path = Arrays.asList(testIp.getPath().split(" "));
        for (String s : path){
            System.out.println(s);
        }
        return ResponseEntity.ok(brandMapper.selectAll());
    }
}
