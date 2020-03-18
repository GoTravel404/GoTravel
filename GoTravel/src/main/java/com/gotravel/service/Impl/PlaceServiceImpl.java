package com.gotravel.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.gotravel.dao.nosqldao.PlaceDao;
import com.gotravel.dao.nosqldao.UserDetailedDao;
import com.gotravel.dao.redis.PlaceRedis;
import com.gotravel.entity.Place;
import com.gotravel.entity.UserDetailed;
import com.gotravel.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Description: Place景点表的Service实现层
 * @date 2019年8月11日 下午9:22:28
 */

@Service("placeService")
@Transactional
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    private UserDetailedDao userDetailedDao;

    @Autowired
    private PlaceDao placeDao;

    @Autowired
    private PlaceRedis placeRedis;


    /**
     * @Title findPlaceByuserlabel
     * @Description: TODO 根据用户的标签+地点设定的范围为用户提供景点且按好评度排序
     * @param phone 手机号
     * @param distance 距离
     * @param lon 经度
     * @param lat 维度
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/9/21  10:15
     **/
    @Override
    public String findPlacesByUserLabel(String phone, int distance, double lon, double lat) {

        //数据库根据phone返回该用户的详细信息
        UserDetailed user_detailed = userDetailedDao.findByPhone(phone);

        //数据库根据用户的标签为用户提供景点且按好评度排序
        List<Map<String, Object>> places = placeDao.findByUserLabel(user_detailed, distance, lon, lat);
        List<String> collectionsPlaceIds = userDetailedDao.findMyCollectionsPlaceId(phone);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("places", places);
        jsonObject.put("collectionsPlaceIds", collectionsPlaceIds);

        return jsonObject.toJSONString();
    }


    /**
     * @Title findPlacesByPlaceId
     * @Description:TODO 根据景点的place_id返回景点信息
     * @Param [place_id]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/8  22:11
     **/
    @Override
    public String findPlacesByPlaceId(String place_id) {

        //数据库根据景点的place_id返回景点信息
        Place place = placeRedis.getARedisPlaceByKey(place_id);

        return JSONObject.toJSONString(place);
    }


    /**
     * @Title findPlacesByPlaceLabel
     * @Description: TODO 根据景点的Label(封装成三组List类型，有List<hobby>、List<customization>、List<place_type>)+地点设定的范围返回景点信息且按好评度排序
     * @param map List<hobby>，List<customization>，List<place_type> ，distance 距离 ，lon 经度 ，lat 维度 , phone 手机号
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/9/21  15:26
     **/
    @Override
    @SuppressWarnings("unchecked")
    public String findPlacesByPlaceLabel(Map<String, Object> map) {

        String phone = (String) map.get("phone");
        List<String> hobby = (List<String>) map.get("hobby");
        List<String> customization = (List<String>) map.get("customization");
        List<String> place_type = (List<String>) map.get("place_type");

        int distance = (int) map.get("distance");
        double lon = (double) map.get("lon");
        double lat = (double) map.get("lat");

        //数据库根据place_label(List<String>)返回景点信息
        List<Map<String, Object>> places = placeDao.findPlacesByPlaceLabel(hobby, customization, place_type, distance, lon, lat);

        List<String> collectionsPlaceIds = userDetailedDao.findMyCollectionsPlaceId(phone);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("places", places);
        jsonObject.put("collectionsPlaceIds", collectionsPlaceIds);

        return jsonObject.toJSONString();
    }


    /**
     * @Title findPlacesByPraise
     * @Description: TODO 根据好评度(热门)+地点设定的范围返回景点信息且按好评度排序
     * @param phone 手机号
     * @param distance 搜索范围
     * @param lon 经度
     * @param lat 维度
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/9/20  16:21
     **/
    @Override
    public String findPlacesByPraise(String phone, int distance, double lon, double lat) {

        List<Map<String, Object>> places = placeDao.findPlacesByPraise(distance, lon, lat);

        List<String> collectionsPlaceIds = userDetailedDao.findMyCollectionsPlaceId(phone);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("places", places);
        jsonObject.put("collectionsPlaceIds", collectionsPlaceIds);

        return jsonObject.toJSONString();
    }
}

