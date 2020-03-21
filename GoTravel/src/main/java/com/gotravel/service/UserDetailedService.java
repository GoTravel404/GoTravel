package com.gotravel.service;

import java.util.List;
import java.util.Map;

/**
 * @Description: TODO User_Detailed用户详细信息表的Service接口
 * @date 2019年8月10日 下午11:37:11
 */
public interface UserDetailedService {


    /**
     * 用户选择个人标签
     * @param map
     * @return
     */
    void chooseLabel(Map<String, Object> map);


    /**
     * 用户添加个人收藏景点
     * @param phone
     * @param place_id
     */
    int addMyCollection(String phone, String place_id);


    /**
     * 用户删除个人收藏景点
     * @param phone
     * @param place_id
     * @return
     */
    int deleteMyCollection(String phone, String place_id);


    /**
     * 用户查找所有个人收藏的景点
     * @param phone
     * @return
     */
    List<Map<String, Object>> findMyCollections(String phone);


    /**
     * 用户添加个人出行计划
     * @param map
     * @return
     */
    int addMyPlan(Map<String, Object> map);


    /**
     * 用户编辑个人出行计划
     * @param map
     * @return
     */
    int editMyPlan(Map<String, Object> map);


    /**
     * 用户删除个人出行计划
     * @param phone
     * @param time
     * @return
     */
    int deleteMyPlan(String phone, long time);


    /**
     * 用户查找所有出行计划(返回所有出行计划的名称列表)
     * @param phone
     * @return
     */
    List<Map<String, Object>> findMyPlans(String phone);


    /**
     * 用户根据计划制定的时间(time)查询出行计划详情列表
     * @param phone
     * @param time
     * @return
     */
    Map<String, Object> findMyPlanDetailed(String phone, long time);


    /**
     * 用户到达景点后将景点添加到历史出行
     * @param phone
     * @param place_id
     * @return
     */
    int addHistory(String phone, String place_id);


    /**
     * 用户删除历史出行的单个景点
     * @param phone
     * @param time
     * @param date
     * @return
     */
    int deleteHistoryPlace(String phone, long time, String date);


    /**
     * 用户删除历史出行记录
     * @param phone
     * @param date
     * @return
     */
    int deleteHistory(String phone, String date);


    /**
     * 用户查找所有历史出行(返回所有日期的历史出行列表)
     * @param phone
     * @return
     */
    List<Map<String, Object>> findMyHistories(String phone);



    /**
     * 用户根据日期查找某一天的历史出行详情列表
     * @param phone
     * @param date
     * @return
     */
    Map<String, Object> findMyHistoriesDetailed(String phone, String date);


}
