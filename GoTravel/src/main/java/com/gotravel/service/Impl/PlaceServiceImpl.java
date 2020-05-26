package com.gotravel.service.Impl;

import com.gotravel.entity.Place;
import com.gotravel.entity.UserDetailed;
import com.gotravel.enums.PlaceEnum;
import com.gotravel.repository.nosqldao.PlaceDao;
import com.gotravel.repository.nosqldao.UserDetailedDao;
import com.gotravel.repository.redis.PlaceRedis;
import com.gotravel.service.PlaceCommentService;
import com.gotravel.service.PlaceService;
import com.gotravel.utils.OtherUtils;
import com.gotravel.utils.PlacesDistanceUtils;
import com.gotravel.vo.PagePlaceCommentVO;
import com.gotravel.vo.PlaceItemVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

import static com.gotravel.enums.DefinedParam.PLACE_COMMENT_QUANTITY;

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

    @Autowired
    private PlaceCommentService placeCommentService;


    /**
     * @Title findPlacesByUserLabel
     * @Description: 根据用户的标签+地点设定的范围为用户提供景点且按好评度排序
     * @param phone
     * @param distance
     * @param lon
     * @param lat
     * @Return: java.util.Map<java.lang.String, java.lang.Object>
     * @Author: chenyx
     * @Date: 2020/3/19 19:32
     **/
    @Override
    public Map<String, Object> findPlacesByUserLabel(String phone, int distance, double lon, double lat) {

        //数据库根据phone返回该用户的详细信息
        UserDetailed user_detailed = userDetailedDao.findByPhone(phone);

        //根据用户的标签为用户提供景点且按好评度排序
        List<Place> placeList = placeDao.findPlacedByUserLabel(user_detailed);

        //匹配符合距离范围的景点
        List<Place> places = PlacesDistanceUtils.getFitDistancePlaces(placeList, distance, lon, lat);

        return getResult(places, phone);
    }


    /**
     * @Title findPlaceByPlaceId
     * @Description: 根据景点的place_id返回景点信息
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
     * @Description: 根据景点的Label(封装成三组List类型 ， 有List < hobby > 、 List < customization > 、 List < place_type >)+地点设定的范围返回景点信息且按好评度排序
     * @param map List<hobby>，List<customization>，List<place_type> ，distance 距离 ，lon 经度 ，lat 维度 , phone 手机号
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/9/21  15:26
     **/
    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> findPlacesByPlaceLabel(Map<String, Object> map) {

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
        List<Place> places = PlacesDistanceUtils.getFitDistancePlaces(placeList, distance, lon, lat);


        return getResult(places, phone);
    }


    /**
     * @Title findPlacesByPraise
     * @Description: 根据好评度(热门)+地点设定的范围返回景点信息且按好评度排序
     * @param phone
     * @param distance
     * @param lon
     * @param lat
     * @Return: java.util.Map<java.lang.String, java.lang.Object>
     * @Author: chenyx
     * @Date: 2020/3/19 22:27
     **/
    @Override
    public Map<String, Object> findPlacesByPraise(String phone, int distance, double lon, double lat) {

        //查询所有景点信息
        List placeList = placeRedis.getRedisAllPlacesList();

        //通过Collections类的sort()方法降序排序
        Collections.sort(placeList);

        //匹配符合距离范围的景点
        List<Place> places = PlacesDistanceUtils.getFitDistancePlaces(placeList, distance, lon, lat);

        return getResult(places, phone);
    }


    /**
     * @Title findPlacesByUserHistories
     * @Description: 根据用户的出行记录+地点设定的范围为用户推荐景点且按好评度排序
     * @param phone
     * @param distance
     * @param lon
     * @param lat
     * @Return: java.util.Map<java.lang.String, java.lang.Object>
     * @Author: chenyx
     * @Date: 2020/5/6 17:10
     **/
    @Override
    public Map<String, Object> findPlacesByUserHistories(String phone, int distance, double lon, double lat) {

        UserDetailed userDetailed = userDetailedDao.findMyHistories(phone);

        if (null == userDetailed || null == userDetailed.getMyHistories() || 0 == userDetailed.getMyHistories().size()) {
            return findPlacesByUserLabel(phone, distance, lon, lat);
        }

        //获取所有用户的踏足(历史出行记录)的景点
        List<String> placeIdList = OtherUtils.getHistoryPlaceIdListByUserDetailed(userDetailed);


        /*
         *获取户的历史出行景点的标签
         */
        //当用户的出行景点不是空
        if (!CollectionUtils.isEmpty(placeIdList)) {

            List<Place> placeList = placeRedis.getPlaceListByPipeline(placeIdList);//返回redis景点

            List<String> place_typeList = new ArrayList<>();//景点的类型
            List<String> hobbyList = new ArrayList<>();//景点的爱好
            List<String> customizationList = new ArrayList<>();//景点的定制

            for (Place place : placeList) {

                if (null != place) {

                    if (!CollectionUtils.isEmpty(place.getPlace_type()))
                        place_typeList.addAll(place.getPlace_type());

                    if (!CollectionUtils.isEmpty(place.getHobby()))
                        hobbyList.addAll(place.getHobby());

                    if (!CollectionUtils.isEmpty(place.getCustomization()))
                        customizationList.addAll(place.getCustomization());
                }
            }


            //Set去重复的元素
            place_typeList = new ArrayList<>(new HashSet(place_typeList));
            hobbyList = new ArrayList<>(new HashSet(hobbyList));
            customizationList = new ArrayList<>(new HashSet(customizationList));


            //根据景点的Label(封装成三组List类型，有List<hobby>、List<customization>、List<place_type>)返回景点信息且按好评度排序,去除特定的景点
            placeList = placeDao.findPlacesByPlaceLabelExceptPlaceIdList(hobbyList, customizationList, place_typeList, placeIdList);

            //匹配符合距离范围的景点
            List<Place> places = PlacesDistanceUtils.getFitDistancePlaces(placeList, distance, lon, lat);

            return getResult(places, phone);

        }

        return findPlacesByUserLabel(phone, distance, lon, lat);

    }


    /**
     * @Title editPlaceCollection
     * @Description: 修改景点收藏数
     * @param place_id
     * @param code
     * @Return: void
     * @Author: chenyx
     * @Date: 2020/5/26 20:42
     **/
    @Override
    public void editPlaceCollection(String place_id, int code) {

        Map<String, Object> result = placeDao.editPlaceCollection(place_id, code);

        if ((Integer) result.get("count") > 0) {

            Place place = (Place) result.get("place");

            if (code == PlaceEnum.COLLECTION_INCREASE.getCode()) {

                place.setCollection(place.getCollection() + 1);
            } else {

                place.setCollection(place.getCollection() - 1);
            }

            //更新redis
            placeRedis.editAPlaceToRedisAllPlaces(place);

        }

    }


    public Map<String, Object> getResult(List<Place> places, String phone) {

        List<Map<String, Object>> placeMapList = new ArrayList<>();

        for (Place place : places) {

            Map<String, Object> map = new HashMap<>();

            //返回该景点的前置评论
            PagePlaceCommentVO pagePlaceCommentVO = placeCommentService.selectPlaceCommentPageByPlaceId(phone, place.getPlace_id(), 0, PLACE_COMMENT_QUANTITY);

            PlaceItemVO placeItemVO = new PlaceItemVO();
            BeanUtils.copyProperties(place, placeItemVO);

            map.put("place", placeItemVO);
            map.put("comment", pagePlaceCommentVO);

            placeMapList.add(map);

        }


        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("places", placeMapList);

        return resultMap;

    }


}

