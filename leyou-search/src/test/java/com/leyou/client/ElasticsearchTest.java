package com.leyou.client;

import com.leyou.LySearchService;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.bo.SpuBo;
import com.leyou.pojo.Goods;
import com.leyou.repository.GoodsRepository;
import com.leyou.service.SearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 98050
 * Time: 2018-10-11 22:13
 * Feature:elasticsearch goods索引创建
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LySearchService.class)
public class ElasticsearchTest {
    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SpuClient spuClient;

    @Autowired
    private SearchService searchService;

    @Test
    public void createIndex(){
        // 创建索引
        this.elasticsearchTemplate.createIndex(Goods.class);
        // 配置映射
        this.elasticsearchTemplate.putMapping(Goods.class);
    }

    @Test
    public void loadData() throws IOException {
        int page = 1;
        int row = 100;

        //分页查询数据
        PageResult<SpuBo> result = this.goodsClient.querySpuByPage(page,row,null,true,null,true);
        List<SpuBo> spus = result.getItems();
        //创建Goods集合
        List<Goods> goodsList = new ArrayList<>();
        //遍历spu
        for (SpuBo spu : spus){
            try {
                Goods goods = this.searchService.buildGoods(spu);
                goodsList.add(goods);
            } catch (IOException e) {
                break;
            }
        }
        this.goodsRepository.saveAll(goodsList);
    }


}
