package com.leyou.review.service;

import com.leyou.common.pojo.PageResult;
import com.leyou.review.bo.RequestParam;
import com.leyou.review.pojo.Review;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Author: 98050
 * @Time: 2018-11-26 15:40
 * @Feature:
 */
public interface ReviewService {

    /**
     * 根据评论id查询
     *
     * @param id
     * @return
     */
    Review findOne(String id);

    /**
     * 新增评论
     *
     * @param review
     */
    void add(Review review);

    /**
     * 修改评论
     *
     * @param review
     */
    void update(Review review);

    /**
     * 删除指定评论
     *
     * @param id
     */
    void deleteById(String id);

    /**
     * 查询某一商品下的所有顶级评论
     * @param requestParam
     * @return
     */
    Page<Review> findReviewBySpuId(RequestParam requestParam);

    /**
     * 评论点赞
     * @param id
     */
    boolean updateThumbup(String id);

    /**
     * 浏览量增1
     * @param id
     */
    boolean updateVisits(String id);
}
