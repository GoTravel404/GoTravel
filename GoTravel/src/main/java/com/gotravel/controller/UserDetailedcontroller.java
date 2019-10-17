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
public class UserDetailedcontroller {

    @Autowired
    private UserDetailedService userDetailedService;

    /**
     * @Title choose_label
     * @Description:TODO 用户选择个人标签
     * @Param [map] phone,hobby,customization
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/8  21:32
     **/
    @RequestMapping(value = "/choose_label", method = RequestMethod.POST)
    public String choose_label(@RequestBody Map<String, Object> map) {
        return userDetailedService.choose_label(map);
    }


    /**
     * @Title addmycollections
     * @Description: TODO 用户添加个人收藏景点
     * @Param [phone, places_id, dateStr]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:13
     **/
    @RequestMapping(value = "/addmycollections", method = RequestMethod.POST)
    public String addmycollections(@RequestParam String phone, @RequestParam int place_id,@RequestParam String time) {
        return userDetailedService.addmycollections(phone,place_id,time);
    }


    /**
     * @Title deletemycollections
     * @Description:TODO 用户删除个人收藏景点
     * @Param [request] phone place_id
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/8  21:33
     **/
    @RequestMapping(value = "/deletemycollections", method = RequestMethod.POST)
    public String deletemycollections(@RequestParam String phone,@RequestParam int place_id) {
        return userDetailedService.deletemycollections(phone,place_id);
    }


    /**
     * @Title addmyplans
     * @Description:TODO 用户添加个人出行计划
     * @Param [map] phone,plan_name,places_id,time
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/8  21:33
     **/
    @RequestMapping(value = "/addmyplans", method = RequestMethod.POST)
    public String addmyplans(@RequestBody Map<String, Object> map) {
        return userDetailedService.addmyplans(map);
    }


    /**
     * @Title editmyplans
     * @Description:TODO 用户编辑个人出行计划
     * @Param [map] phone,plan_name,places_id,time
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/8  21:34
     **/
    @RequestMapping(value = "/editmyplans", method = RequestMethod.POST)
    public String editmyplans(@RequestBody Map<String, Object> map) {
        return userDetailedService.editmyplans(map);
    }


    /**
     * @Title deletemyplans
     * @Description: TODO 用户删除个人出行计划
     * @Param [phone, time]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:25
     **/
    @RequestMapping(value = "/deletemyplans", method = RequestMethod.POST)
    public String deletemyplans(@RequestParam String phone,@RequestParam String time) {
        return userDetailedService.deletemyplans(phone,time);
    }


    /**
     * @Title addhistory
     * @Description:TODO 用户到达景点后将景点添加到历史出行
     * @Param [request]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/8  21:35
     **/
    @RequestMapping(value = "/addhistory", method = RequestMethod.POST)
    public String addhistory(@RequestParam String phone,@RequestParam int place_id,@RequestParam String time) {
        return userDetailedService.addhistory(phone,place_id,time);
    }


    /**
     * @Title deletehistoryplace
     * @Description: TODO 用户删除历史出行的单个景点
     * @Param [phone, place_id, date]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:31
     **/
    @RequestMapping(value = "/deletehistoryplace", method = RequestMethod.POST)
    public String deletehistoryplace(@RequestParam String phone,@RequestParam int place_id,@RequestParam String date) {
        return userDetailedService.deletehistoryplace(phone,place_id,date);
    }


    /**
     * @Title deletehistory
     * @Description: TODO 用户删除历史出行记录
     * @Param [phone, date]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:34
     **/
    @RequestMapping(value = "/deletehistory", method = RequestMethod.POST)
    public String deletehistory(@RequestParam String phone,@RequestParam String date) {
        return userDetailedService.deletehistory(phone,date);
    }


    /**
     * @Title findmycollections
     * @Description: TODO 用户查找所有个人收藏的景点
     * @Param [phone]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:35
     **/
    @RequestMapping(value = "/findmycollections", method = RequestMethod.GET)
    public String findmycollections(String phone) {
        return userDetailedService.findmycollections(phone);
    }


    /**
     * @Title findmyhistories
     * @Description: TODO 用户查找所有历史出行(返回所有日期的历史出行列表)
     * @Param [phone]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:37
     **/
    @RequestMapping(value = "/findmyhistories", method = RequestMethod.GET)
    public String findmyhistories(@RequestParam String phone) {
        return userDetailedService.findmyhistories(phone);
    }


    /**
     * @Title findmyhistories_detailed
     * @Description: TODO 用户根据日期查找某一天的历史出行详情列表
     * @Param [phone, date]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:38
     **/
    @RequestMapping(value = "/findmyhistories_detailed", method = RequestMethod.POST)
    public String findmyhistories_detailed(@RequestParam String phone,@RequestParam String date) {
        return userDetailedService.findmyhistories_detailed(phone, date);
    }


    /**
     * @Title findmyplans
     * @Description: TODO 用户查找所有出行计划(返回所有出行计划的名称列表)
     * @Param [phone]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:40
     **/
    @RequestMapping(value = "/findmyplans", method = RequestMethod.GET)
    public String findmyplans(@RequestParam String phone) {
        return userDetailedService.findmyplans(phone);
    }


    /**
     * @Title findmyplans_detailed
     * @Description: TODO 用户根据计划制定的时间(time)查询出行计划详情列表
     * @Param [phone, time]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:42
     **/
    @RequestMapping(value = "/findmyplans_detailed", method = RequestMethod.POST)
    public String findmyplans_detailed(@RequestParam String phone,@RequestParam String time) {
        return userDetailedService.findmyplans_detailed(phone, time);
    }
}
