package com.gotravel.dao.nosqldao;


import com.google.gson.Gson;
import com.gotravel.common.Common;
import com.gotravel.model.*;
import com.mongodb.BasicDBObject;
import com.mongodb.client.result.UpdateResult;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @Description: mongodb的User_Detailed表的CURD实现层
 *  @date 2019年8月10日 下午9:55:05
 */
@Repository
public class UserDetailedDaoImpl  implements  UserDetailedDao{

	@Autowired
	 private MongoTemplate mongoTemplate;


	/**
	 * @Title findOne
	 * @Description:TODO 根据手机phone查找用户的详细信息
	 * @Param [phone]
	 * @return User_detailed
	 * @Author: 陈一心
	 * @Date: 2019/9/8  21:59
	 **/
	@Override
	public User_detailed findOne(String phone) {
		// TODO Auto-generated method stub
		 Query query= Query.query(Criteria.where("phone").is(phone));
		return mongoTemplate.findOne(query, User_detailed.class);
	}


	/**
	 * @Title adduserdetailed
	 * @Description:TODO 注册账号添加用户详细信息
	 * @Param [user_detailed]
	 * @return void
	 * @Author: 陈一心
	 * @Date: 2019/9/8  22:00
	 **/
	@Override
	public void adduserdetailed(User_detailed user_detailed) {
		// TODO Auto-generated method stub
		 mongoTemplate.save(user_detailed);
	}


	/**
	 * @Title edit_label
	 * @Description:TODO 根据phone编辑个人标签
	 * @Param [phone, hobby, customization]
	 * @return int
	 * @Author: 陈一心
	 * @Date: 2019/9/8  22:00
	 **/
	@Override
	public int edit_label(String phone, List<String> hobby, List<String> customization) {
		// TODO Auto-generated method stub
			Query query = new Query(Criteria.where("phone").is(phone));
            Update update =new Update().set("hobby",hobby).set("customization", customization);
            UpdateResult updateResult= mongoTemplate.updateFirst(query, update,User_detailed.class);
            return (int) updateResult.getModifiedCount();
	}


	/**
	 * @Title findByphone
	 * @Description:TODO 根据phone提供该用户的个人详细信息
	 * @Param [phone]
	 * @return User_detailed
	 * @Author: 陈一心
	 * @Date: 2019/9/8  22:01
	 **/
	@Override
	public User_detailed findByphone(String phone) {
		// TODO Auto-generated method stub
		Query query = new Query(Criteria.where("phone").is(phone));
		return mongoTemplate.findOne(query, User_detailed.class);
	}


	/**
	 * @Title addmycollections
	 * @Description:TODO 根据phone添加个人收藏景点
	 * @Param [phone, place_id, time]
	 * @return int
	 * @Author: 陈一心
	 * @Date: 2019/9/8  22:01
	 **/
	@Override
	public int addmycollections(String phone, int place_id, Date time) {
		// TODO Auto-generated method stub
		Query query = new Query(Criteria.where("phone").is(phone));
		Placeid_Time placeid_Time=new Placeid_Time(place_id,time);

		//无论是否存在先删除可能已经存在相同景点了
		BasicDBObject object = new BasicDBObject();
		object.put("place_id", place_id);
		Update update1 =new Update().pull("mycollections", object);
		mongoTemplate.updateFirst(query, update1, User_detailed.class);


		//添加新的景点收藏
		Update update2 =new Update().addToSet("mycollections",placeid_Time);
		UpdateResult updateResult= mongoTemplate.updateFirst(query, update2, User_detailed.class);
		return (int) updateResult.getModifiedCount();
	}


	/**
	 * @Title deletemycollections
	 * @Description:TODO 根据phone和places_id删除个人收藏景点
	 * @Param [phone, place_id]
	 * @return int
	 * @Author: 陈一心
	 * @Date: 2019/9/8  22:01
	 **/
	@Override
	public int deletemycollections(String phone, int place_id) {
		// TODO Auto-generated method stub
		Query query = new Query(Criteria.where("phone").is(phone));
		//删除操作构建一个带places_id的DB对象
		BasicDBObject object = new BasicDBObject();
	    object.put("place_id", place_id);
		Update update = new Update().pull("mycollections", object);
		UpdateResult updateResult=mongoTemplate.updateFirst(query, update, User_detailed.class);
		return (int) updateResult.getModifiedCount();
	}


	/**
	 * @Title addmyplans
	 * @Description:TODO 根据phone添加个人出行计划
	 * @Param [phone, plan_name, places_id, time]
	 * @return int
	 * @Author: 陈一心
	 * @Date: 2019/9/8  22:01
	 **/
	@Override
	public int addmyplans(String phone, String plan_name,List<Integer> places_id, Date time) {
		// TODO Auto-generated method stub
		Query query = new Query(Criteria.where("phone").is(phone));
		Myplan myplan=new Myplan(plan_name,places_id, time);
		Update update =new Update().push("myplans",myplan);
		UpdateResult updateResult=mongoTemplate.updateFirst(query, update, User_detailed.class);
		return (int) updateResult.getModifiedCount();
	}


	/**
	 * @Title editmyplans
	 * @Description:TODO 根据phone和time修改个人出行计划
	 * @Param [phone, plan_name, places_id, time]
	 * @return int
	 * @Author: 陈一心
	 * @Date: 2019/9/8  22:01
	 **/
	@Override
	public int editmyplans(String phone, String plan_name, List<Integer> places_id, Date time) {
		// TODO Auto-generated method stub
		Query query = new Query(Criteria.where("phone").is(phone).and("myplans.time").is(time));
		Update update = new Update().set("myplans.$.places_id", places_id).set("myplans.$.plan_name", plan_name);
		UpdateResult updateResult = mongoTemplate.updateFirst(query, update, User_detailed.class);
		return (int) updateResult.getModifiedCount();
	}


	/**
	 * @Title deletemyplans
	 * @Description:TODO 根据phone和time删除个人出行计划
	 * @Param [phone, time]
	 * @return int
	 * @Author: 陈一心
	 * @Date: 2019/9/8  22:02
	 **/
	@Override
	public int deletemyplans(String phone, Date time) {
		// TODO Auto-generated method stub
		Query query = new Query(Criteria.where("phone").is(phone));
		//删除操作构建一个带time的DB对象
		BasicDBObject object = new BasicDBObject();
	    object.put("time", time);
		Update update = new Update().pull("myplans", object);
	    UpdateResult updateResult=mongoTemplate.updateFirst(query, update, User_detailed.class);
	    return (int) updateResult.getModifiedCount();
	}


	/**
	 * @Title addhistory
	 * @Description:TODO 根据phone和time(date)添加历史出行
	 * @Param [phone, place_id, dateStr]
	 * @return int
	 * @Author: 陈一心
	 * @Date: 2019/9/8  22:02
	 **/
	@Override
	public int addhistory(String phone, int place_id, String dateStr) {
		// TODO Auto-generated method stub
		 UpdateResult updateResult=null;
          String  date = dateStr.split("\\s+")[0];    //以空格区分yyyy-MM-dd和HH:mm:ss，截取年月日
	      Date time = Common.TimeConversion(dateStr);	//时间转换
	      Placeid_Time places_time=new Placeid_Time(place_id, time);		//到达一个景点和时间
	      Query query1 = new Query(Criteria.where("phone").is(phone).and("myhistories.date").is(date));	//判断是否已存在年月日(date)
	      User_detailed user_detailed=mongoTemplate.findOne(query1, User_detailed.class);
	  /**
	   * 判断是否已经存在当天的历史出行
	   */
	  if(user_detailed==null){	//不存在则创建当天的历史出行
	      List<Placeid_Time> placeid_Times=new ArrayList<Placeid_Time>( );	//到达的多个景点和时间
	      placeid_Times.add(places_time);
	      Myhistory myhistory=new Myhistory(placeid_Times, date);		//当天的历史出行
	      Query query2= new Query(Criteria.where("phone").is(phone));
	      Update update = new Update().push("myhistories", myhistory);
	      updateResult= mongoTemplate.updateFirst(query2, update, User_detailed.class);
	  }
	  else{	//存在则直接添加到当天的出行计划
	      Update update = new Update().push("myhistories.$.places_time", places_time);
	      updateResult= mongoTemplate.updateFirst(query1, update, User_detailed.class);
	  }
	     return (int) updateResult.getModifiedCount();
	}


	/**
	 * @Title deletehistoryplace
	 * @Description:TODO 根据phone和date删除历史出行的单个景点
	 * @Param [phone, place_id, date]
	 * @return int
	 * @Author: 陈一心
	 * @Date: 2019/9/8  22:02
	 **/
	@Override
	public int deletehistoryplace(String phone, int place_id, String date) {
		// TODO Auto-generated method stub
		int result = 0;
		Query query = new Query(Criteria.where("phone").is(phone).and("myhistories.date").is(date));
		// 计算剩余的历史出行景点数，为0，则把整个历史出行记录删除
		query.fields().include("myhistories");
		User_detailed user_detailed = mongoTemplate.findOne(query, User_detailed.class);
		for (Object myhistoriesoObject : user_detailed.getMyhistories()) {
			Myhistory myhistory = (Myhistory) myhistoriesoObject;
			if (myhistory.getDate().equals(date)) {
				if (myhistory.getPlaces_time().size() == 1) {
					BasicDBObject object1 = new BasicDBObject();
					object1.put("date", date);
					Update update1 = new Update().pull("myhistories", object1);
					mongoTemplate.updateFirst(query, update1, User_detailed.class);
					result = 1;
				}
			} else {
				// 删除操作构建一个带place_id的DB对象
				BasicDBObject object = new BasicDBObject();
				object.put("place_id", place_id);
				Update update = new Update().pull("myhistories.$.places_time", object);
				UpdateResult updateResult = mongoTemplate.updateFirst(query, update, User_detailed.class);
				result = (int) updateResult.getModifiedCount();
			}
		}

		return result;
	}


	/**
	 * @Title deletehistory
	 * @Description:TODO 根据phone和date删除历史出行记录
	 * @Param [phone, date]
	 * @return int
	 * @Author: 陈一心
	 * @Date: 2019/9/8  22:02
	 **/
	@Override
	public int deletehistory(String phone, String date) {
		// TODO Auto-generated method stub
		Query query = new Query(Criteria.where("phone").is(phone));
		//删除操作构建一个带date的DB对象
		BasicDBObject object = new BasicDBObject();
	    object.put("date", date);
		Update update = new Update().pull("myhistories", object);
		UpdateResult updateResult=mongoTemplate.updateFirst(query, update, User_detailed.class);
	    return (int) updateResult.getModifiedCount();
	}


	/**
	 * @Title findmycollections
	 * @Description:TODO 根据phone联表(user_detailed和place)查询所有个人收藏的景点
	 * @Param [phone]
	 * @return java.lang.String
	 * @Author: 陈一心
	 * @Date: 2019/9/8  22:03
	 **/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String findmycollections(String phone) {
		// TODO Auto-generated method stub
		//拼装信息
		AggregationOperation lookup = Aggregation.lookup("place", "mycollections.place_id", "place_id","mycollections_detailed"); 		//关联从表名, 主表关联字段,从表关联的字段,设置查询结果集合名
		AggregationOperation unwind = Aggregation.unwind("mycollections_detailed");		 //以什么分散
		AggregationOperation match = Aggregation.match(Criteria.where("phone").is(phone));	//主表的条件
	    AggregationOperation project = Aggregation.project("mycollections_detailed.place_id","mycollections_detailed.name", "mycollections_detailed.picture","mycollections.time").andExclude("_id");	//返回的字段，不返回自带的“_id”
		//将条件封装到Aggregate管道
		Aggregation aggregation = Aggregation.newAggregation(lookup,match,unwind,project);
		// 可能需要这个
		// withOptions(Aggregation.newAggregationOptions().cursorBatchSize(2000).build());
		// withOptions(Aggregation.newAggregationOptions().cursor(new Document()).build());
		List<Map> results = mongoTemplate.aggregate(aggregation, "user_detailed", Map.class).getMappedResults();
		/**
		 * 获取正确的时间time
		 * PS:代码不友好，如果有其他方法最好，例如直接在Aggregation聚合中获取最好
		 */
		int i=0;
		for(Map<String, Object> map:results){
			List<String> dates=  (List<String>) map.get("time");
			map.put("time", dates.get(i));
			i++;
		}
		//results转为json格式
		Gson gson = new Gson();
		String jsonString = gson.toJson(results);
		return jsonString;
	}


	/**
	 * @Title findmyhistories
	 * @Description:TODO 根据phone查询所有历史出行(返回所有日期的历史出行)
	 * @Param [phone]
	 * @return java.util.List<Myhistory>
	 * @Author: 陈一心
	 * @Date: 2019/9/8  22:03
	 **/
	@Override
	public List<Myhistory> findmyhistories(String phone) {
		// TODO Auto-generated method stub
		Query query = new Query(Criteria.where("phone").is(phone));
		query.fields().include("myhistories.places_time").include("myhistories.date");
		 User_detailed user_detailed=  mongoTemplate.findOne(query, User_detailed.class);
		return  user_detailed.getMyhistories();
	}

//有问题！！！！！！！！！！！！！！！！！！！
	/**
	 * @Title findmyhistories_detailed
	 * @Description:TODO 根据phone和date联表(user_detailed和place)查找某一天的历史出行详情列表
	 * @Param [phone, date]
	 * @return java.lang.String
	 * @Author: 陈一心
	 * @Date: 2019/9/8  22:03
	 **/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String findmyhistories_detailed(String phone, String date) {
		// TODO Auto-generated method stub
		//拼装信息
		AggregationOperation lookup = Aggregation.lookup("place", "myhistories.places_time.place_id", "place_id","myhistories_detailed"); 		//关联从表名, 主表关联字段,从表关联的字段,设置查询结果集合名
		AggregationOperation unwind = Aggregation.unwind("myhistories_detailed");		 //以什么分散
		BasicDBObject object = new BasicDBObject();
		object.put("date", date);
		AggregationOperation match = Aggregation.match(Criteria.where("phone").is(phone).and("myhistories").elemMatch(Criteria.where("date").is(date)));	//主表的条件
	    AggregationOperation project = Aggregation.project("myhistories_detailed.place_id","myhistories_detailed.name", "myhistories_detailed.picture").andExclude("_id");	//返回的字段，不返回自带的“_id”
		//将条件封装到Aggregate管道
		Aggregation aggregation = Aggregation.newAggregation(lookup,match,unwind,project);
		List<Map> results = mongoTemplate.aggregate(aggregation, "user_detailed", Map.class).getMappedResults();
		/**
		 * 返回历史出行景点对应的时间time
		 * PS:代码很不友好，如果有其他方法最好，例如直接在Aggregation聚合中获取最好
		 */
		Query query = new Query(Criteria.where("phone").is(phone));
		query.fields().include("myhistories");
		User_detailed user_detailed = mongoTemplate.findOne(query, User_detailed.class);
		int i = 0;
		for (Object myhistoryObject : user_detailed.getMyhistories()) {
			Myhistory myhistory = (Myhistory) myhistoryObject;
			if (myhistory.getDate().equals(date)) //查找日期为date的当天出行历史记录
				System.out.println("myhistory.getPlaces_time().size():"+myhistory.getPlaces_time().size()+"----------------results:"+results.size());
				for (Map<String, Object> map : results) {
					//map.put("time", myhistory.getPlaces_time().get(i).getTime());
					i++;
				}
		}
		//results转为json格式
		Gson gson = new Gson();
		String jsonString = gson.toJson(results);
		return jsonString;
	}

	/**
	 * @Title findmyplans
	 * @Description:TODO 根据phone查找所有出行计划(返回所有出行计划的名称列表)
	 * @Param [phone]
	 * @return java.util.List<Myplan>
	 * @Author: 陈一心
	 * @Date: 2019/9/8  22:03
	 **/
	@Override
	public List<Myplan> findmyplans(String phone) {
		// TODO Auto-generated method stub
		Query query = new Query(Criteria.where("phone").is(phone));
		query.fields().include("myplans");
		query.with(new Sort(Direction.DESC,  "myplans.time"));	 									//没有效果
		 User_detailed user_detailed=  mongoTemplate.findOne(query, User_detailed.class);
		return  user_detailed.getMyplans();
	}


	/**
	 * @Title findmyplans_detailed
	 * @Description:TODO 根据phone和time联表(user_detailed和place)查询出行计划详情列表
	 * @Param [phone, time]
	 * @return java.lang.String
	 * @Author: 陈一心
	 * @Date: 2019/9/8  22:04
	 **/
	@Override
	public String findmyplans_detailed(String phone, Date time) {
		// TODO Auto-generated method stub
		/**
		 * 注释的代码查询结果为空，怀疑是"lookup"的参数"myplans.places_id.$"的问题
		 */
//		AggregationOperation lookup = Aggregation.lookup("place", "myplans.places_id.$", "place_id","myplans_detailed"); 		//关联从表名, 主表关联字段,从表关联的字段,设置查询结果集合名
//		AggregationOperation unwind = Aggregation.unwind("myplans_detailed");		 //以什么分散
//		AggregationOperation match = Aggregation.match(Criteria.where("phone").is(phone));	//主表的条件
//	   // AggregationOperation project = Aggregation.project("myplans_detailed.place_id","myplans_detailed.name", "myplans_detailed.picture").andExclude("_id");	//返回的字段，不返回自带的“_id”
//		//将条件封装到Aggregate管道
//		Aggregation aggregation = Aggregation.newAggregation(lookup,match,unwind);	
//		List<Map> results = mongoTemplate.aggregate(aggregation, "user_detailed", Map.class).getMappedResults();
//		Gson gson = new Gson();	
//		String jsonString = gson.toJson(results);	
		/**
		 * 临时采用这种方法
		 */
		String plan_name=null;
		List<Integer> listplaces = null;
		List<Place> places = new ArrayList<Place>();
		JSONObject jsonObject = new JSONObject();
		Query query = new Query(Criteria.where("phone").is(phone));
		query.fields().include("myplans");
		User_detailed user_detailed = mongoTemplate.findOne(query, User_detailed.class);
		for (Object myplanObject : user_detailed.getMyplans()) {
			Myplan myplan = (Myplan) myplanObject;
			if (myplan.getTime().equals(time)) {          //获取出行计划的制定时间(time)为time的出行计划
				plan_name=myplan.getPlan_name();
				listplaces = myplan.getPlaces_id();		//获取计划中的景点编号列表
				for (Integer place_id : listplaces) {
					Query query1 = new Query(Criteria.where("place_id").is(place_id));
					query1.fields().include("place_id").include("name").include("picture").include("praise");
					Place place = mongoTemplate.findOne(query1, Place.class);
                    places.add(place);
				}
			}
		}
		jsonObject.put("plan_name", plan_name);
		jsonObject.put("places", places);
		jsonObject.put("time", time);
		return jsonObject.toString();
	}

	/**
	 * @Title findMyCollectionsPlaceId
	 * @Description: TODO 根据phone查找用户收藏的所有景点，组成List<Integer>
	 * @param phone 手机号
	 * @return java.util.List<java.lang.Integer>
	 * @Author: chenyx
	 * @Date: 2019/11/8  21:37
	 **/
	@Override
	public List<Integer> findMyCollectionsPlaceId(String phone) {
		Query query = new Query(Criteria.where("phone").is(phone));
		query.fields().include("mycollections");
		User_detailed user_detailed = mongoTemplate.findOne(query, User_detailed.class);
		List<Integer> collectionsPlaceIds=new ArrayList<>();
		for(Placeid_Time placeid_time:user_detailed.getMycollections()){
			collectionsPlaceIds.add( placeid_time.getPlaces_id());
		}
		return collectionsPlaceIds;
	}
}
