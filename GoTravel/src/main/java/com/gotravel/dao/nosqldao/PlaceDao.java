package com.gotravel.dao.nosqldao;


import com.gotravel.model.Place;
import com.gotravel.model.User_detailed;

import java.util.List;
import java.util.Map;

/**
 *
 * @Description:TODO monodb的Place表的CURD接口
 *  @date 2019年8月11日 下午9:53:35
 */
public interface PlaceDao {


    /**
     * @Title findByuser_label
     * @Description: TODO 根据用户的标签+地点设定的范围为用户提供景点且按好评度排序
     * @param user_detailed 用户标签
     * @param distance 距离
     * @param lon 经度
     * @param lat 维度
     * @return List<Map < String , Object>>
     * @Author: chenyx
     * @Date: 2019/9/21  10:20
     **/
    List<Map<String, Object>> findByuser_label(User_detailed user_detailed, int distance, double lon, double lat);


    /**
     * @Title findPlaceByplace_id
     * @Description:TODO 根据景点的place_id返回景点信息
     * @Param [place_id]
     * @return Place
     * @Author: 陈一心
     * @Date: 2019/9/8  21:50
     **/
    Place findPlaceByplace_id(int place_id);


    /**
     * @Title findPlacesByPlaceLabel
     * @Description: TODO 根据景点的Label(封装成三组List类型，有List<hobby>、List<customization>、List<place_type>)+地点设定的范围返回景点信息且按好评度排序
     * @param hobby
     * @param customization
     * @param place_type
     * @param distance
     * @param lon
     * @param lat
     * @return List<Map < String , Object>>
     * @Author: chenyx
     * @Date: 2019/9/21  15:29
     **/
    List<Map<String, Object>> findPlacesByPlaceLabel(List<String> hobby, List<String> customization, List<String> place_type, int distance, double lon, double lat);


    /**
     * @Title findPlacesByPraise
     * @Description: TODO 根据好评度(热门)+地点设定的范围返回景点信息且按好评度排序
     * @param distance 搜索范围
     * @param lon 经度
     * @param lat 维度
     * @return List<Map < String , Object>>
     * @Author: chenyx
     * @Date: 2019/9/20  16:27
     **/
    List<Map<String, Object>> findPlacesByPraise(int distance, double lon, double lat);
}
