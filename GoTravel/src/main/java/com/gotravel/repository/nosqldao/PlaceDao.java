package com.gotravel.repository.nosqldao;


import com.gotravel.entity.Place;
import com.gotravel.entity.UserDetailed;

import java.util.List;
import java.util.Map;

/**
 *
 * @Description:TODO monodb的Place表的CURD接口
 *  @date 2019年8月11日 下午9:53:35
 */
public interface PlaceDao {


    /**
     * 根据用户的标签为用户提供景点且按好评度排序
     * @param user_detailed
     * @return
     */
    List<Place> findPlacedByUserLabel(UserDetailed user_detailed);


    /**
     * 根据景点的Label(封装成三组List类型，有List<hobby>、List<customization>、List<place_type>)返回景点信息且按好评度排序
     * @param hobby
     * @param customization
     * @param place_type
     * @return
     */
    List<Place> findPlacesByPlaceLabel(List<String> hobby, List<String> customization, List<String> place_type);


    /**
     * 景点添加好评
     * @param place_id
     * @return
     */
    Map<String,Object> increasePlacePraise(String place_id);


    /**
     * 修改景点收藏数
     * @param place_id
     * @param code
     * @return
     */
    Map<String, Object> editPlaceCollection(String place_id, int code);


    /**
     *  根据景点的Label(封装成三组List类型，有List<hobby>、List<customization>、List<place_type>)返回景点信息且按好评度排序,去除特定的景点
     * @param hobbyList
     * @param customizationList
     * @param place_typeList
     * @param placeIdList
     * @return
     */
    List<Place> findPlacesByPlaceLabelExceptPlaceIdList(List<String> hobbyList, List<String> customizationList, List<String> place_typeList, List<String> placeIdList);



}

