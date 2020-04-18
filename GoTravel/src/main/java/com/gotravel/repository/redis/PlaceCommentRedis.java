package com.gotravel.repository.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gotravel.dto.PlaceCommentRedisDTO;
import com.gotravel.dto.UserPraisePlaceCommentRedisDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.gotravel.enums.DefinedParam.REDIS_KEY_PlaceCommentPraise;
import static com.gotravel.enums.DefinedParam.REDIS_KEY_UserPraisePlaceComment;

/**
 * @Name: PlaceCommentRedis
 * @Description:
 * @Author:chenyx
 * @Date: 2020/4/15 19:06
 **/
@Slf4j
@Component
public class PlaceCommentRedis {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;


    /**
     * @Title getAllPlaceCommentFormRedis
     * @Description: 获取PlaceComment的所有redis缓存
     * @param
     * @Return: List<PlaceCommentRedisDTO>
     * @Author: chenyx
     * @Date: 2020/4/16 23:29
     **/
    public List<PlaceCommentRedisDTO> getAllPlaceCommentFormRedis() {

        //返回所有景点评论的点赞数缓存
        List<Object> placeCommentRedisList = redisTemplate.opsForHash().values(REDIS_KEY_PlaceCommentPraise);

        //当为空
        if (placeCommentRedisList.isEmpty())
            return null;

        JSONArray array = (JSONArray) JSONArray.toJSON(placeCommentRedisList);

        return JSONObject.parseArray(array.toJSONString(), PlaceCommentRedisDTO.class);

    }



    /**
     * @Title increasePlaceCommentPraise
     * @Description: 用户点赞景点评论
     * @param phone
     * @param place_id
     * @param placeCommentId
     * @Return: void
     * @Author: chenyx
     * @Date: 2020/4/15 20:41
     **/
    public synchronized void increasePlaceCommentPraise(String phone, String place_id, String placeCommentId) {

        //返回景点评论的点赞数缓存
        Object obj = redisTemplate.opsForHash().get(REDIS_KEY_PlaceCommentPraise, placeCommentId);

        PlaceCommentRedisDTO placeCommentRedisDTO;

        //该景点评论之前没有被点赞
        if (null == obj) {

            placeCommentRedisDTO = new PlaceCommentRedisDTO();
            placeCommentRedisDTO.setPlaceCommentId(placeCommentId);
            placeCommentRedisDTO.setPraise(1);

        } else {

            JSONObject json = (JSONObject) JSONObject.toJSON(obj);
            placeCommentRedisDTO = JSONObject.toJavaObject(json, PlaceCommentRedisDTO.class);

            //该景点评论的点赞数+1
            placeCommentRedisDTO.setPraise(placeCommentRedisDTO.getPraise() + 1);
        }


        //记录用户点赞的景点评论
        UserPraisePlaceCommentRedisDTO userPraisePlaceCommentRedisDTO = new UserPraisePlaceCommentRedisDTO();
        userPraisePlaceCommentRedisDTO.setPhone(phone);
        userPraisePlaceCommentRedisDTO.setPlaceCommentId(placeCommentId);
        userPraisePlaceCommentRedisDTO.setPlaceId(place_id);


        //管道操作
        redisTemplate.executePipelined(new RedisCallback<Object>() {

            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {

                redisConnection.openPipeline();

                //缓存景点评论点赞数+1
                redisConnection.hSet(REDIS_KEY_PlaceCommentPraise.getBytes(), placeCommentId.getBytes(), JSON.toJSONBytes(placeCommentRedisDTO));

                //缓存记录用户点赞的景点评论
                redisConnection.hSet(REDIS_KEY_UserPraisePlaceComment.getBytes(), (phone + "::" + place_id + "::" + placeCommentId).getBytes(), JSON.toJSONBytes(userPraisePlaceCommentRedisDTO));

                return null;
            }
        }, redisTemplate.getHashValueSerializer());


    }



    /**
     * @Title decreasePlaceCommentPraise
     * @Description: 用户取消景点评论点赞
     * @param phone
     * @param place_id
     * @param placeCommentId
     * @Return: void
     * @Author: chenyx
     * @Date: 2020/4/16 20:35
     **/
    public synchronized void decreasePlaceCommentPraise(String phone, String place_id, String placeCommentId) {

        //返回景点评论的点赞数缓存
        Object obj = redisTemplate.opsForHash().get(REDIS_KEY_PlaceCommentPraise, placeCommentId);

        PlaceCommentRedisDTO placeCommentRedisDTO;

        //该景点评论没有被点赞
        if (null != obj) {

            JSONObject json = (JSONObject) JSONObject.toJSON(obj);
            placeCommentRedisDTO = JSONObject.toJavaObject(json, PlaceCommentRedisDTO.class);

            //该景点评论的点赞数 -1
            placeCommentRedisDTO.setPraise(placeCommentRedisDTO.getPraise() - 1);


            //管道操作
            redisTemplate.executePipelined(new RedisCallback<Object>() {

                @Override
                public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {

                    redisConnection.openPipeline();

                    //点赞数不为0
                    if (0 != placeCommentRedisDTO.getPraise()) {
                        //缓存景点评论点赞数 -1
                        redisConnection.hSet(REDIS_KEY_PlaceCommentPraise.getBytes(), placeCommentId.getBytes(), JSON.toJSONBytes(placeCommentRedisDTO));
                    } else {
                        //删除缓存景点评论点赞数
                        redisConnection.hDel(REDIS_KEY_PlaceCommentPraise.getBytes(), placeCommentId.getBytes());
                    }

                    //删除缓存记录用户点赞的景点评论
                    redisConnection.hDel(REDIS_KEY_UserPraisePlaceComment.getBytes(), (phone + "::" + place_id + "::" + placeCommentId).getBytes());

                    return null;
                }
            }, redisTemplate.getHashValueSerializer());

        }

    }


    /**
     * @Title deletePlaceComment
     * @Description: 删除redis中关于该景点评论的点赞数和点赞用户
     * @param place_id
     * @param placeCommentId
     * @Return: void
     * @Author: chenyx
     * @Date: 2020/4/18 14:14
     **/
    public void deletePlaceComment(String place_id, String placeCommentId) {

        List<String> keyList = new ArrayList<>();

        //模糊匹配key
        try {
            Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(REDIS_KEY_UserPraisePlaceComment, ScanOptions.scanOptions().match("*::" + place_id + "::" + placeCommentId).build());

            while (cursor.hasNext()) {

                Map.Entry<Object, Object> entry = cursor.next();

                keyList.add((String) entry.getKey());

                log.info("key:" + entry.getKey());
            }

            //关闭cursor
            cursor.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


        //管道操作
        redisTemplate.executePipelined(new RedisCallback<Object>() {

            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {

                redisConnection.openPipeline();

                //删除缓存景点评论点赞数
                redisConnection.hDel(REDIS_KEY_PlaceCommentPraise.getBytes(), placeCommentId.getBytes());

                //删除缓存记录用户点赞的景点评论
                if (!keyList.isEmpty()) {
                    for (String key : keyList) {
                        redisConnection.hDel(REDIS_KEY_UserPraisePlaceComment.getBytes(), key.getBytes());
                    }
                }

                return null;
            }

        }, redisTemplate.getHashValueSerializer());


    }


}
