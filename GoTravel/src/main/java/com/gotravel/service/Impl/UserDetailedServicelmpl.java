package com.gotravel.service.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gotravel.common.Common;
import com.gotravel.dao.nosqldao.UserDetailedDao;
import com.gotravel.entity.node.MyHistory;
import com.gotravel.entity.node.MyPlan;
import com.gotravel.service.UserDetailedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: TODO User_Detailed用户信息详细表的service的实现类
 * @date 2019年8月4日 下午8:36:35
 */
@Service("userDetailedService")
@Transactional
public class UserDetailedServicelmpl implements UserDetailedService {

    @Autowired
    private UserDetailedDao userDetailedDao;

    /**
     * @Title chooseLabel
     * @Description:TODO 用户选择个人标签
     * @Param [map] phone,hobby(List<String> ),customization(List<String> )
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/8  22:18
     **/
    @SuppressWarnings("unchecked")
    @Override
    public String chooseLabel(Map<String, Object> map) {

        List<String> hobby = (List<String>) map.get("hobby");
        List<String> customization = (List<String>) map.get("customization");
        String phone = (String) map.get("phone");

        //编辑数据库
        int modifiedCount = userDetailedDao.edit_label(phone, hobby, customization);

        return Common.sendMessage(modifiedCount, "选择");
    }


    /**
     * @Title addMyCollections
     * @Description: TODO 用户添加个人收藏景点
     * @Param [phone, places_id, dateStr]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:14
     **/
    @Override
    public String addMyCollections(String phone,String place_id,String time) {

        //时间转换
        Date toTime = Common.TimeConversion(time);

        //收藏添加到数据库
        int modifiedCount = userDetailedDao.addMyCollections(phone, place_id, toTime);

        return Common.sendMessage(modifiedCount, "收藏");
    }


    /**
     * @Title deleteMyCollections
     * @Description: TODO 用户删除个人收藏景点
     * @Param [phone, place_id]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:15
     **/
    @Override
    public String deleteMyCollections(String phone,String place_id) {

        //数据库删除收藏
        int modifiedCount = userDetailedDao.deleteMyCollections(phone, place_id);

        return Common.sendMessage(modifiedCount, "删除");
    }


    /**
     * @Title addMyPlan
     * @Description:TODO 用户添加个人出行计划
     * @Param [map] phone	plan_name  places_id(List<Integer>)	time
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/8  22:19
     **/
    @SuppressWarnings("unchecked")
    @Override
    public String addMyPlan(Map<String, Object> map) {

        String phone = (String) map.get("phone");
        String plan_name = (String) map.get("plan_name");
        List<String> places_id = (List<String>) map.get("places_id");

        String dateStr = (String) map.get("time");
        //时间转换
        Date time = Common.TimeConversion(dateStr);

        //数据库添加个人出行计划
        int modifiedCount = userDetailedDao.addMyPlan(phone, plan_name, places_id, time);

        return Common.sendMessage(modifiedCount, "添加");
    }


    /**
     * @Title editmyplans
     * @Description:TODO 用户编辑个人出行计划
     * @Param [map] phone,plan_name,places_id(List<Integer>),	time
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/8  22:19
     **/
    @SuppressWarnings("unchecked")
    @Override
    public String editMyPlan(Map<String, Object> map) {

        String phone = (String) map.get("phone");
        String plan_name = (String) map.get("plan_name");
        List<String> places_id = (List<String>) map.get("places_id");
        String dateStr = (String) map.get("time");

        //时间转换
        Date time = Common.TimeConversion(dateStr);

        //数据库修改个人出行计划
        int modifiedCount = userDetailedDao.editMyPlan(phone, plan_name, places_id, time);

        return Common.sendMessage(modifiedCount, "修改");
    }


    /**
     * @Title deleteMyPlan
     * @Description: TODO 用户删除个人出行计划
     * @Param [phone, time]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:21
     **/
    @Override
    public String deleteMyPlan(String phone,String time) {

        //时间转换
        Date toTime = Common.TimeConversion(time);

        //数据库删除个人出行计划
        int modifiedCount = userDetailedDao.deleteMyPlan(phone, toTime);

        return Common.sendMessage(modifiedCount, "删除");
    }


    /**
     * @Title addHistory
     * @Description: TODO 用户到达景点后将景点添加到历史出行
     * @Param [phone, place_id, time]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:27
     **/
    @Override
    public String addHistory(String phone,String place_id,String time) {

        //数据库添加个人历史出行
        int modifiedCount = userDetailedDao.addHistory(phone, place_id, time);

        return Common.sendMessage(modifiedCount, "添加");
    }


    /**
     * @Title deleteHistoryPlace
     * @Description: TODO 用户删除历史出行的单个景点
     * @Param [phone, place_id, date]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:30
     **/
    @Override
    public String deleteHistoryPlace(String phone,String place_id,String date) {

        //数据库删除历史出行的单个景点
        int modifiedCount = userDetailedDao.deleteHistoryPlace(phone, place_id, date);

        return Common.sendMessage(modifiedCount, "删除");
    }


    /**
     * @Title deleteHistory
     * @Description: TODO 用户删除历史出行记录
     * @Param [phone, date]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:34
     **/
    @Override
    public String deleteHistory(String phone,String date) {

        //数据库删除历史出行记录
        int modifiedCount = userDetailedDao.deleteHistory(phone, date);

        return Common.sendMessage(modifiedCount, "删除");
    }


     /**
      * @Title findMyCollections
      * @Description: TODO 用户查找所有个人收藏的景点
      * @Param [phone]
      * @return java.lang.String
      * @Author: 陈一心
      * @Date: 2019/9/9  23:36
      **/
    @Override
    public String findMyCollections(String phone) {

        //数据库联表(user_detailed和place)查询个人收藏景点
        return userDetailedDao.findMyCollections(phone);
    }


    /**
     * @Title findMyHistories
     * @Description: TODO 用户查找所有历史出行(返回所有日期的历史出行列表)
     * @Param [phone]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:37
     **/
    @Override
    public String findMyHistories(String phone) {

        //数据库查询所有历史出行
        List<MyHistory> myhistories = userDetailedDao.findMyHistories(phone);
        //封装返回出行日期(date) , 浏览景点数量(place_number)的json数组类型数据

        JSONArray jsonArray = new JSONArray();
        for (Object myhistoryObject : myhistories) {
            MyHistory myhistory = (MyHistory) myhistoryObject;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("date", myhistory.getDate());
            jsonObject.put("place_number", myhistory.getPlaces_time().size());
            jsonArray.add(jsonObject);
        }
        //返回json数组类型
        return jsonArray.toString();
    }


   /**
    * @Title findMyHistoriesDetailed
    * @Description: TODO 用户根据日期查找某一天的历史出行详情列表
    * @Param [phone, date]
    * @return java.lang.String
    * @Author: 陈一心
    * @Date: 2019/9/9  23:39
    **/
    @Override
    public String findMyHistoriesDetailed(String phone,String date) {

        //数据库返回历史出行详情
        return userDetailedDao.findMyHistoriesDetailed(phone, date);
    }


    /**
     * @Title findmyplans
     * @Description: TODO 用户查找所有出行计划(返回所有出行计划的名称列表)
     * @Param [phone]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:41
     **/
    @Override
    public String findMyPlans(String phone) {

        //数据库查询所有出行计划的名称列表
        List<MyPlan> myplans = userDetailedDao.findMyPlans(phone);
        //封装返回出行计划名称(plan_name) , 计划时间(time)的json数组类型数据
        JSONArray jsonArray = new JSONArray();
        for (Object myplanObject : myplans) {
            MyPlan myplan = (MyPlan) myplanObject;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("plan_name", myplan.getPlan_name());
            jsonObject.put("time", myplan.getTime());
            jsonArray.add(jsonObject);
        }
        //返回json数组类型
        return jsonArray.toString();
    }


    /**
     * @Title findMyPlansDetailed
     * @Description: TODO 用户根据计划制定的时间(time)查询出行计划详情列表
     * @Param [phone, time]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:42
     **/
    @Override
    public String findMyPlansDetailed(String phone,String time) {

        //时间转换
        Date totime = Common.TimeConversion(time);

        //数据库查找个人出行计划详情
        return userDetailedDao.findMyPlansDetailed(phone, totime);

    }


}
