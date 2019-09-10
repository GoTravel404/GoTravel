package com.gotravel.dao.nosqldao;


import com.gotravel.pojo.Place;
import com.gotravel.pojo.User_detailed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @Description: mongodb的Place表的CURD实现层
 *  @date 2019年8月4日 下午8:16:34
 */
@Repository
public class PlaceDaoImpl implements PlaceDao{
	
	@Autowired
	 private MongoTemplate mongoTemplate;
	

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
		List<String> hobby=user_detailed.getHobby();
		List<String> customization=user_detailed.getCustomization();
		//根据用户的hobby与customization进行查询
		Criteria criteriahobby = Criteria.where("hobby").in(hobby);
		Criteria criteriacustomization = Criteria.where("customization").in(customization);
		Criteria criteria=new Criteria();
		Query query = new Query(criteria.orOperator(criteriahobby,criteriacustomization));
		 //query.with(new Sort(new Order(Direction.DESC,"praise")));
		 query.with(new Sort(Direction.DESC,  "praise"));
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
		return  mongoTemplate.findOne(query, Place.class);
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
		 query.with(new Sort(Direction.DESC,  "praise"));
		return  mongoTemplate.find(query, Place.class);
	}


}
