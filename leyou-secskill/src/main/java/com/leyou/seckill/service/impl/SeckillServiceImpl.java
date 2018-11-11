package com.leyou.seckill.service.impl;

import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Stock;
import com.leyou.seckill.client.GoodsClient;
import com.leyou.seckill.mapper.SeckillMapper;
import com.leyou.seckill.mapper.StockMapper;
import com.leyou.seckill.service.SeckillService;
import com.leyou.seckill.vo.SeckillGoods;
import com.leyou.seckill.vo.SeckillParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author: 98050
 * @Time: 2018-11-10 16:57
 * @Feature:
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private SeckillMapper seckillMapper;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private StockMapper stockMapper;

    /**
     * 添加秒杀商品
     * @param seckillParameter
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addSeckillGoods(SeckillParameter seckillParameter) {

        //1.根据spu_id查询商品
        Long id = seckillParameter.getId();
        Sku sku = goodsClient.querySkuById(id);
        //2.插入到秒杀商品表中
        SeckillGoods seckillGoods = new SeckillGoods();
        seckillGoods.setEnable(true);
        seckillGoods.setEndTime(seckillParameter.getEndTime());
        seckillGoods.setImage(sku.getImages());
        seckillGoods.setSkuId(sku.getId());
        seckillGoods.setStock(seckillParameter.getCount());
        seckillGoods.setTitle(sku.getTitle());
        seckillGoods.setSeckillPrice(sku.getPrice()*seckillParameter.getDiscount());
        this.seckillMapper.insert(seckillGoods);
        //3.更新对应的库存信息，tb_stock
        Stock stock = stockMapper.selectByPrimaryKey(sku.getId());
        stock.setSeckillStock(seckillParameter.getCount());
        stock.setSeckillTotal(seckillParameter.getCount());
        stock.setStock(stock.getStock() - seckillParameter.getCount());
        this.stockMapper.updateByPrimaryKeySelective(stock);

    }

    /**
     * 查询秒杀商品
     * @return
     */
    @Override
    public List<SeckillGoods> querySeckillGoods() {
        Example example = new Example(SeckillGoods.class);
        example.createCriteria().andEqualTo("enable",true);
        return this.seckillMapper.selectByExample(example);
    }
}
