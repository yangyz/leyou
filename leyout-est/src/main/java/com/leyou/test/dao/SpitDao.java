package com.leyou.test.dao;

import com.leyou.test.pojo.Spit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * @Author: 98050
 * @Time: 2018-11-29 13:04
 * @Feature:
 */
public interface SpitDao extends MongoRepository<Spit,String> {
}
