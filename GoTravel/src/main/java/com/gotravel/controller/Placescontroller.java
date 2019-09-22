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
     * @Title findPlacesByUserlabel
     * @Description: TODO 根据用户的标签+地点设定的范围为用户提供景点且按好评度排序
     * @Param [phone]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  22:57
     **/
    @RequestMapping(value = "/findPlacesByUserlabel", method = RequestMethod.POST)
    public String findPlaceByuserlabel(@RequestParam String phone, @RequestParam int distance, @RequestParam double lon, @RequestParam double lat) {
        return placeService.findPlaceByuserlabel(phone, distance, lon, lat);
    }


    /**
     * @Title findPlaceByplace_id
     * @Description: TODO 根据景点的place_id返回景点信息
     * @Param [place_id]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  22:58
     **/
    @RequestMapping(value = "/findPlacesByPlace_id", method = RequestMethod.GET)
    public String findPlaceByplace_id(@RequestParam int place_id) {
        return placeService.findPlaceByplace_id(place_id);
    }


    /**
     * @Title findPlacesByPlaceLabel
     * @Description: TODO 根据景点的Label(封装成三组List类型，有List<hobby>、List<customization>、List<place_type>)+地点设定的范围返回景点信息且按好评度排序
     * @param map List<hobby>，List<customization>，List<place_type> ，distance 距离 ，lon 经度 ，lat 维度
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/9/21  10:37
     **/
    @RequestMapping(value = "/findPlacesByPlaceLabel", method = RequestMethod.POST)
    public String findPlacesByPlaceLabel(@RequestBody Map<String, Object> map) {
        return placeService.findPlacesByPlaceLabel(map);
    }


    /**
     * @Title findPlacesByPraise
     * @Description: TODO 根据好评度(热门)+地点设定的范围返回景点信息且按好评度排序
     * @param distance 搜索范围
     * @param lon 经度
     * @param lat 维度
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/9/20  16:18
     **/
    @RequestMapping(value = "/findPlacesByPraise", method = RequestMethod.POST)
    public String findPlacesByPraise(@RequestParam int distance, @RequestParam double lon, @RequestParam double lat) {
        return placeService.findPlacesByPraise(distance, lon, lat);
    }


}
