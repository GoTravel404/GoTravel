package com.gotravel.service;

import com.gotravel.entity.Place;

import java.util.Map;

/**
 *
 * @Description:TODO Place景点表的Service接口
 *  @date 2019年8月11日 下午9:21:32
 */
public interface PlaceService {


    /**
     * 根据用户的标签+地点设定的范围为用户提供景点且按好评度排序
     * @param phone
     * @param distance
     * @param lon
     * @param lat
     * @return
     */
    Map<String, Object> findPlacesByUserLabel(String phone, int distance, double lon, double lat);


    /**
     * 根据景点的place_id返回景点信息
     * @param place_id
     * @return
     */
    Place findPlaceByPlaceId(String place_id);


    /**
     *  根据景点的Label(封装成三组List类型，有List<hobby>、List<customization>、List<place_type>)+地点设定的范围返回景点信息且按好评度排序
     * @param map
     * @return
     */
    Map<String, Object> findPlacesByPlaceLabel(Map<String, Object> map);


    /**
     * 根据好评度(热门)+地点设定的范围返回景点信息且按好评度排序
     * @param phone
     * @param distance
     * @param lon
     * @param lat
     * @param placeName
     * @return
     */
    Map<String, Object> findPlacesByPraise(String phone, int distance, double lon, double lat,String placeName);


    /**
     * 根据用户的出行记录+收藏景点+地点设定的范围为用户推荐景点且按好评度排序
     * @param phone
     * @param distance
     * @param lon
     * @param lat
     * @return
     */
    Map<String, Object> findPlacesByUserBehavior(String phone, int distance, double lon, double lat);


    /**
     * 修改景点收藏数
     * @param place_id
     * @param code
     * @return
     */
    void editPlaceCollection(String place_id, int code);


}
