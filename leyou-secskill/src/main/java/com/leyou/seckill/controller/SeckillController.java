package com.leyou.seckill.controller;


import com.leyou.seckill.service.SeckillService;
import com.leyou.seckill.vo.SeckillGoods;
import com.leyou.seckill.vo.SeckillParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: 98050
 * @Time: 2018-11-10 16:57
 * @Feature:
 */
@RestController
@RequestMapping
public class SeckillController {

    @Autowired
    private SeckillService seckillService;

    /**
     * 添加秒杀商品(后台)
     * @param seckillParameter
     * @return
     */
    @PostMapping("addSeckill")
    public ResponseEntity<Boolean> addSeckillGoods(@RequestBody SeckillParameter seckillParameter){
        if (seckillParameter != null){
            this.seckillService.addSeckillGoods(seckillParameter);
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok().build();
    }

    /**
     * 查询秒杀商品
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<SeckillGoods>> querySeckillGoods(){
        List<SeckillGoods> list = this.seckillService.querySeckillGoods();
        if (list == null || list.size() < 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(list);
    }


    @PostMapping("seck")
    public ResponseEntity<Long> seckillOrder(@RequestBody SeckillGoods seckillGoods){
        //1.创建订单
        Long id = this.seckillService.createOrder(seckillGoods);
        //2.判断秒杀是否成功
        if (id == null){
           return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
        }
        return ResponseEntity.ok(id);
    }
}
