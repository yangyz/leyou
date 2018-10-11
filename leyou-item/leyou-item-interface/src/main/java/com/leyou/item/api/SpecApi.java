package com.leyou.item.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: 98050
 * Time: 2018-10-11 20:05
 * Feature:
 */
public interface SpecApi {
    /**
     * 根据分类id查询规格参数
     * @param cid
     * @return
     */
    @GetMapping("cid")
    ResponseEntity<String> querySpecificationsByCid(@RequestParam("cid")Long cid);
}
