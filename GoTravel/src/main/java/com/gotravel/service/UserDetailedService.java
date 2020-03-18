package com.gotravel.service;

import java.util.Map;

/**
 * @Description: TODO User_Detailed用户详细信息表的Service接口
 * @date 2019年8月10日 下午11:37:11
 */
public interface UserDetailedService {


	/**
	 * @Title chooseLabel
	 * @Description:TODO 用户选择个人标签
	 * @Param [map]
	 * @return java.lang.String
	 * @Author: 陈一心
	 * @Date: 2019/9/8  22:13
	 **/
     String chooseLabel(Map<String, Object> map);


   /**
    * @Title addMyCollections
    * @Description: TODO 用户添加个人收藏景点
    * @Param [phone, places_id, dateStr]
    * @return java.lang.String
    * @Author: 陈一心
    * @Date: 2019/9/9  23:14
    **/
     String addMyCollections(String phone,String place_id,String time);


    /**
     * @Title deleteMyCollections
     * @Description: TODO 用户删除个人收藏景点
     * @Param [phone, place_id]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:15
     **/
     String deleteMyCollections(String phone,String place_id);


    /**
     * @Title addMyPlan
     * @Description:TODO 用户添加个人出行计划
     * @Param [map]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/8  22:15
     **/
     String addMyPlan(Map<String, Object> map);


    /**
     * @Title editMyPlan
     * @Description:TODO 用户编辑个人出行计划
     * @Param [map]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/8  22:15
     **/
     String editMyPlan(Map<String, Object> map);


    /**
     * @Title deleteMyPlan
     * @Description: TODO 用户删除个人出行计划
     * @Param [phone, time]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:24
     **/
     String deleteMyPlan(String phone,String time);


    /**
     * @Title addHistory
     * @Description:TODO 用户到达景点后将景点添加到历史出行
     * @Param [request]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/8  22:16
     **/
     String addHistory(String phone,String place_id,String time);


    /**
     * @Title deleteHistoryPlace
     * @Description:TODO 用户删除历史出行的单个景点
     * @Param [request]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/8  22:16
     **/
     String deleteHistoryPlace(String phone,String place_id,String date);


    /**
     * @Title deleteHistory
     * @Description: TODO 用户删除历史出行记录
     * @Param [phone, date]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:34
     **/
     String deleteHistory(String phone,String date);


    /**
     * @Title findMyCollections
     * @Description: TODO 用户查找所有个人收藏的景点
     * @Param [phone]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:36
     **/
     String findMyCollections(String phone);


    /**
     * @Title findMyHistories
     * @Description: TODO 用户查找所有历史出行(返回所有日期的历史出行列表)
     * @Param [phone]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:37
     **/
     String findMyHistories(String phone);


    /**
     * @Title findmyhistories_detailed
     * @Description: TODO 用户根据日期查找某一天的历史出行详情列表
     * @Param [phone, date]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:39
     **/
     String findMyHistoriesDetailed(String phone,String date);


    /**
     * @Title findMyPlans
     * @Description: TODO 用户查找所有出行计划(返回所有出行计划的名称列表)
     * @Param [phone]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:40
     **/
     String findMyPlans(String phone);


   /**
    * @Title findmyplans_detailed
    * @Description: TODO 用户根据计划制定的时间(time)查询出行计划详情列表
    * @Param [phone, time]
    * @return java.lang.String
    * @Author: 陈一心
    * @Date: 2019/9/9  23:42
    **/
     String findMyPlansDetailed(String phone,String time);

}
