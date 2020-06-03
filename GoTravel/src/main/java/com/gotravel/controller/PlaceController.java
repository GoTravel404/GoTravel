package com.gotravel.controller;

import com.gotravel.entity.Place;
import com.gotravel.enums.PlaceEnum;
import com.gotravel.enums.ResultEnum;
import com.gotravel.service.PlaceCommentService;
import com.gotravel.service.PlaceService;
import com.gotravel.utils.PlacesDistanceUtils;
import com.gotravel.utils.ResultVOUtil;
import com.gotravel.vo.PagePlaceCommentVO;
import com.gotravel.vo.PlaceVO;
import com.gotravel.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.gotravel.enums.DefinedParam.PLACE_COMMENT_QUANTITY;

/**
 * @Description: place集合的所有操作接口
 *  @date 2019年8月4日 下午10:41:02
 */
@RequestMapping("/goTravel/place")
@RestController
public class PlaceController {

    @Autowired
    private PlaceService placeService;


    @Autowired
    private PlaceCommentService placeCommentService;


    /**
     * @Title findPlacesByUserLabel
     * @Description: 根据用户的标签+地点设定的范围为用户提供景点且按好评度排序
     * @param phone
     * @param distance
     * @param lon
     * @param lat
     * @Return: ResultVO
     * @Author: chenyx
     * @Date: 2020/3/19 19:30
     **/
    @RequestMapping(value = "/findPlacesByUserLabel", method = RequestMethod.POST)
    public ResultVO findPlacesByUserLabel(@RequestParam String phone, @RequestParam int distance, @RequestParam double lon, @RequestParam double lat) {

        Map<String, Object> resultMap = placeService.findPlacesByUserLabel(phone, distance, lon, lat);

        return ResultVOUtil.success(resultMap);

    }


    /**
     * @Title findPlaceByPlaceId
     * @Description: 根据景点的place_id返回景点信息
     * @param phone
     * @param place_id
     * @param lon
     * @param lat
     * @Return: com.gotravel.vo.ResultVO
     * @Author: chenyx
     * @Date: 2020/5/26 21:23
     **/
    @RequestMapping(value = "/findPlaceByPlaceId", method = RequestMethod.POST)
    public ResultVO findPlaceByPlaceId(@RequestParam String phone, @RequestParam String place_id, @RequestParam double lon, @RequestParam double lat) {

        Place place = placeService.findPlaceByPlaceId(place_id);

        Map<String, Object> resultMap = new HashMap<>();

        if (PlaceEnum.BAN.getCode() == place.getStatus()) {

            return ResultVOUtil.error(ResultEnum.PLACE_BAN.getCode(), ResultEnum.PLACE_BAN.getMessage());

        } else {

            //获取当前定位到景点的距离
            double distance = PlacesDistanceUtils.getDistance(place, lon, lat);

            PlaceVO placeVO = new PlaceVO();
            BeanUtils.copyProperties(place, placeVO);

            placeVO.setDistance(distance);

            resultMap.put("place", placeVO);

            //返回该景点的前置评论
            PagePlaceCommentVO pagePlaceCommentVO = placeCommentService.selectPlaceCommentPageByPlaceId(phone, place_id, 0, PLACE_COMMENT_QUANTITY);

            resultMap.put("comment", pagePlaceCommentVO);

            return ResultVOUtil.success(resultMap);
        }

    }


    /**
     * @Title findPlacesByPlaceLabel
     * @Description: 根据景点的Label(封装成三组List类型 ， 有List < hobby > 、 List < customization > 、 List < place_type >)+地点设定的范围返回景点信息且按好评度排序
     * @param map    List<hobby>，List<customization>，List<place_type> ，distance 距离 ，lon 经度 ，lat 维度 ,phone 手机号
     * @Return: com.gotravel.vo.ResultVO
     * @Author: chenyx
     * @Date: 2020/3/19 21:01
     **/
    @RequestMapping(value = "/findPlacesByPlaceLabel", method = RequestMethod.POST)
    public ResultVO findPlacesByPlaceLabel(@RequestBody Map<String, Object> map) {

        Map<String, Object> resultMap = placeService.findPlacesByPlaceLabel(map);

        return ResultVOUtil.success(resultMap);

    }


    /**
     * @Title findPlacesByPraise
     * @Description: 根据好评度(热门)+地点设定的范围返回景点信息且按好评度排序
     * @param phone
     * @param distance
     * @param lon
     * @param lat
     * @Return: com.gotravel.vo.ResultVO
     * @Author: chenyx
     * @Date: 2020/3/19 22:26
     **/
    @RequestMapping(value = "/findPlacesByPraise", method = RequestMethod.POST)
    public ResultVO findPlacesByPraise(@RequestParam String phone, @RequestParam int distance, @RequestParam double lon, @RequestParam double lat,@RequestParam String placeName) {

        Map<String, Object> resultMap = placeService.findPlacesByPraise(phone, distance, lon, lat,placeName);

        return ResultVOUtil.success(resultMap);
    }


    /**
     * @Title findPlacesByUserBehavior
     * @Description: 根据用户的出行记录+收藏景点+地点设定的范围为用户推荐景点且按好评度排序
     * @param phone
     * @param distance
     * @param lon
     * @param lat
     * @Return: ResultVO
     * @Author: chenyx
     * @Date: 2020/3/19 19:30
     **/
    @RequestMapping(value = "/findPlacesByUserBehavior", method = RequestMethod.POST)
    public ResultVO findPlacesByUserBehavior(@RequestParam String phone, @RequestParam int distance, @RequestParam double lon, @RequestParam double lat) {

        Map<String, Object> resultMap = placeService.findPlacesByUserBehavior(phone, distance, lon, lat);

        return ResultVOUtil.success(resultMap);

    }


}
