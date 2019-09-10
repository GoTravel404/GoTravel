package com.gotravel.service;

import java.util.Map;

/**
 * @Description: TODO User_Detailed用户详细信息表的Service接口
 * @date 2019年8月10日 下午11:37:11
 */
public interface UserDetailedService {


	/**
	 * @Title choose_label
	 * @Description:TODO 用户选择个人标签
	 * @Param [map]
	 * @return java.lang.String
	 * @Author: 陈一心
	 * @Date: 2019/9/8  22:13
	 **/
     String choose_label(Map<String, Object> map);

   /**
    * @Title addmycollections
    * @Description: TODO 用户添加个人收藏景点
    * @Param [phone, places_id, dateStr]
    * @return java.lang.String
    * @Author: 陈一心
    * @Date: 2019/9/9  23:14
    **/
     String addmycollections(String phone,int place_id,String time);


    /**
     * @Title deletemycollections
     * @Description: TODO 用户删除个人收藏景点
     * @Param [phone, place_id]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:15
     **/
     String deletemycollections(String phone,int place_id);


    /**
     * @Title addmyplans
     * @Description:TODO 用户添加个人出行计划
     * @Param [map]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/8  22:15
     **/
     String addmyplans(Map<String, Object> map);


    /**
     * @Title editmyplans
     * @Description:TODO 用户编辑个人出行计划
     * @Param [map]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/8  22:15
     **/
     String editmyplans(Map<String, Object> map);


    /**
     * @Title deletemyplans
     * @Description: TODO 用户删除个人出行计划
     * @Param [phone, time]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:24
     **/
     String deletemyplans(String phone,String time);


    /**
     * @Title addhistory
     * @Description:TODO 用户到达景点后将景点添加到历史出行
     * @Param [request]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/8  22:16
     **/
     String addhistory(String phone,int place_id,String time);


    /**
     * @Title deletehistoryplace
     * @Description:TODO 用户删除历史出行的单个景点
     * @Param [request]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/8  22:16
     **/
     String deletehistoryplace(String phone,int place_id,String date);


    /**
     * @Title deletehistory
     * @Description: TODO 用户删除历史出行记录
     * @Param [phone, date]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:34
     **/
     String deletehistory(String phone,String date);


    /**
     * @Title findmycollections
     * @Description: TODO 用户查找所有个人收藏的景点
     * @Param [phone]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:36
     **/
     String findmycollections(String phone);


    /**
     * @Title findmyhistories
     * @Description: TODO 用户查找所有历史出行(返回所有日期的历史出行列表)
     * @Param [phone]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:37
     **/
     String findmyhistories(String phone);


    /**
     * @Title findmyhistories_detailed
     * @Description: TODO 用户根据日期查找某一天的历史出行详情列表
     * @Param [phone, date]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:39
     **/
     String findmyhistories_detailed(String phone,String date);


    /**
     * @Title findmyplans
     * @Description: TODO 用户查找所有出行计划(返回所有出行计划的名称列表)
     * @Param [phone]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:40
     **/
     String findmyplans(String phone);


   /**
    * @Title findmyplans_detailed
    * @Description: TODO 用户根据计划制定的时间(time)查询出行计划详情列表
    * @Param [phone, time]
    * @return java.lang.String
    * @Author: 陈一心
    * @Date: 2019/9/9  23:42
    **/
     String findmyplans_detailed(String phone,String time);

}
