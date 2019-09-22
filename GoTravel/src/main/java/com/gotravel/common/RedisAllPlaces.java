package com.gotravel.common;

import com.gotravel.model.Place;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @ClassName:RedisAllPlaces
 * @Description:TODO 查询redis缓存里所有的景点信息，redis缓存没有则在数据库查询,同时存放到redis中
 * @Author:chenyx
 * @Date:Create in  2019/9/22 14:15
 **/
@Slf4j
@Component
public class RedisAllPlaces {

    //Redis缓存的所有景点List的key值
    private final static String REDIS_KEY = "AllPlacesList";

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    //注入springboot自动配置好的redisTemplate
    private RedisTemplate<Object, Object> redisTemplate;


    /**
     * @Title getRedisallPlacesList
     * @Description: TODO 查询redis缓存里所有的景点信息，redis缓存没有则在数据库查询,同时存放到redis中
     * @param
     * @return java.util.List
     * @Author: chenyx
     * @Date: 2019/9/21  17:20
     **/
    public List getRedisAllPlacesList() {

        //字符串序列化器
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);

        //查询缓存
        List placesList = redisTemplate.opsForList().range(REDIS_KEY, 0, -1);
        //双重检测锁
        if (0 == placesList.size()) {
            synchronized (this) {  //加锁
                //从redis获取
                placesList = redisTemplate.opsForList().range(REDIS_KEY, 0, -1);
                if (0 == placesList.size()) {   //缓存为空,查询一遍数据库
                    log.info("------------查询数据库------------");
                    Query query = new Query().with(new Sort(Direction.DESC, "praise"));//按按好评度高到低排序
                    placesList = mongoTemplate.find(query, Place.class);
                    //将数据库查询出来的数据，存放到redis中
                    for (Object place : placesList) {
                        redisTemplate.opsForList().rightPush(REDIS_KEY, place);
                    }
                } else {
                    log.info("-------------查询缓存-------------");
                }
            }
        } else {
            log.info("------------查询缓存-------------");
        }
        return placesList;
    }
}
