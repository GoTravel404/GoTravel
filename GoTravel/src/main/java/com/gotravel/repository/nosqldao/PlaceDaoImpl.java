package com.gotravel.repository.nosqldao;

import com.gotravel.repository.redis.PlaceRedis;
import com.gotravel.entity.Place;
import com.gotravel.entity.UserDetailed;
import com.gotravel.enums.PlaceEnum;
import com.gotravel.utils.OtherUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

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
     * @Title findByUserLabel
     * @Description: TODO 根据用户的标签为用户提供景点且按好评度排序
     * @param user_detailed 用户标签
     * @Return: java.util.List<Place>
     * @Author: chenyx
     * @Date: 2020/3/19 17:13
     **/
    @Override
    public List<Place> findPlacedByUserLabel(UserDetailed user_detailed) {

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

        List<Place> placeList = mongoTemplate.find(query, Place.class);

        return placeList;
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
        Criteria criteria= OtherUtils.getCriteriaOfOrOperatorForLabel(place_type,hobby,customization);

        Query query = new Query(criteria);

        //景点状态为启动
        query.addCriteria(Criteria.where("status").is(PlaceEnum.ACTIVE.getCode()));

        //降序
        query.with(new Sort(Sort.Direction.DESC, "praise"));

        return mongoTemplate.find(query, Place.class);

    }



}
