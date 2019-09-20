package com.gotravel.dao.nosqldao;


import com.gotravel.common.places_distance.Places_distance;
import com.gotravel.model.Place;
import com.gotravel.model.User_detailed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:TODO mongodb的Place表的CURD实现层
 *  @date 2019年8月4日 下午8:16:34
 */

@Repository
public class PlaceDaoImpl implements PlaceDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    /**注入springboot自动配置好的redisTemplate**/
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * @Title findByuser_label
     * @Description:TODO 根据用户的标签为用户提供景点且按好评度排序
     * @Param [user_detailed]
     * @return java.util.List<Place>
     * @Author: 陈一心
     * @Date: 2019/9/8  21:52
     **/
    @Override
    public List<Place> findByuser_label(User_detailed user_detailed) {
        // TODO Auto-generated method stub
        List<String> hobby = user_detailed.getHobby();
        List<String> customization = user_detailed.getCustomization();
        //根据用户的hobby与customization进行查询
        Criteria criteriahobby = Criteria.where("hobby").in(hobby);
        Criteria criteriacustomization = Criteria.where("customization").in(customization);
        Criteria criteria = new Criteria();
        Query query = new Query(criteria.orOperator(criteriahobby, criteriacustomization));
        //query.with(new Sort(new Order(Direction.DESC,"praise")));
        query.with(new Sort(Direction.DESC, "praise"));
        return mongoTemplate.find(query, Place.class);
    }


    /**
     * @Title findPlaceByplace_id
     * @Description:TODO 根据景点的place_id返回景点信息
     * @Param [place_id]
     * @return Place
     * @Author: 陈一心
     * @Date: 2019/9/8  21:53
     **/
    @Override
    public Place findPlaceByplace_id(int place_id) {
        // TODO Auto-generated method stub
        Query query = new Query(Criteria.where("place_id").is(place_id));
        return mongoTemplate.findOne(query, Place.class);
    }


    /**
     * @Title findPlaceByplace_type
     * @Description:TODO 根据景点的place_type(封装成List类型)返回景点信息且按好评度排序
     * @Param [place_type]
     * @return java.util.List<Place>
     * @Author: 陈一心
     * @Date: 2019/9/8  21:53
     **/
    @Override
    public List<Place> findPlaceByplace_type(List<String> place_type) {
        // TODO Auto-generated method stub
        Query query = new Query(Criteria.where("place_type").in(place_type));
        query.with(new Sort(Direction.DESC, "praise"));
        return mongoTemplate.find(query, Place.class);
    }


    /**
     * @Title findPlacesByPraise
     * @Description: TODO 根据好评度(热门)+地点设定的范围返回景点信息且按好评度排序
     * @param distance 范围
     * @param lon 经度
     * @param lat 维度
     * @return java.util.List<com.gotravel.model.Place>
     * @Author: chenyx
     * @Date: 2019/9/20  16:28
     **/
    @Override
    public List<Place> findPlacesByPraise(int distance, double lon, double lat) {
        List rsplaceList = new ArrayList<>();
        //字符串序列化器
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);

        /**查询所有景点信息**/
        //查询缓存
        List placesList = redisTemplate.opsForList().range("AllPlacesList", 0, -1);
        //双重检测锁
        if (null == placesList) {
            synchronized (this) {  //加锁
                //从redis获取一下
                placesList = redisTemplate.opsForList().range("AllPlacesList", 0, -1);
                if (null == placesList) {   //缓存为空,查询一遍数据库
                    System.out.println("\n查询数据库----------------------");
                    Query query = new Query().with(new Sort(Direction.DESC, "praise"));//按按好评度高到低排序
                    placesList = mongoTemplate.find(query, Place.class);
                    //把数据库查询出来的数据，放到redis中,顺带返回符合距离范围的景点

                    rsplaceList = getFitDistancePlaces(placesList, distance, lon, lat, 0);
                    return rsplaceList;
                } else {
                    System.out.println("\n查询缓存----------------------");
                }
            }
        } else {
            System.out.println("\n查询缓存----------------------");
        }
        rsplaceList = getFitDistancePlaces(placesList, distance, lon, lat, 1);
        return rsplaceList;
    }


    /**
     * @Title getFitDistancePlaces
     * @Description: TODO 匹配符合距离范围的景点
     * @param placesList 景点Lsit
     * @param distance 范围
     * @param lon 经度
     * @param lat 维度
     * @return java.util.List<com.gotravel.model.Place>
     * @Author: chenyx
     * @Date: 2019/9/20  23:31
     **/
    private List<Place> getFitDistancePlaces(List<Place> placesList, int distance, double lon, double lat, int code) {
        List<Place> placeList = new ArrayList<>(); //转载符合的范围的景点

        for (Place place : placesList) {
            String Longitude_latitude = place.getLongitude_latitude();

            //当出入的code为1时，借助遍历把数据库查询出来的数据，放到redis中,
            if (code == 0) {
                redisTemplate.opsForList().rightPush("AllPlacesList", place);
            }
            if (null != Longitude_latitude && !Longitude_latitude.equals("") && !Longitude_latitude.equals("null") && Longitude_latitude.length() != 0 && Longitude_latitude != "") {//经纬度不为空才进行判断
                String[] lal = Longitude_latitude.trim().split(",");//分解经纬度字符串
                Double longitude = Double.parseDouble(lal[1]);  //经度
                Double latitude = Double.parseDouble(lal[0]); //维度
                Double sure_distance = Places_distance.getDistance(lon, lat, longitude, latitude);
                int int_distance = Integer.parseInt(new java.text.DecimalFormat("0").format(sure_distance));//double转为int

                if (int_distance <= distance) { //地点在指定的范围中
                    System.out.print("-" + int_distance);
                    placeList.add(place);
                }
            }
        }
        return placeList;
    }


}
