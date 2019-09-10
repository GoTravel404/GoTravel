package com.gotravel.service.Impl;

import com.gotravel.common.Common;
import com.gotravel.dao.nosqldao.UserDetailedDao;
import com.gotravel.pojo.Myhistory;
import com.gotravel.pojo.Myplan;
import com.gotravel.service.UserDetailedService;
import org.json.JSONArray;
import org.json.JSONObject;
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
public class UserDetailedServicelmpl<E> implements UserDetailedService {

    @Autowired
    private UserDetailedDao userDetailedDao;

    /**
     * @Title choose_label
     * @Description:TODO 用户选择个人标签
     * @Param [map] phone,hobby(List<String> ),customization(List<String> )
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/8  22:18
     **/
    @SuppressWarnings("unchecked")
    @Override
    public String choose_label(Map<String, Object> map) {
        // TODO Auto-generated method stub
        List<String> hobby = (List<String>) map.get("hobby");
        List<String> customization = (List<String>) map.get("customization");
        String phone = (String) map.get("phone");
        //编辑数据库
        int modifiedcount = userDetailedDao.edit_label(phone, hobby, customization);
        return Common.sendMessage(modifiedcount, "选择");
    }


    /**
     * @Title addmycollections
     * @Description: TODO 用户添加个人收藏景点
     * @Param [phone, places_id, dateStr]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:14
     **/
    @Override
    public String addmycollections(String phone,int place_id,String time) {
        // TODO Auto-generated method stub
        //时间转换
        Date totime = Common.TimeConversion(time);
        //收藏添加到数据库
        int modifiedcount = userDetailedDao.addmycollections(phone, place_id, totime);
        return Common.sendMessage(modifiedcount, "收藏");
    }


    /**
     * @Title deletemycollections
     * @Description: TODO 用户删除个人收藏景点
     * @Param [phone, place_id]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:15
     **/
    @Override
    public String deletemycollections(String phone,int place_id) {
        // TODO Auto-generated method stub
        //数据库删除收藏
        int modifiedcount = userDetailedDao.deletemycollections(phone, place_id);
        return Common.sendMessage(modifiedcount, "删除");
    }


    /**
     * @Title addmyplans
     * @Description:TODO 用户添加个人出行计划
     * @Param [map] phone	plan_name  places_id(List<Integer>)	time
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/8  22:19
     **/
    @SuppressWarnings("unchecked")
    @Override
    public String addmyplans(Map<String, Object> map) {
        // TODO Auto-generated method stub
        String phone = (String) map.get("phone");
        String plan_name = (String) map.get("plan_name");
        List<Integer> places_id = (List<Integer>) map.get("places_id");
        String dateStr = (String) map.get("time");
        //时间转换
        Date time = Common.TimeConversion(dateStr);
        //数据库添加个人出行计划
        int modifiedcount = userDetailedDao.addmyplans(phone, plan_name, places_id, time);
        return Common.sendMessage(modifiedcount, "添加");
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
    public String editmyplans(Map<String, Object> map) {
        // TODO Auto-generated method stub
        String phone = (String) map.get("phone");
        String plan_name = (String) map.get("plan_name");
        List<Integer> places_id = (List<Integer>) map.get("places_id");
        String dateStr = (String) map.get("time");
        //时间转换
        Date time = Common.TimeConversion(dateStr);
        //数据库修改个人出行计划
        int modifiedcount = userDetailedDao.editmyplans(phone, plan_name, places_id, time);
        return Common.sendMessage(modifiedcount, "修改");
    }


    /**
     * @Title deletemyplans
     * @Description: TODO 用户删除个人出行计划
     * @Param [phone, time]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:21
     **/
    @Override
    public String deletemyplans(String phone,String time) {
        // TODO Auto-generated method stub
        //时间转换
        Date totime = Common.TimeConversion(time);
        //数据库删除个人出行计划
        int modifiedcount = userDetailedDao.deletemyplans(phone, totime);
        return Common.sendMessage(modifiedcount, "删除");
    }


    /**
     * @Title addhistory
     * @Description: TODO 用户到达景点后将景点添加到历史出行
     * @Param [phone, place_id, time]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:27
     **/
    @Override
    public String addhistory(String phone,int place_id,String time) {
        // TODO Auto-generated method stub
        //数据库添加个人历史出行
        int modifiedcount = userDetailedDao.addhistory(phone, place_id, time);
        return Common.sendMessage(modifiedcount, "添加");
    }


    /**
     * @Title deletehistoryplace
     * @Description: TODO 用户删除历史出行的单个景点
     * @Param [phone, place_id, date]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:30
     **/
    @Override
    public String deletehistoryplace(String phone,int place_id,String date) {
        // TODO Auto-generated method stub
        //数据库删除历史出行的单个景点
        int modifiedcount = userDetailedDao.deletehistoryplace(phone, place_id, date);
        return Common.sendMessage(modifiedcount, "删除");
    }


    /**
     * @Title deletehistory
     * @Description: TODO 用户删除历史出行记录
     * @Param [phone, date]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:34
     **/
    @Override
    public String deletehistory(String phone,String date) {
        // TODO Auto-generated method stub
        //数据库删除历史出行记录
        int modifiedcount = userDetailedDao.deletehistory(phone, date);
        return Common.sendMessage(modifiedcount, "删除");
    }


     /**
      * @Title findmycollections
      * @Description: TODO 用户查找所有个人收藏的景点
      * @Param [phone]
      * @return java.lang.String
      * @Author: 陈一心
      * @Date: 2019/9/9  23:36
      **/
    @Override
    public String findmycollections(String phone) {
        // TODO Auto-generated method stub
        //数据库联表(user_detailed和place)查询个人收藏景点
        return userDetailedDao.findmycollections(phone);
    }


    /**
     * @Title findmyhistories
     * @Description: TODO 用户查找所有历史出行(返回所有日期的历史出行列表)
     * @Param [phone]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:37
     **/
    @Override
    public String findmyhistories(String phone) {
        // TODO Auto-generated method stub
        //数据库查询所有历史出行
        List<Myhistory> myhistories = userDetailedDao.findmyhistories(phone);
        //封装返回出行日期(date) , 浏览景点数量(place_number)的json数组类型数据
        JSONArray jsonArray = new JSONArray();
        for (Object myhistoryObject : myhistories) {
            Myhistory myhistory = (Myhistory) myhistoryObject;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("date", myhistory.getDate());
            jsonObject.put("place_number", myhistory.getPlaces_time().size());
            jsonArray.put(jsonObject);
        }
        //返回json数组类型
        return jsonArray.toString();
    }


   /**
    * @Title findmyhistories_detailed
    * @Description: TODO 用户根据日期查找某一天的历史出行详情列表
    * @Param [phone, date]
    * @return java.lang.String
    * @Author: 陈一心
    * @Date: 2019/9/9  23:39
    **/
    @Override
    public String findmyhistories_detailed(String phone,String date) {
        // TODO Auto-generated method stub
        //数据库返回历史出行详情
        return userDetailedDao.findmyhistories_detailed(phone, date);
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
    public String findmyplans(String phone) {
        // TODO Auto-generated method stub
        //数据库查询所有出行计划的名称列表
        List<Myplan> myplans = userDetailedDao.findmyplans(phone);
        //封装返回出行计划名称(plan_name) , 计划时间(time)的json数组类型数据
        JSONArray jsonArray = new JSONArray();
        for (Object myplanObject : myplans) {
            Myplan myplan = (Myplan) myplanObject;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("plan_name", myplan.getPlan_name());
            jsonObject.put("time", myplan.getTime());
            jsonArray.put(jsonObject);
        }
        //返回json数组类型
        return jsonArray.toString();
    }


    /**
     * @Title findmyplans_detailed
     * @Description: TODO 用户根据计划制定的时间(time)查询出行计划详情列表
     * @Param [phone, time]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:42
     **/
    @Override
    public String findmyplans_detailed(String phone,String time) {
        // TODO Auto-generated method stub
        //时间转换
        Date totime = Common.TimeConversion(time);
        //数据库查找个人出行计划详情
        return userDetailedDao.findmyplans_detailed(phone, totime);

    }


}
