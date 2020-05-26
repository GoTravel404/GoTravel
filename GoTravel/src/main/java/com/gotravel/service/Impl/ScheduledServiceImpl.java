package com.gotravel.service.Impl;

import com.gotravel.dto.PlaceCommentRedisDTO;
import com.gotravel.dto.UserPraisePlaceCommentRedisDTO;
import com.gotravel.repository.PlaceCommentRepository;
import com.gotravel.repository.redis.PlaceCommentRedis;
import com.gotravel.repository.redis.UserPraisePlaceCommentRedis;
import com.gotravel.service.ScheduledService;
import com.gotravel.utils.KeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static com.gotravel.enums.DefinedParam.USER_PRAISE_PLACE_COMMENT_PREFIX;

/**
 * @Name: ScheduledServiceImpl
 * @Description:
 * @Author:chenyx
 * @Date: 2020/4/16 21:29
 **/
@Slf4j
@EnableScheduling //1.启动定时任务
@EnableAsync //2.启动多线程,异步事件的支持
@Service
public class ScheduledServiceImpl implements ScheduledService {

    @Autowired
    private PlaceCommentRedis placeCommentRedis;

    @Autowired
    private UserPraisePlaceCommentRedis userPraisePlaceCommentRedis;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PlaceCommentRepository placeCommentRepository;


    /**
     * @Title persistMySqlPlaceCommentFormRedis
     * @Description: 从redis持久化到Mysql的PlaceComment表
     * @param
     * @Return: void
     * @Author: chenyx
     * @Date: 2020/4/16 21:33
     **/
    @Async //基于@Async标注的方法，称之为异步方法；这些方法在执行的时候，将会在独立的线程中被执行，调用者无需等待它的完成，即可继续其他的操作。
    //@Scheduled(cron = "0 0/2 4-23,0-1 * * ?") //一天的5点到次日的2点每1分钟执行一次
    //@Scheduled(cron = "*/15 * 4-23,0-1 * * ?") //一天的5点到次日的2点每15秒执行一次
    @Override
    public void persistMySqlPlaceCommentFormRedis() {

        log.info("【从redis更新Mysql的PlaceComment的数据】");

        List<PlaceCommentRedisDTO> placeCommentRedisDTOList = placeCommentRedis.getAllPlaceCommentFormRedis();

        if (null != placeCommentRedisDTOList) {

            String sql = "update place_comment set praise=? where place_comment_id=?";

            // jdbcTemplate批量更新操作
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {

                    //景点评论id
                    String placeCommentId = placeCommentRedisDTOList.get(i).getPlaceCommentId();

                    //景点评论的点赞数
                    Integer praise = placeCommentRedisDTOList.get(i).getPraise();

                    preparedStatement.setInt(1, praise);
                    preparedStatement.setString(2, placeCommentId);

                }

                @Override
                public int getBatchSize() {
                    return placeCommentRedisDTOList.size();
                }

            });

        }

    }



    /**
     * @Title persistMysqlUserPraisePlaceCommentFormRedis
     * @Description: 从redis持久化到Mysql的UserPraisePlaceComment表
     * @param
     * @Return: void
     * @Author: chenyx
     * @Date: 2020/4/17 16:35
     **/
    // @Async
    //@Scheduled(cron = "0 0/1 4-23,0-1 * * ?") //一天的5点到次日的2点每1分钟执行一次
    //@Scheduled(cron = "*/15 * 4-23,0-1 * * ?") //一天的5点到次日的2点每15秒执行一次
    @Override
    public void persistMysqlUserPraisePlaceCommentFormRedis() {

        log.info("【从redis持久化到Mysql的UserPraisePlaceComment表】");

        List<UserPraisePlaceCommentRedisDTO> userPraisePlaceCommentRedisList = userPraisePlaceCommentRedis.getAllUserPraisePlaceCommentFormRedis();

        if (null != userPraisePlaceCommentRedisList) {

            //先清空user_praise_place_comment表
            jdbcTemplate.execute("delete from user_praise_place_comment");

            String sql = "insert into user_praise_place_comment(id,phone,place_comment_id,place_id) value (?,?,?,?)";

            // jdbcTemplate批量插入操作
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {

                    //id
                    String id = USER_PRAISE_PLACE_COMMENT_PREFIX + KeyUtils.getUniqueKry();

                    //用户的phone
                    String phone = userPraisePlaceCommentRedisList.get(i).getPhone();

                    //景点评论id
                    String placeCommentId = userPraisePlaceCommentRedisList.get(i).getPlaceCommentId();

                    //评论的景点id
                    String place_id = userPraisePlaceCommentRedisList.get(i).getPlaceId();

                    preparedStatement.setString(1, id);
                    preparedStatement.setString(2, phone);
                    preparedStatement.setString(3, placeCommentId);
                    preparedStatement.setString(4, place_id);

                }

                @Override
                public int getBatchSize() {
                    return userPraisePlaceCommentRedisList.size();
                }

            });

        }

    }


}
