package com.gotravel.service;

import java.util.Map;

/**
 *
 * @Description:TODO Place景点表的Service接口
 *  @date 2019年8月11日 下午9:21:32
 */
public interface PlaceService {


    /**
     * @Title findPlaceByuserlabel
     * @Description: TODO 根据用户的标签+地点设定的范围为用户提供景点且按好评度排序
     * @param phone 手机号
     * @param distance 范围
     * @param lon 经度
     * @param lat 维度
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/9/21  10:11
     **/
    String findPlaceByuserlabel(String phone, int distance, double lon, double lat);


    /**
     * @Title findPlaceByplace_id
     * @Description:TODO 根据景点的place_id返回景点信息
     * @Param [place_id]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  22:45
     **/
    String findPlaceByplace_id(int place_id);


    /**
     * @Title findPlacesByPlaceLabel
     * @Description: TODO 根据景点的Label(封装成三组List类型，有List<hobby>、List<customization>、List<place_type>)+地点设定的范围返回景点信息且按好评度排序
     * @param map List<hobby>，List<customization>，List<place_type> ，distance 距离 ，lon 经度 ，lat 维度
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/9/21  15:26
     **/
    String findPlacesByPlaceLabel(Map<String, Object> map);


    /**
     * @Title findPlacesByPraise
     * @Description: TODO 根据好评度(热门)+地点设定的范围返回景点信息且按好评度排序
     * @param distance 搜索 范围
     * @param lon 经度
     * @param lat 维度
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/9/20  16:20
     **/
    String findPlacesByPraise(int distance, double lon, double lat);
}
