package com.gotravel.controller;


import com.gotravel.enums.PlaceEnum;
import com.gotravel.enums.ResultEnum;
import com.gotravel.service.PlaceService;
import com.gotravel.service.UserDetailedService;
import com.gotravel.utils.ResultVOUtil;
import com.gotravel.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: User_Detailed用户详细信息表的Controller层
 *  @date 2019年8月4日 下午9:05:28
 */
@RequestMapping("/goTravel/userDetailed")
@RestController
public class UserDetailedController {

    @Autowired
    private UserDetailedService userDetailedService;

    @Autowired
    private PlaceService placeService;


    /**
     * @Title chooseLabel
     * @Description: 用户选择个人标签
     * @param map    phone,hobby,customization
     * @Return: ResultVO
     * @Author: chenyx
     * @Date: 2020/3/20 16:28
     **/
    @RequestMapping(value = "/chooseLabel", method = RequestMethod.POST)
    public ResultVO chooseLabel(@RequestBody Map<String, Object> map) {

        userDetailedService.chooseLabel(map);

        return ResultVOUtil.success(map);

    }


    /**
     * @Title addMyCollection
     * @Description: 用户添加个人收藏景点
     * @param phone
     * @param place_id
     * @Return: com.gotravel.vo.ResultVO
     * @Author: chenyx
     * @Date: 2020/3/20 18:39
     **/
    @RequestMapping(value = "/addMyCollection", method = RequestMethod.POST)
    public synchronized ResultVO addMyCollection(@RequestParam String phone, @RequestParam String place_id) {

        int modifiedCount = userDetailedService.addMyCollection(phone, place_id);

        if (modifiedCount > 0) {

            //place的collection+1
            placeService.editPlaceCollection(place_id, PlaceEnum.COLLECTION_INCREASE.getCode());

            return ResultVOUtil.success();
        } else {

            return ResultVOUtil.error(ResultEnum.ADD_COLLECTION_ERROR.getCode(), ResultEnum.ADD_COLLECTION_ERROR.getMessage());
        }

    }


    /**
     * @Title deleteMyCollection
     * @Description: 用户删除个人收藏景点
     * @param phone
     * @param place_id
     * @Return: com.gotravel.vo.ResultVO
     * @Author: chenyx
     * @Date: 2020/3/20 18:40
     **/
    @RequestMapping(value = "/deleteMyCollection", method = RequestMethod.POST)
    public ResultVO deleteMyCollection(@RequestParam String phone, @RequestParam String place_id) {

        int modifiedCount = userDetailedService.deleteMyCollection(phone, place_id);

        if (modifiedCount > 0) {

            //place的collection-1
            placeService.editPlaceCollection(place_id, PlaceEnum.COLLECTION_DECREASE.getCode());

            return ResultVOUtil.success();
        } else {

            return ResultVOUtil.error(ResultEnum.DELETE_COLLECTION_ERROR.getCode(), ResultEnum.DELETE_COLLECTION_ERROR.getMessage());
        }
    }


    /**
     * @Title findMyCollections
     * @Description: 用户查找所有个人收藏的景点
     * @param phone
     * @Return: com.gotravel.vo.ResultVO
     * @Author: chenyx
     * @Date: 2020/3/20 19:25
     **/
    @RequestMapping(value = "/findMyCollections", method = RequestMethod.GET)
    public ResultVO findMyCollections(String phone) {

        List<Map<String, Object>> resultList = userDetailedService.findMyCollections(phone);

        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("collections", resultList);

        return ResultVOUtil.success(resultMap);

    }


    /**
     * @Title addMyPlan
     * @Description: 用户添加个人出行计划
     * @param map     phone,plan_name,place_ids(List<Integer>),postscript
     * @Return: com.gotravel.vo.ResultVO
     * @Author: chenyx
     * @Date: 2020/3/20 19:59
     **/
    @RequestMapping(value = "/addMyPlan", method = RequestMethod.POST)
    public ResultVO addMyPlan(@RequestBody Map<String, Object> map) {

        int modifiedCount = userDetailedService.addMyPlan(map);

        if (modifiedCount > 0) {

            return ResultVOUtil.success();
        } else {

            return ResultVOUtil.error(ResultEnum.ADD_PLAN_ERROR.getCode(), ResultEnum.ADD_PLAN_ERROR.getMessage());
        }

    }


    /**
     * @Title editMyPlan
     * @Description: 用户编辑个人出行计划
     * @param map    phone,plan_name,places_id(List<Integer>),time,postscript
     * @Return: com.gotravel.vo.ResultVO
     * @Author: chenyx
     * @Date: 2020/3/20 20:30
     **/
    @RequestMapping(value = "/editMyPlan", method = RequestMethod.POST)
    public ResultVO editMyPlan(@RequestBody Map<String, Object> map) {

        int modifiedCount = userDetailedService.editMyPlan(map);

        if (modifiedCount > 0) {

            return ResultVOUtil.success();
        } else {

            return ResultVOUtil.error(ResultEnum.EDIT_PLAN_ERROR.getCode(), ResultEnum.EDIT_PLAN_ERROR.getMessage());
        }

    }


    /**
     * @Title deleteMyPlan
     * @Description: 用户删除个人出行计划
     * @Param [phone, time]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:25
     **/
    @RequestMapping(value = "/deleteMyPlan", method = RequestMethod.POST)
    public ResultVO deleteMyPlan(@RequestParam String phone, @RequestParam long time) {

        int modifiedCount = userDetailedService.deleteMyPlan(phone, time);

        if (modifiedCount > 0) {

            return ResultVOUtil.success();

        } else {

            return ResultVOUtil.error(ResultEnum.DELETE_PLAN_ERROR.getCode(), ResultEnum.DELETE_PLAN_ERROR.getMessage());
        }


    }


    /**
     * @Title findMyPlans
     * @Description: 用户查找所有出行计划(返回所有出行计划的名称列表)
     * @Param [phone]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:40
     **/
    @RequestMapping(value = "/findMyPlans", method = RequestMethod.GET)
    public ResultVO findMyPlans(@RequestParam String phone) {

        List<Map<String, Object>> resultList = userDetailedService.findMyPlans(phone);

        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("plans", resultList);

        return ResultVOUtil.success(resultMap);

    }


    /**
     * @Title findMyPlansDetailed
     * @Description: 用户根据计划制定的时间(time)查询出行计划详情列表
     * @param phone
     * @param time
     * @Return: com.gotravel.vo.ResultVO
     * @Author: chenyx
     * @Date: 2020/3/20 22:59
     **/
    @RequestMapping(value = "/findMyPlanDetailed", method = RequestMethod.POST)
    public ResultVO findMyPlanDetailed(@RequestParam String phone, @RequestParam long time) {

        Map<String, Object> map = userDetailedService.findMyPlanDetailed(phone, time);

        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("planDetailed", map);

        return ResultVOUtil.success(resultMap);

    }


    /**
     * @Title searchMyPlanByPhoneAndPlanName
     * @Description: 用户根据手机号+出行计划的名称查询计划列表
     * @param phone
     * @param plan_name
     * @Return: com.gotravel.vo.ResultVO
     * @Author: chenyx
     * @Date: 2020/4/8 17:33
     **/
    @RequestMapping(value = "/searchMyPlanByPhoneAndPlanName", method = RequestMethod.POST)
    public ResultVO searchMyPlanByPhoneAndPlanName(@RequestParam String phone, @RequestParam String plan_name) {

        List<Map<String, Object>> resultList = userDetailedService.searchMyPlanByPhoneAndPlanName(phone, plan_name);

        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("plans", resultList);

        return ResultVOUtil.success(resultMap);
    }


    /**
     * @Title addHistory
     * @Description: 用户到达景点后将景点添加到历史出行
     * @param phone
     * @param place_id
     * @Return: com.gotravel.vo.ResultVO
     * @Author: chenyx
     * @Date: 2020/3/21 19:45
     **/
    @RequestMapping(value = "/addHistory", method = RequestMethod.POST)
    public ResultVO addHistory(@RequestParam String phone, @RequestParam String place_id) {

        int modifiedCount = userDetailedService.addHistory(phone, place_id);

        if (modifiedCount > 0) {

            return ResultVOUtil.success();
        } else {

            return ResultVOUtil.error(ResultEnum.ADD_HISTORY_ERROR.getCode(), ResultEnum.ADD_HISTORY_ERROR.getMessage());
        }


    }


    /**
     * @Title deleteHistoryPlace
     * @Description: 用户删除历史出行的单个景点
     * @param phone
     * @param time
     * @param date
     * @Return: com.gotravel.vo.ResultVO
     * @Author: chenyx
     * @Date: 2020/3/21 21:24
     **/
    @RequestMapping(value = "/deleteHistoryPlace", method = RequestMethod.POST)
    public ResultVO deleteHistoryPlace(@RequestParam String phone, @RequestParam long time, @RequestParam String date) {

        int modifiedCount = userDetailedService.deleteHistoryPlace(phone, time, date);

        if (modifiedCount > 0) {

            return ResultVOUtil.success();
        } else {

            return ResultVOUtil.error(ResultEnum.DELETE_HISTORY_PLACE_ERROR.getCode(), ResultEnum.DELETE_HISTORY_PLACE_ERROR.getMessage());
        }

    }


    /**
     * @Title deleteHistory
     * @Description: 用户删除历史出行记录
     * @param phone
     * @param date
     * @Return: com.gotravel.vo.ResultVO
     * @Author: chenyx
     * @Date: 2020/3/21 21:41
     **/
    @RequestMapping(value = "/deleteHistory", method = RequestMethod.POST)
    public ResultVO deleteHistory(@RequestParam String phone, @RequestParam String date) {

        int modifiedCount = userDetailedService.deleteHistory(phone, date);

        if (modifiedCount > 0) {

            return ResultVOUtil.success();
        } else {

            return ResultVOUtil.error(ResultEnum.DELETE_HISTORY__ERROR.getCode(), ResultEnum.DELETE_HISTORY__ERROR.getMessage());
        }

    }


    /**
     * @Title findMyHistories
     * @Description: 用户查找所有历史出行(返回所有日期的历史出行列表)
     * @param phone
     * @Return: com.gotravel.vo.ResultVO
     * @Author: chenyx
     * @Date: 2020/3/21 21:43
     **/
    @RequestMapping(value = "/findMyHistories", method = RequestMethod.GET)
    public ResultVO findMyHistories(@RequestParam String phone) {

        List<Map<String, Object>> resultList = userDetailedService.findMyHistories(phone);

        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("histories", resultList);

        return ResultVOUtil.success(resultMap);
    }


    /**
     * @Title findMyHistoriesDetailed
     * @Description: 用户根据日期查找某一天的历史出行详情列表
     * @param phone
     * @param date
     * @Return: com.gotravel.vo.ResultVO
     * @Author: chenyx
     * @Date: 2020/3/21 22:16
     **/
    @RequestMapping(value = "/findMyHistoriesDetailed", method = RequestMethod.POST)
    public ResultVO findMyHistoriesDetailed(@RequestParam String phone, @RequestParam String date) {

        Map<String, Object> map = userDetailedService.findMyHistoriesDetailed(phone, date);

        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("historiesDetailed", map);

        return ResultVOUtil.success(resultMap);

    }


}
