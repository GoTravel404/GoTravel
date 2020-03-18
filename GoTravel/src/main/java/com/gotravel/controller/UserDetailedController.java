package com.gotravel.controller;


import com.gotravel.service.UserDetailedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Description: TODO User_Detailed用户详细信息表的Controller层
 *  @date 2019年8月4日 下午9:05:28
 */
@RestController
public class UserDetailedController {

    @Autowired
    private UserDetailedService userDetailedService;

    /**
     * @Title chooseLabel
     * @Description:TODO 用户选择个人标签
     * @Param [map] phone,hobby,customization
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/8  21:32
     **/
    @RequestMapping(value = "/chooseLabel", method = RequestMethod.POST)
    public String chooseLabel(@RequestBody Map<String, Object> map) {

        return userDetailedService.chooseLabel(map);
    }


    /**
     * @Title addMyCollections
     * @Description: TODO 用户添加个人收藏景点
     * @Param [phone, places_id, dateStr]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:13
     **/
    @RequestMapping(value = "/addMyCollections", method = RequestMethod.POST)
    public String addMyCollections(@RequestParam String phone, @RequestParam String place_id,@RequestParam String time) {
        return userDetailedService.addMyCollections(phone,place_id,time);
    }


    /**
     * @Title deleteMyCollections
     * @Description:TODO 用户删除个人收藏景点
     * @Param [request] phone place_id
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/8  21:33
     **/
    @RequestMapping(value = "/deleteMyCollections", method = RequestMethod.POST)
    public String deleteMyCollections(@RequestParam String phone,@RequestParam String place_id) {
        return userDetailedService.deleteMyCollections(phone,place_id);
    }


    /**
     * @Title addMyPlan
     * @Description:TODO 用户添加个人出行计划
     * @Param [map] phone,plan_name,places_id,time
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/8  21:33
     **/
    @RequestMapping(value = "/addMyPlan", method = RequestMethod.POST)
    public String addMyPlan(@RequestBody Map<String, Object> map) {
        return userDetailedService.addMyPlan(map);
    }


    /**
     * @Title editMyPlan
     * @Description:TODO 用户编辑个人出行计划
     * @Param [map] phone,plan_name,places_id,time
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/8  21:34
     **/
    @RequestMapping(value = "/editMyPlan", method = RequestMethod.POST)
    public String editMyPlan(@RequestBody Map<String, Object> map) {
        return userDetailedService.editMyPlan(map);
    }


    /**
     * @Title deleteMyPlan
     * @Description: TODO 用户删除个人出行计划
     * @Param [phone, time]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:25
     **/
    @RequestMapping(value = "/deleteMyPlan", method = RequestMethod.POST)
    public String deleteMyPlan(@RequestParam String phone,@RequestParam String time) {
        return userDetailedService.deleteMyPlan(phone,time);
    }


    /**
     * @Title addHistory
     * @Description:TODO 用户到达景点后将景点添加到历史出行
     * @Param [request]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/8  21:35
     **/
    @RequestMapping(value = "/addHistory", method = RequestMethod.POST)
    public String addHistory(@RequestParam String phone,@RequestParam String place_id,@RequestParam String time) {
        return userDetailedService.addHistory(phone,place_id,time);
    }


    /**
     * @Title deleteHistoryPlace
     * @Description: TODO 用户删除历史出行的单个景点
     * @Param [phone, place_id, date]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:31
     **/
    @RequestMapping(value = "/deleteHistoryPlace", method = RequestMethod.POST)
    public String deleteHistoryPlace(@RequestParam String phone,@RequestParam String place_id,@RequestParam String date) {
        return userDetailedService.deleteHistoryPlace(phone,place_id,date);
    }


    /**
     * @Title deleteHistory
     * @Description: TODO 用户删除历史出行记录
     * @Param [phone, date]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:34
     **/
    @RequestMapping(value = "/deleteHistory", method = RequestMethod.POST)
    public String deleteHistory(@RequestParam String phone,@RequestParam String date) {
        return userDetailedService.deleteHistory(phone,date);
    }


    /**
     * @Title findMyCollections
     * @Description: TODO 用户查找所有个人收藏的景点
     * @Param [phone]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:35
     **/
    @RequestMapping(value = "/findMyCollections", method = RequestMethod.GET)
    public String findMyCollections(String phone) {
        return userDetailedService.findMyCollections(phone);
    }


    /**
     * @Title findMyHistories
     * @Description: TODO 用户查找所有历史出行(返回所有日期的历史出行列表)
     * @Param [phone]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:37
     **/
    @RequestMapping(value = "/findMyHistories", method = RequestMethod.GET)
    public String findMyHistories(@RequestParam String phone) {
        return userDetailedService.findMyHistories(phone);
    }


    /**
     * @Title findMyHistoriesDetailed
     * @Description: TODO 用户根据日期查找某一天的历史出行详情列表
     * @Param [phone, date]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:38
     **/
    @RequestMapping(value = "/findMyHistoriesDetailed", method = RequestMethod.POST)
    public String findMyHistoriesDetailed(@RequestParam String phone,@RequestParam String date) {
        return userDetailedService.findMyHistoriesDetailed(phone, date);
    }


    /**
     * @Title findMyPlans
     * @Description: TODO 用户查找所有出行计划(返回所有出行计划的名称列表)
     * @Param [phone]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:40
     **/
    @RequestMapping(value = "/findMyPlans", method = RequestMethod.GET)
    public String findMyPlans(@RequestParam String phone) {
        return userDetailedService.findMyPlans(phone);
    }


    /**
     * @Title findMyPlansDetailed
     * @Description: TODO 用户根据计划制定的时间(time)查询出行计划详情列表
     * @Param [phone, time]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:42
     **/
    @RequestMapping(value = "/findMyPlansDetailed", method = RequestMethod.POST)
    public String findMyPlansDetailed(@RequestParam String phone,@RequestParam String time) {
        return userDetailedService.findMyPlansDetailed(phone, time);
    }
}
