package com.leyou.review.service.impl;

import com.leyou.auth.entity.UserInfo;
import com.leyou.review.bo.RequestParam;
import com.leyou.review.dao.ReviewDao;
import com.leyou.review.interceptor.LoginInterceptor;
import com.leyou.review.pojo.Review;
import com.leyou.review.service.ReviewService;
import com.leyou.utils.IdWorker;

import com.mongodb.bulk.UpdateRequest;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author: 98050
 * @Time: 2018-11-26 15:41
 * @Feature:
 */
@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewDao reviewDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private IdWorker idWorker;


    @Override
    public Review findOne(String id) {
        Review review = reviewDao.findById(id).get();
        return review;
    }

    /**
     * 新增
     *
     * @param review
     */
    @Override
    public void add(Review review) {
        /**
         * 设置主键
         */
        review.set_id(idWorker.nextId() + "");
        review.setPublishtime(new Date());
        review.setComment(0);
        review.setThumbup(0);
        review.setVisits(0);
        if (review.getParentid() != null && !"".equals(review.getParentid())){
            //如果存在上级id，则上级评论数加1，将上级评论的isParent设置为true，浏览量加一
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(review.getParentid()));
            Update update = new Update();
            update.inc("comment",1);
            update.set("isparent",true);
            update.inc("visits",1);
            this.mongoTemplate.updateFirst(query,update,"review");
        }
        reviewDao.save(review);
    }

    /**
     * 修改
     *
     * @param review
     */
    @Override
    public void update(Review review) {
        reviewDao.save(review);
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void deleteById(String id) {
        reviewDao.deleteById(id);
    }

    /**
     * 查询某一商品下的所有顶级评论
     * @param requestParam
     * @return
     */
    @Override
    public Page<Review> findReviewBySpuId(RequestParam requestParam) {
        PageRequest pageRequest = PageRequest.of(requestParam.getPage()-1,requestParam.getDefaultSize());
        return this.reviewDao.findReviewBySpuId(requestParam.getSpuId()+"",pageRequest);
    }

    /**
     * 评论点赞(需要改进)
     * @param id
     */
    @Override
    public boolean updateThumbup(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update = new Update();
        update.inc("thumbup",1);
        UpdateResult result = this.mongoTemplate.updateFirst(query,update,"review");
        return result.isModifiedCountAvailable();
    }

    /**
     * 访问量加一
     * @param id
     */
    @Override
    public boolean updateVisits(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update = new Update();
        update.inc("visits",1);
        UpdateResult result = this.mongoTemplate.updateFirst(query,update,"review");
        return result.isModifiedCountAvailable();
    }
}
