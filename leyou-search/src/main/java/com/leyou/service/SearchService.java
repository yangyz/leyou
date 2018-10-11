package com.leyou.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leyou.client.CategoryClient;
import com.leyou.client.GoodsClient;
import com.leyou.client.SpecClient;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Specification;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import com.leyou.pojo.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: 98050
 * Time: 2018-10-11 22:59
 * Feature: 数据导入
 */
@Service
public class SearchService {
    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SpecClient specificationClient;

    private ObjectMapper mapper = new ObjectMapper();

    public Goods buildGoods(Spu spu) {
        //1.查询商品分类名称
        List<String> names = this.categoryClient.queryNameByIds(Arrays.asList(spu.getCid1(),spu.getCid2(),spu.getCid3())).getBody();
        //2.查询sku
        List<Sku> skuList = this.goodsClient.querySkuBySpuId(spu.getId());
        //3.查询详情
        SpuDetail spuDetail = this.goodsClient.querySpuDetailBySpuId(spu.getId());
        //4.查询规格参数
        String specificationList = this.specificationClient.querySpecificationsByCid(spu.getCid3()).getBody();
        return null;
    }
}
