package com.gotravel.dao.redis;

import com.alibaba.fastjson.JSONObject;
import com.gotravel.entity.Place;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Name: PlaceRedis
 * @Description:TODO place的redis
 * @Author:chenyx
 * @Date: 2020/3/18 18:40
 **/
@Slf4j
@Component
public class PlaceRedis {

    //Redis缓存的所有景点List的key值
    private final static String REDIS_KEY = "AllPlaces";

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

        //查询缓存
        List placesList = redisTemplate.opsForHash().values(REDIS_KEY);

        //双重检测锁
        if (0 == placesList.size()) {
            synchronized (this) {  //加锁
                //从redis获取
                // placesList = redisTemplate.opsForList().range(REDIS_KEY, 0, -1);
                placesList=redisTemplate.opsForHash().values(REDIS_KEY);

                if (0 == placesList.size()) {   //缓存为空,查询一遍数据库
                    log.info("【查询数据库】");

                    List<Place> placesList1 = mongoTemplate.findAll(Place.class);

                    //将数据库查询出来的数据，存放到redis中
                    //使用redis的Hash存储
                    Map<String, Place> placeMap = new HashMap<>();
                    for (Place place : placesList1) {

                        placeMap.put(String.valueOf(place.getPlace_id()),place);
                    }
                    redisTemplate.opsForHash().putAll(REDIS_KEY,placeMap);

                    placesList=placesList1;

                }
            }
        }

        return placesList;
    }



    /**
     * @Title getARedisPlaceByKey
     * @Description: TODO 获取指定key的景点
     * @param place_id
     * @Return: com.gotravel.background.entity.Place
     * @Author: chenyx
     * @Date: 2020/2/8 21:35
     **/
    public Place getARedisPlaceByKey(String place_id) {

        //查询缓存是否存在key
        if (!redisTemplate.hasKey(REDIS_KEY)) {
            log.info("【获取指定key的景点】：缓存为空,添加缓存");
            getRedisAllPlacesList();
        }

        Object obj = redisTemplate.opsForHash().get(REDIS_KEY, place_id);

        JSONObject json = (JSONObject) JSONObject.toJSON(obj);

        return JSONObject.toJavaObject(json, Place.class);
    }



}
