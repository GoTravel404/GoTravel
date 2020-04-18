package com.gotravel.repository.redis;

import com.alibaba.fastjson.JSONObject;
import com.gotravel.entity.Label;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import static com.gotravel.enums.DefinedParam.REDIS_KEY_AllPlacesLabel;

/**
 * @Name: RedisLabel
 * @Description:TODO 操作redis的标签数据
 * @Author:chenyx
 * @Date: 2020/2/26 18:38
 **/
@Slf4j
@Component
public class LabelRedis {

    @Autowired
    //注入springboot自动配置好的redisTemplate
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;


    /**
     * @Title findAllLabel
     * @Description: TODO 返回官方的标签，并使用redis存储
     * @param
     * @Return: java.util.List<com.gotravel.background.entity.Label>
     * @Author: chenyx
     * @Date: 2020/2/4 19:38
     **/
    public Label findAllLabel() {

        //查询缓存
        Object obj = redisTemplate.opsForValue().get(REDIS_KEY_AllPlacesLabel);

        JSONObject json = (JSONObject) JSONObject.toJSON(obj);

        Label label= JSONObject.toJavaObject(json, Label.class);

        //双重检测锁
        if(null==label) {

            synchronized (this) {  //加锁
                //从redis获取一下
                 obj = redisTemplate.opsForValue().get(REDIS_KEY_AllPlacesLabel);
                 json = (JSONObject) JSONObject.toJSON(obj);
                 label= JSONObject.toJavaObject(json, Label.class);

                if (null == label) {
                    log.info("【返回官方的标签，并使用redis存储】：查询数据库");

                    //缓存为空,查询一遍数据库
                    label =  mongoTemplate.findAll(Label.class).get(0);

                    //把数据库查询出来的数据，放到redis中
                    redisTemplate.opsForValue().set(REDIS_KEY_AllPlacesLabel, label);

                }else{
                    log.info("【返回官方的标签，并使用redis存储】：查询缓存");
                }
            }

        }

        return label;
    }




}
