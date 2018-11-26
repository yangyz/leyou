package com.leyou.review.dao;

import com.leyou.common.pojo.PageResult;
import com.leyou.review.pojo.Review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;



/**
 * @Author: 98050
 * @Time: 2018-11-26 20:51
 * @Feature:
 */
public interface ReviewDao extends MongoRepository<Review,String> {

    Page<Review> findReviewBySpuId(String spuId, Pageable pageable);
}
