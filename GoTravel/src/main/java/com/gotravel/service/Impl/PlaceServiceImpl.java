package com.gotravel.service.Impl;

import com.gotravel.repository.nosqldao.PlaceDao;
import com.gotravel.repository.nosqldao.UserDetailedDao;
import com.gotravel.repository.redis.PlaceRedis;
import com.gotravel.entity.Place;
import com.gotravel.entity.UserDetailed;
import com.gotravel.service.PlaceService;
import com.gotravel.utils.PlacesDistanceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
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
     * @Title findPlacesByUserLabel
     * @Description:  根据用户的标签+地点设定的范围为用户提供景点且按好评度排序
     * @param phone
     * @param distance
     * @param lon
     * @param lat
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Author: chenyx
     * @Date: 2020/3/19 19:32
     **/
    @Override
    public  Map<String,Object> findPlacesByUserLabel(String phone, int distance, double lon, double lat) {

        //数据库根据phone返回该用户的详细信息
        UserDetailed user_detailed = userDetailedDao.findByPhone(phone);

        //根据用户的标签为用户提供景点且按好评度排序
        List<Place> placeList = placeDao.findPlacedByUserLabel(user_detailed);

        //匹配符合距离范围的景点
        List<Map<String, Object>> places = PlacesDistanceUtils.getFitDistancePlaces(placeList, distance, lon, lat);

        //返回用户收藏的景点Id
        List<String> collectionsPlaceIds = userDetailedDao.findMyCollectionsPlaceId(phone);

        Map<String,Object> map=new HashMap<>();

        map.put("places", places);
        map.put("collectionsPlaceIds", collectionsPlaceIds);

        return map;
    }


    /**
     * @Title findPlaceByPlaceId
     * @Description:  根据景点的place_id返回景点信息
     * @param place_id
     * @Return: com.gotravel.entity.Place
     * @Author: chenyx
     * @Date: 2020/3/19 20:00
     **/
    @Override
    public Place findPlaceByPlaceId(String place_id) {

        //数据库根据景点的place_id返回景点信息
        return placeRedis.getARedisPlaceByKey(place_id);
    }


    /**
     * @Title findPlacesByPlaceLabel
     * @Description:  根据景点的Label(封装成三组List类型，有List<hobby>、List<customization>、List<place_type>)+地点设定的范围返回景点信息且按好评度排序
     * @param map List<hobby>，List<customization>，List<place_type> ，distance 距离 ，lon 经度 ，lat 维度 , phone 手机号
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/9/21  15:26
     **/
    @Override
    @SuppressWarnings("unchecked")
    public  Map<String,Object> findPlacesByPlaceLabel(Map<String, Object> map) {

        String phone = (String) map.get("phone");
        List<String> hobby = (List<String>) map.get("hobby");
        List<String> customization = (List<String>) map.get("customization");
        List<String> place_type = (List<String>) map.get("place_type");

        int distance = (int) map.get("distance");
        double lon = (double) map.get("lon");
        double lat = (double) map.get("lat");

        //根据景点的Label(封装成三组List类型，有List<hobby>、List<customization>、List<place_type>)返回景点信息且按好评度排序
       List<Place> placeList = placeDao.findPlacesByPlaceLabel(hobby, customization, place_type);

        //匹配符合距离范围的景点
        List<Map<String, Object>> places = PlacesDistanceUtils.getFitDistancePlaces(placeList, distance, lon, lat);

        List<String> collectionsPlaceIds = userDetailedDao.findMyCollectionsPlaceId(phone);

        Map<String,Object> resultMap=new HashMap<>();

        resultMap.put("places", places);
        resultMap.put("collectionsPlaceIds", collectionsPlaceIds);

        return resultMap;
    }


    /**
     * @Title findPlacesByPraise
     * @Description:  根据好评度(热门)+地点设定的范围返回景点信息且按好评度排序
     * @param phone
     * @param distance
     * @param lon
     * @param lat
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Author: chenyx
     * @Date: 2020/3/19 22:27
     **/
    @Override
    public Map<String, Object> findPlacesByPraise(String phone, int distance, double lon, double lat) {

        //查询所有景点信息
        List placesList = placeRedis.getRedisAllPlacesList();

        //通过Collections类的sort()方法降序排序
        Collections.sort(placesList);

        //匹配符合距离范围的景点
        List<Map<String, Object>> places = PlacesDistanceUtils.getFitDistancePlaces(placesList, distance, lon, lat);

        List<String> collectionsPlaceIds = userDetailedDao.findMyCollectionsPlaceId(phone);

        Map<String,Object> resultMap=new HashMap<>();

        resultMap.put("places", places);
        resultMap.put("collectionsPlaceIds", collectionsPlaceIds);

        return resultMap;
    }

}

