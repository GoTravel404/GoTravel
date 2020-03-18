package com.gotravel.dao.nosqldao;


import com.gotravel.entity.UserDetailed;
import com.gotravel.entity.node.MyHistory;
import com.gotravel.entity.node.MyPlan;

import java.util.Date;
import java.util.List;

/**
 *
 * @Description: mongodb的User_Detailed表的CURD接口
 *  @date 2019年8月10日 下午9:05:01
 */
public interface UserDetailedDao {


    /**
     * @Title findOne
     * @Description:TODO 根据手机phone查找用户的详细信息
     * @Param [phone]
     * @return User_detailed
     * @Author: 陈一心
     * @Date: 2019/9/8  21:53
     **/
    UserDetailed findOne(String phone);


    /**
     * @Title addUserDetailed
     * @Description:TODO 注册账号添加用户详细信息
     * @Param [user_detailed]
     * @return void
     * @Author: 陈一心
     * @Date: 2019/9/8  21:54
     **/
    void addUserDetailed(UserDetailed user_detailed);


    /**
     * @Title edit_label
     * @Description:TODO 根据phone编辑个人标签
     * @Param [phone, hobby, customization]
     * @return int
     * @Author: 陈一心
     * @Date: 2019/9/8  21:54
     **/
    int edit_label(String phone, List<String> hobby, List<String> customization);


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
     * @Title: addmycollections
     * @Description: 根据phone添加个人收藏景点
     * @param    phone, place_id,  time
     * @return int
     * @author 陈一心
     * @date 2019年8月12日 下午3:38:49
     */
    int addMyCollections(String phone, String place_id, Date time);


    /**
     * @Title deleteMyCollections
     * @Description:TODO 根据phone和places_id删除个人收藏景点
     * @Param [phone, place_id]
     * @return int
     * @Author: 陈一心
     * @Date: 2019/9/8  21:54
     **/
    int deleteMyCollections(String phone, String place_id);


    /**
     * @Title addMyPlan
     * @Description:TODO 根据phone添加个人出行计划
     * @Param [phone, plan_name, places_id, time]
     * @return int
     * @Author: 陈一心
     * @Date: 2019/9/8  21:54
     **/
    int addMyPlan(String phone, String plan_name, List<String> places_id, Date time);


    /**
     * @Title editMyPlan
     * @Description:TODO 根据phone和time修改个人出行计划
     * @Param [phone, plan_name, places_id, time]
     * @return int
     * @Author: 陈一心
     * @Date: 2019/9/8  21:55
     **/
    int editMyPlan(String phone, String plan_name, List<String> places_id, Date time);


    /**
     * @Title deleteMyPlan
     * @Description:TODO 根据phone和time删除个人出行计划
     * @Param [phone, time]
     * @return int
     * @Author: 陈一心
     * @Date: 2019/9/8  21:55
     **/
    int deleteMyPlan(String phone, Date time);


    /**
     * @Title addHistory
     * @Description:TODO 根据phone和time(date)添加历史出行
     * @Param [phone, place_id, time]
     * @return int
     * @Author: 陈一心
     * @Date: 2019/9/8  21:55
     **/
    int addHistory(String phone, String place_id, String dateStr);


    /**
     * @Title deleteHistoryPlace
     * @Description:TODO 根据phone和date删除历史出行的单个景点
     * @Param [phone, place_id, date]
     * @return int
     * @Author: 陈一心
     * @Date: 2019/9/8  21:55
     **/
    int deleteHistoryPlace(String phone, String place_id, String date);


    /**
     * @Title deleteHistory
     * @Description:TODO 根据phone和date删除历史出行记录
     * @Param [phone, date]
     * @return int
     * @Author: 陈一心
     * @Date: 2019/9/8  21:56
     **/
    int deleteHistory(String phone, String date);


    /**
     * @Title findMyCollections
     * @Description:TODO 根据phone联表(user_detailed和place)查询所有个人收藏的景点
     * @Param [phone]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/8  21:58
     **/
    String findMyCollections(String phone);


    /**
     * @Title findMyHistories
     * @Description:TODO 根据phone查询所有历史出行(返回所有日期的历史出行)
     * @Param [phone]
     * @return java.util.List<Myhistory>
     * @Author: 陈一心
     * @Date: 2019/9/8  21:59
     **/
    List<MyHistory> findMyHistories(String phone);


    /**
     * @Title findMyHistoriesDetailed
     * @Description:TODO 根据phone和date联表(user_detailed和place)查找某一天的历史出行详情列表
     * @Param [phone, date]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/8  21:59
     **/
    String findMyHistoriesDetailed(String phone, String date);


    /**
     * @Title findMyPlans
     * @Description:TODO 根据phone查找所有出行计划(返回所有出行计划的名称列表)
     * @Param [phone]
     * @return java.util.List<MyPlan>
     * @Author: 陈一心
     * @Date: 2019/9/8  21:59
     **/
    List<MyPlan> findMyPlans(String phone);


    /**
     * @Title findmyplans_detailed
     * @Description:TODO 根据phone和time联表(user_detailed和place)查询出行计划详情列表
     * @Param [phone, time]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/8  21:59
     **/
    String findMyPlansDetailed(String phone, Date time);

    /**
     * @Title findMyCollectionsPlaceId
     * @Description: TODO 根据phone查找用户收藏的所有景点，组成List<String>
     * @param phone 手机号
     * @return java.util.List<java.lang.Integer>
     * @Author: chenyx
     * @Date: 2019/11/8  21:35
     **/
    List<String> findMyCollectionsPlaceId(String phone);

}
