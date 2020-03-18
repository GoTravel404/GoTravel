package com.gotravel.dao.nosqldao;

import com.gotravel.common.places_distance.PlacesDistance;
import com.gotravel.dao.redis.PlaceRedis;
import com.gotravel.entity.Place;
import com.gotravel.entity.UserDetailed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Description:TODO mongodb的Place表的CURD实现层
 *  @date 2019年8月4日 下午8:16:34
 */
@Slf4j
@Repository
public class PlaceDaoImpl implements PlaceDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    //查询redis缓存里所有的景点信息
    @Autowired
    private PlaceRedis placeRedis;


    /**
     * @Title findByuser_label
     * @Description: TODO 根据用户的标签+地点设定的范围为用户提供景点且按好评度排序
     * @param user_detailed 用户标签
     * @param distance 距离
     * @param lon 经度
     * @param lat 维度
     * @return List<Map < String , Object>>
     * @Author: chenyx
     * @Date: 2019/9/21  10:22
     **/
    @Override
    public List<Map<String, Object>> findByUserLabel(UserDetailed user_detailed, int distance, double lon, double lat) {

        List<String> hobby = user_detailed.getHobby();
        List<String> customization = user_detailed.getCustomization();

        //根据用户的hobby与customization进行查询
        Criteria criteriaHobby = Criteria.where("hobby").in(hobby);
        Criteria criteriaCustomization = Criteria.where("customization").in(customization);
        Criteria criteria = new Criteria();
        Query query = new Query(criteria.orOperator(criteriaHobby, criteriaCustomization));

        query.with(new Sort(Direction.DESC, "praise"));
        List<Place> placesList = mongoTemplate.find(query, Place.class);

        return PlacesDistance.getFitDistancePlaces(placesList, distance, lon, lat);
    }





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
     * @Date: 2019/9/21  15:30
     **/
    @Override
    public List<Map<String, Object>> findPlacesByPlaceLabel(List<String> hobby, List<String> customization, List<String> place_type, int distance, double lon, double lat) {

        // TODO

        //查询redis缓存里所有的景点信息
        List redisPlacesList = placeRedis.getRedisAllPlacesList();
        List placesListByHobby, placesListByCus, placesListByP_t;

        //符合hobby的景点
        if (0 != hobby.size()) {
            Query query1 = new Query(Criteria.where("hobby").in(hobby));
            query1.with(new Sort(Direction.DESC, "praise"));
            placesListByHobby = mongoTemplate.find(query1, Place.class);
        } else {
            placesListByHobby = redisPlacesList;
        }

        //符合customization的景点
        if (0 != customization.size()) {
            Query query2 = new Query(Criteria.where("customization").in(customization));
            query2.with(new Sort(Direction.DESC, "praise"));
            placesListByCus = mongoTemplate.find(query2, Place.class);
        } else {
            placesListByCus = redisPlacesList;
        }

        //符合place_type的景点
        if (0 != place_type.size()) {
            Query query3 = new Query(Criteria.where("place_type").in(place_type));
            query3.with(new Sort(Direction.DESC, "praise"));
            placesListByP_t = mongoTemplate.find(query3, Place.class);
        } else {
            placesListByP_t = redisPlacesList;
        }

        /*三组Label都存在的景点*/
        placesListByHobby.retainAll(placesListByCus);//取交集方法retainAll;如果存在相同元素,placesListByHobby仅保留相同的元素,否则为空。

        placesListByHobby.retainAll(placesListByP_t);

        List<Map<String, Object>> maps = PlacesDistance.getFitDistancePlaces(placesListByHobby, distance, lon, lat);

        return maps;
    }


    /**
     * @Title findPlacesByPraise
     * @Description: TODO 根据好评度(热门)+地点设定的范围返回景点信息且按好评度排序
     * @param distance 范围
     * @param lon 经度
     * @param lat 维度
     * @return List<Map < String , Object>>
     * @Author: chenyx
     * @Date: 2019/9/20  16:28
     **/
    @Override
    public List<Map<String, Object>> findPlacesByPraise(int distance, double lon, double lat) {
        //查询所有景点信息
        List placesList = placeRedis.getRedisAllPlacesList();

        //通过Collections类的sort()方法降序排序
        Collections.sort(placesList);

        //匹配符合距离范围的景点
        List<Map<String, Object>> maps = PlacesDistance.getFitDistancePlaces(placesList, distance, lon, lat);

        return maps;
    }


}
