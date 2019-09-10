package com.gotravel.controller;

import com.gotravel.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Description: TODO place集合的所有操作接口
 *  @date 2019年8月4日 下午10:41:02
 */
@RestController
public class Placescontroller {

    @Autowired
    private PlaceService placeService;


    /**
     * @Title findPlaceByuserlabel
     * @Description: TODO 根据用户的标签为用户提供景点且按好评度排序
     * @Param [phone]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  22:57
     **/
    @RequestMapping(value = "/findPlaceByuserlabel", method = RequestMethod.GET)
    public String findPlaceByuserlabel(@RequestParam String phone) {
        return placeService.findPlaceByuserlabel(phone);
    }


    /**
     * @Title findPlaceByplace_id
     * @Description: TODO 根据景点的place_id返回景点信息
     * @Param [place_id]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  22:58
     **/
    @RequestMapping(value = "/findPlaceByplace_id", method = RequestMethod.GET)
    public String findPlaceByplace_id(@RequestParam int place_id) {
        return placeService.findPlaceByplace_id(place_id);
    }


    /**
     * @Title findPlaceByplace_type
     * @Description:TODO 根据景点的place_type(封装成List类型)返回景点信息且按好评度排序
     * @Param [map] List<String>
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/8  21:19
     **/
    @RequestMapping(value = "/findPlaceByplace_type", method = RequestMethod.POST)
    public String findPlaceByplace_type(@RequestBody Map<String, Object> map) {
        return placeService.findPlaceByplace_type(map);
    }

}
