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
     * 添加秒杀商品
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
        if (list!=null || list.size() < 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(list);
    }


    @GetMapping("seckill/{orderId}")
    public ResponseEntity<Long> seckillOrder(@PathVariable("orderId") Long orderId){
        //1.判断用户是否登录

        //2.判断库存
        //3.判断是否秒杀成
        //4.秒杀成功的话就下订单，减库存
        return null;

    }
}
