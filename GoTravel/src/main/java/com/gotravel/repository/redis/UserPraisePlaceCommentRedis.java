package com.gotravel.repository.redis;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gotravel.dto.UserPraisePlaceCommentRedisDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.gotravel.enums.DefinedParam.REDIS_KEY_UserPraisePlaceComment;

/**
 * @Name: UserPraisePlaceCommentRedis
 * @Description:
 * @Author:chenyx
 * @Date: 2020/4/17 16:37
 **/
@Slf4j
@Component
public class UserPraisePlaceCommentRedis {


    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;


    /**
     * @Title getAllUserPraisePlaceCommentFormRedis
     * @Description: 返回所有用户点赞的景点评论
     * @param
     * @Return: java.util.List<com.gotravel.dto.UserPraisePlaceCommentRedisDTO>
     * @Author: chenyx
     * @Date: 2020/4/17 16:41
     **/
    public List<UserPraisePlaceCommentRedisDTO> getAllUserPraisePlaceCommentFormRedis() {

        //返回所有用户点赞的景点评论
        // List<Object> userPraisePlaceCommentRedisList = redisTemplate.opsForHash().values(REDIS_KEY_UserPraisePlaceComment);
        Set<Object> userPraisePlaceCommentRedisSet = redisTemplate.opsForHash().keys(REDIS_KEY_UserPraisePlaceComment);

        List<Object> userPraisePlaceCommentRedisList = new ArrayList<>(userPraisePlaceCommentRedisSet);

        //当为空
        if (CollectionUtils.isEmpty(userPraisePlaceCommentRedisList))
            return null;

        List<UserPraisePlaceCommentRedisDTO> result = new ArrayList<>();

        for (Object o : userPraisePlaceCommentRedisList) {

            try {

                String key = (String) o;

                String[] value = key.split("::");

                UserPraisePlaceCommentRedisDTO userPraisePlaceCommentRedisDTO = new UserPraisePlaceCommentRedisDTO();

                userPraisePlaceCommentRedisDTO.setPhone(value[0]);
                userPraisePlaceCommentRedisDTO.setPlaceId(value[1]);
                userPraisePlaceCommentRedisDTO.setPlaceCommentId(value[2]);

                result.add(userPraisePlaceCommentRedisDTO);

            } catch (Exception e) {

                log.info("【用户点赞的景点评论的key异常】,{}", o);

            }

        }

//        JSONArray array = (JSONArray) JSONArray.toJSON(userPraisePlaceCommentRedisList);
//
//        return JSONObject.parseArray(array.toJSONString(), UserPraisePlaceCommentRedisDTO.class);

        return result;

    }


    /**
     * @Title determineHasKey
     * @Description: 确定key是否存在(该用户是否点赞过评论)
     * @param userPraisePlaceCommentRedisDTOList
     * @Return: java.util.List<java.lang.Boolean>
     * @Author: chenyx
     * @Date: 2020/4/17 19:41
     **/
    public List<Boolean> determineHasKey(List<UserPraisePlaceCommentRedisDTO> userPraisePlaceCommentRedisDTOList) {

        if (userPraisePlaceCommentRedisDTOList.isEmpty())
            return null;

        //管道操作
        List<Object> result = redisTemplate.executePipelined(new RedisCallback<Object>() {

            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {

                redisConnection.openPipeline();

                for (UserPraisePlaceCommentRedisDTO userPraisePlaceCommentRedisDTO : userPraisePlaceCommentRedisDTOList) {

                    String phone = userPraisePlaceCommentRedisDTO.getPhone();

                    String place_id = userPraisePlaceCommentRedisDTO.getPlaceId();

                    String placeCommentId = userPraisePlaceCommentRedisDTO.getPlaceCommentId();

                    String key = phone + "::" + place_id + "::" + placeCommentId;

                    redisConnection.hExists(REDIS_KEY_UserPraisePlaceComment.getBytes(), key.getBytes());


                }

                return null;
            }
        }, redisTemplate.getHashValueSerializer());


        JSONArray array = (JSONArray) JSONArray.toJSON(result);

        return JSONObject.parseArray(array.toJSONString(), Boolean.class);

    }


}
