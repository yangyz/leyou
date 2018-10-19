package com.leyou.service;

/**
 * @Author: 98050
 * @Time: 2018-10-19 09:40
 * @Feature: 页面详情静态化接口
 */
public interface GoodsHtmlService {

    /**
     * 创建html页面
     * @param spuId
     */
    void createHtml(Long spuId);

    /**
     * 新建线程处理页面静态化
     * @param spuId
     */
    void asyncExecute(Long spuId);

}
