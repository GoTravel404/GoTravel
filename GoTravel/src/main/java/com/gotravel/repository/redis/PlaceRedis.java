package com.gotravel.repository.redis;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gotravel.entity.Place;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gotravel.enums.DefinedParam.REDIS_KEY_AllPlaces;

/**
 * @Name: PlaceRedis
 * @Description:TODO place的redis
 * @Author:chenyx
 * @Date: 2020/3/18 18:40
 **/
@Slf4j
@Component
public class PlaceRedis {


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
    public List<Place> getRedisAllPlacesList() {

        //查询缓存
        List<Object> placesList = redisTemplate.opsForHash().values(REDIS_KEY_AllPlaces);
        JSONArray array = (JSONArray) JSONArray.toJSON(placesList);
        List<Place> placesList_1 = JSONObject.parseArray(array.toJSONString(), Place.class);

        //双重检测锁
        if (0 == placesList.size()) {
            synchronized (this) {  //加锁

                //从redis获取
                placesList = redisTemplate.opsForHash().values(REDIS_KEY_AllPlaces);
                array = (JSONArray) JSONArray.toJSON(placesList);
                placesList_1 = JSONObject.parseArray(array.toJSONString(), Place.class);

                if (0 == placesList.size()) {   //缓存为空,查询一遍数据库
                    log.info("【查询数据库】");

                    List<Place> placesList_2 = mongoTemplate.findAll(Place.class);

                    //将数据库查询出来的数据，存放到redis中
                    //使用redis的Hash存储
                    Map<String, Place> placeMap = new HashMap<>();
                    for (Place place : placesList_2) {

                        placeMap.put(String.valueOf(place.getPlace_id()), place);
                    }
                    redisTemplate.opsForHash().putAll(REDIS_KEY_AllPlaces, placeMap);

                    return placesList_2;

                }

            }
        }

        return placesList_1;
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
        if (!redisTemplate.hasKey(REDIS_KEY_AllPlaces)) {
            log.info("【获取指定key的景点】：缓存为空,添加缓存");
            getRedisAllPlacesList();
        }

        Object obj = redisTemplate.opsForHash().get(REDIS_KEY_AllPlaces, place_id);

        JSONObject json = (JSONObject) JSONObject.toJSON(obj);

        return JSONObject.toJavaObject(json, Place.class);
    }


    /**
     * @Title getPlaceListByPipeline
     * @Description: TODO 根据通道一次性返回多个景点
     * @param keyList
     * @Return: java.util.List<java.lang.Object>
     * @Author: chenyx
     * @Date: 2020/3/6 12:13
     **/
    public List<Place> getPlaceListByPipeline(List<String> keyList) {

        //查询缓存是否存在key
        if (!redisTemplate.hasKey(REDIS_KEY_AllPlaces)) {
            log.info("【根据通道一次性返回多个景点】：缓存为空,添加缓存");
            getRedisAllPlacesList();
        }

        List<Object> placeList = redisTemplate.executePipelined(new RedisCallback<Place>() {

            @Override
            public Place doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.openPipeline();
                for (String key : keyList) {
                    redisConnection.hGet(REDIS_KEY_AllPlaces.getBytes(), key.getBytes());
                }
                return null;
            }
        }, redisTemplate.getHashValueSerializer());


        JSONArray array = (JSONArray) JSONArray.toJSON(placeList);

        return JSONObject.parseArray(array.toJSONString(), Place.class);


    }


}
