package com.gotravel.repository.nosqldao;


import com.gotravel.entity.UserDetailed;

import java.util.List;

/**
 *
 * @Description: mongodb的User_Detailed表的CURD接口
 *  @date 2019年8月10日 下午9:05:01
 */
public interface UserDetailedDao {


    /**
     * 根据手机phone查找用户的详细信息
     * @param phone
     * @return
     */
    UserDetailed findOne(String phone);


    /**
     * 注册账号添加用户详细信息
     * @param user_detailed
     */
    void addUserDetailed(UserDetailed user_detailed);


    /**
     * 根据phone编辑个人标签
     * @param phone
     * @param hobby
     * @param customization
     * @return
     */
    void editLabel(String phone, List<String> hobby, List<String> customization);


    /**
     * @Title findByPhone
     * @Description:TODO 根据phone提供该用户的个人详细信息
     * @Param [phone]
     * @return User_detailed
     * @Author: 陈一心
     * @Date: 2019/9/8  21:54
     **/
    UserDetailed findByPhone(String phone);


    /**
     * 根据phone添加个人收藏景点
     * @param phone
     * @param place_id
     */
    int addMyCollection(String phone, String place_id);


    /**
     * 根据phone和places_id删除个人收藏景点
     * @param phone
     * @param place_id
     * @return
     */
    int deleteMyCollection(String phone, String place_id);


    /**
     * 根据phone查询所有个人收藏的景点
     * @param phone
     * @return
     */
    UserDetailed findMyCollections(String phone);


    /**
     * 根据phone添加个人出行计划
     * @param phone
     * @param plan_name
     * @param places_id
     * @return
     */
    int addMyPlan(String phone, String plan_name, List<String> places_id);


    /**
     * 根据phone和time修改个人出行计划
     * @param phone
     * @param plan_name
     * @param places_id
     * @param time
     * @return
     */
    int editMyPlan(String phone, String plan_name, List<String> places_id, long time);


    /**
     * 根据phone和time删除个人出行计划
     * @param phone
     * @param time
     * @return
     */
    int deleteMyPlan(String phone, long time);


    /**
     * 根据phone查找所有出行计划
     * @param phone
     * @return
     */
    UserDetailed findMyPlans(String phone);


    /**
     * 根据phone和place_id添加历史出行
     * @param phone
     * @param place_id
     * @return
     */
    int addHistory(String phone, String place_id);


    /**
     * 根据phone,time,date删除历史出行的单个景点
     * @param phone
     * @param time
     * @param date
     * @return
     */
    int deleteHistoryPlace(String phone, long time, String date);


    /**
     * 根据phone和date删除历史出行记录
     * @param phone
     * @param date
     * @return
     */
    int deleteHistory(String phone, String date);


    /**
     * 根据phone查询所有历史出行(返回所有日期的历史出行)
     * @param phone
     * @return
     */
    UserDetailed findMyHistories(String phone);



    /**
     * 根据phone查找用户收藏的所有景点，组成List<String>
     * @param phone
     * @return
     */
    List<String> findMyCollectionsPlaceId(String phone);

}
