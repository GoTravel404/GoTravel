package com.gotravel.repository.nosqldao;

import com.gotravel.entity.Place;
import com.gotravel.entity.UserDetailed;
import com.gotravel.enums.PlaceEnum;
import com.gotravel.repository.redis.PlaceRedis;
import com.gotravel.utils.OtherUtils;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
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
     * @Title findPlacesByUserLabel
     * @Description: TODO 根据用户的标签为用户提供景点且按好评度排序
     * @param user_detailed 用户标签
     * @Return: java.util.List<Place>
     * @Author: chenyx
     * @Date: 2020/3/19 17:13
     **/
    @Override
    public List<Place> findPlacesByUserLabel(UserDetailed user_detailed) {

        List<String> hobby = user_detailed.getHobby();
        List<String> customization = user_detailed.getCustomization();

        Criteria criteriaHobby = null, criteriaCustomization = null;

        Criteria criteria = new Criteria();

        //根据用户的hobby与customization进行查询
        if (null != hobby && 0 != hobby.size())
            criteriaHobby = Criteria.where("hobby").in(hobby);

        if (null != customization && 0 != customization.size())
            criteriaCustomization = Criteria.where("customization").in(customization);


        if (null != criteriaHobby && null != criteriaCustomization) {
            criteria.orOperator(criteriaHobby, criteriaCustomization);

        } else if (null != criteriaHobby) {
            criteria.orOperator(criteriaHobby);

        } else if (null != criteriaCustomization) {
            criteria.orOperator(criteriaCustomization);
        }


        //景点状态为启动
        criteria.andOperator(Criteria.where("status").in(PlaceEnum.ACTIVE.getCode()));

        Query query = new Query(criteria);

        query.with(new Sort(Direction.DESC, "praise"));

        query.fields().exclude("introduce");
        query.fields().exclude("picture");
        query.fields().exclude("status");

        return mongoTemplate.find(query, Place.class);

    }


    /**
     * @Title findPlacesByPlaceLabel
     * @Description: TODO 根据景点的Label(封装成三组List类型，有List<hobby>、List<customization>、List<place_type>)返回景点信息且按好评度排序
     * 取并集
     * @param hobby
     * @param customization
     * @param place_type
     * @Return: java.util.List<com.gotravel.entity.Place>
     * @Author: chenyx
     * @Date: 2020/3/19 20:46
     **/
    @Override
    public List<Place> findPlacesByPlaceLabel(List<String> hobby, List<String> customization, List<String> place_type) {

        //查询条件
        Criteria criteria = OtherUtils.getCriteriaOfOrOperatorForLabel(place_type, hobby, customization);

        Query query = new Query(criteria);

        //景点状态为启动
        query.addCriteria(Criteria.where("status").is(PlaceEnum.ACTIVE.getCode()));

        //降序
        query.with(new Sort(Sort.Direction.DESC, "praise"));

        query.fields().exclude("introduce");
        query.fields().exclude("picture");
        query.fields().exclude("status");

        return mongoTemplate.find(query, Place.class);

    }


    /**
     * @Title increasePlacePraise
     * @Description: 景点添加好评
     * @param place_id
     * @Return: java.util.Map<java.lang.String, java.lang.Object>
     * @Author: chenyx
     * @Date: 2020/5/26 20:25
     **/
    @Override
    public Map<String, Object> increasePlacePraise(String place_id) {

        Map<String, Object> resultMap = new HashMap<>();

        Query query = new Query(Criteria.where("place_id").is(place_id));

        Place place = mongoTemplate.findOne(query, Place.class);

        if (place == null) {

            resultMap.put("count", 0);

            return resultMap;

        }

        Update update = new Update().set("praise", place.getPraise() + 1);

        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Place.class);

        resultMap.put("count", (int) updateResult.getModifiedCount());
        resultMap.put("place", place);

        return resultMap;


    }


    /**
     * @Title editPlaceCollection
     * @Description: 修改景点收藏数
     * @param place_id
     * @param code
     * @Return: java.util.Map<java.lang.String, java.lang.Object>
     * @Author: chenyx
     * @Date: 2020/5/26 20:46
     **/
    @Override
    public Map<String, Object> editPlaceCollection(String place_id, int code) {

        Map<String, Object> resultMap = new HashMap<>();

        Query query = new Query(Criteria.where("place_id").is(place_id));

        Place place = mongoTemplate.findOne(query, Place.class);

        Update update = new Update();

        if (place == null) {

            resultMap.put("count", 0);

            return resultMap;

        }

        if (code == PlaceEnum.COLLECTION_INCREASE.getCode()) {

            update.set("collection", place.getCollection() + 1);

        } else {

            update.set("collection", place.getCollection() - 1);
        }

        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Place.class);

        resultMap.put("count", (int) updateResult.getModifiedCount());
        resultMap.put("place", place);

        return resultMap;
    }


    /**
     * @Title findPlacesByPlaceLabelExceptPlaceIdList
     * @Description: 根据景点的Label(封装成三组List类型 ， 有List < hobby > 、 List < customization > 、 List < place_type >)返回景点信息且按好评度排序, 去除特定的景点
     * @param hobbyList
     * @param customizationList
     * @param place_typeList
     * @param placeIdList
     * @Return: java.util.List<com.gotravel.entity.Place>
     * @Author: chenyx
     * @Date: 2020/5/6 17:36
     **/
    @Override
    public List<Place> findPlacesByPlaceLabelExceptPlaceIdList(List<String> hobbyList, List<String> customizationList, List<String> place_typeList, List<String> placeIdList) {

        //查询条件
        Criteria criteria = OtherUtils.getCriteriaOfOrOperatorForLabel(place_typeList, hobbyList, customizationList);

        Query query = new Query(criteria);

        //不好含的景点
        query.addCriteria(Criteria.where("place_id").nin(placeIdList));

        //景点状态为启动
        query.addCriteria(Criteria.where("status").is(PlaceEnum.ACTIVE.getCode()));

        //降序
        query.with(new Sort(Sort.Direction.DESC, "praise"));

        query.fields().exclude("introduce");
        query.fields().exclude("picture");
        query.fields().exclude("status");

        return mongoTemplate.find(query, Place.class);

    }


}
