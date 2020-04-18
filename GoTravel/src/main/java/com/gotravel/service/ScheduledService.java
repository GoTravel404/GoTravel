package com.gotravel.service;

/**
 * @Name: ScheduledService
 * @Description: 预订执行的方法
 * @Author:chenyx
 * @Date: 2020/4/16 21:28
 **/
public interface ScheduledService {


    /**
     * 从redis持久化到Mysql的PlaceComment表
     */
    void persistMySqlPlaceCommentFormRedis();


    /**
     * 从redis持久化到Mysql的UserPraisePlaceComment表
     */
    void persistMysqlUserPraisePlaceCommentFormRedis();


}
