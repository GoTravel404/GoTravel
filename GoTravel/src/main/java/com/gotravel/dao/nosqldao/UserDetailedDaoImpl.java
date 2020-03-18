package com.gotravel.dao.nosqldao;


import com.alibaba.fastjson.JSONObject;
import com.gotravel.common.Common;
import com.gotravel.entity.Place;
import com.gotravel.entity.UserDetailed;
import com.gotravel.entity.node.MyHistory;
import com.gotravel.entity.node.MyPlan;
import com.gotravel.entity.node.PlaceIdTime;
import com.mongodb.BasicDBObject;
import com.mongodb.client.result.UpdateResult;
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
 * @Description: mongodb的UserDetailed表的CURD实现层
 *  @date 2019年8月10日 下午9:55:05
 */
@Repository
public class UserDetailedDaoImpl implements UserDetailedDao {

    @Autowired
    private MongoTemplate mongoTemplate;


    /**
     * @Title findOne
     * @Description:TODO 根据手机phone查找用户的详细信息
     * @Param [phone]
     * @return UserDetailed
     * @Author: 陈一心
     * @Date: 2019/9/8  21:59
     **/
    @Override
    public UserDetailed findOne(String phone) {

        Query query = Query.query(Criteria.where("phone").is(phone));

        return mongoTemplate.findOne(query, UserDetailed.class);
    }


    /**
     * @Title addUserDetailed
     * @Description:TODO 注册账号添加用户详细信息
     * @Param [UserDetailed]
     * @return void
     * @Author: 陈一心
     * @Date: 2019/9/8  22:00
     **/
    @Override
    public void addUserDetailed(UserDetailed UserDetailed) {

        mongoTemplate.save(UserDetailed);
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

        Query query = new Query(Criteria.where("phone").is(phone));
        Update update = new Update().set("hobby", hobby).set("customization", customization);

        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, UserDetailed.class);
        return (int) updateResult.getModifiedCount();
    }


    /**
     * @Title findByPhone
     * @Description:TODO 根据phone提供该用户的个人详细信息
     * @Param [phone]
     * @return UserDetailed
     * @Author: 陈一心
     * @Date: 2019/9/8  22:01
     **/
    @Override
    public UserDetailed findByPhone(String phone) {

        Query query = new Query(Criteria.where("phone").is(phone));
        return mongoTemplate.findOne(query, UserDetailed.class);
    }


    /**
     * @Title addMyCollections
     * @Description:TODO 根据phone添加个人收藏景点
     * @Param [phone, place_id, time]
     * @return int
     * @Author: 陈一心
     * @Date: 2019/9/8  22:01
     **/
    @Override
    public int addMyCollections(String phone, String place_id, Date time) {

        Query query = new Query(Criteria.where("phone").is(phone));
        PlaceIdTime PlaceIdTime = new PlaceIdTime(place_id, time);

        //无论是否存在先删除可能已经存在相同景点了
        BasicDBObject object = new BasicDBObject();
        object.put("place_id", place_id);
        Update update1 = new Update().pull("mycollections", object);
        mongoTemplate.updateFirst(query, update1, UserDetailed.class);

        //添加新的景点收藏
        Update update2 = new Update().addToSet("mycollections", PlaceIdTime);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update2, UserDetailed.class);

        return (int) updateResult.getModifiedCount();
    }


    /**
     * @Title deleteMyCollections
     * @Description:TODO 根据phone和places_id删除个人收藏景点
     * @Param [phone, place_id]
     * @return int
     * @Author: 陈一心
     * @Date: 2019/9/8  22:01
     **/
    @Override
    public int deleteMyCollections(String phone, String place_id) {

        Query query = new Query(Criteria.where("phone").is(phone));

        //删除操作构建一个带places_id的DB对象
        BasicDBObject object = new BasicDBObject();
        object.put("place_id", place_id);

        Update update = new Update().pull("mycollections", object);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, UserDetailed.class);

        return (int) updateResult.getModifiedCount();
    }


    /**
     * @Title addMyPlan
     * @Description:TODO 根据phone添加个人出行计划
     * @Param [phone, plan_name, places_id, time]
     * @return int
     * @Author: 陈一心
     * @Date: 2019/9/8  22:01
     **/
    @Override
    public int addMyPlan(String phone, String plan_name, List<String> places_id, Date time) {

        Query query = new Query(Criteria.where("phone").is(phone));

        MyPlan myplan = new MyPlan(plan_name, places_id, time);
        Update update = new Update().push("myplans", myplan);

        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, UserDetailed.class);
        return (int) updateResult.getModifiedCount();

    }


    /**
     * @Title editMyPlan
     * @Description:TODO 根据phone和time修改个人出行计划
     * @Param [phone, plan_name, places_id, time]
     * @return int
     * @Author: 陈一心
     * @Date: 2019/9/8  22:01
     **/
    @Override
    public int editMyPlan(String phone, String plan_name, List<String> places_id, Date time) {

        Query query = new Query(Criteria.where("phone").is(phone).and("myplans.time").is(time));

        Update update = new Update().set("myplans.$.places_id", places_id).set("myplans.$.plan_name", plan_name);

        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, UserDetailed.class);
        return (int) updateResult.getModifiedCount();
    }


    /**
     * @Title deleteMyPlan
     * @Description:TODO 根据phone和time删除个人出行计划
     * @Param [phone, time]
     * @return int
     * @Author: 陈一心
     * @Date: 2019/9/8  22:02
     **/
    @Override
    public int deleteMyPlan(String phone, Date time) {

        Query query = new Query(Criteria.where("phone").is(phone));

        //删除操作构建一个带time的DB对象
        BasicDBObject object = new BasicDBObject();
        object.put("time", time);

        Update update = new Update().pull("myplans", object);

        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, UserDetailed.class);

        return (int) updateResult.getModifiedCount();
    }


    /**
     * @Title addHistory
     * @Description:TODO 根据phone和time(date)添加历史出行
     * @Param [phone, place_id, dateStr]
     * @return int
     * @Author: 陈一心
     * @Date: 2019/9/8  22:02
     **/
    @Override
    public int addHistory(String phone, String place_id, String dateStr) {

        UpdateResult updateResult = null;
        String date = dateStr.split("\\s+")[0];    //以空格区分yyyy-MM-dd和HH:mm:ss，截取年月日
        Date time = Common.TimeConversion(dateStr);    //时间转换

        PlaceIdTime places_time = new PlaceIdTime(place_id, time);        //到达一个景点和时间
        Query query1 = new Query(Criteria.where("phone").is(phone).and("myhistories.date").is(date));    //判断是否已存在年月日(date)
        UserDetailed UserDetailed = mongoTemplate.findOne(query1, UserDetailed.class);
        /**
         * 判断是否已经存在当天的历史出行
         */
        if (UserDetailed == null) {    //不存在则创建当天的历史出行
            List<PlaceIdTime> PlaceIdTimes = new ArrayList<PlaceIdTime>();    //到达的多个景点和时间
            PlaceIdTimes.add(places_time);
            MyHistory myhistory = new MyHistory(PlaceIdTimes, date);        //当天的历史出行
            Query query2 = new Query(Criteria.where("phone").is(phone));
            Update update = new Update().push("myhistories", myhistory);
            updateResult = mongoTemplate.updateFirst(query2, update, UserDetailed.class);
        } else {    //存在则直接添加到当天的出行计划
            Update update = new Update().push("myhistories.$.places_time", places_time);
            updateResult = mongoTemplate.updateFirst(query1, update, UserDetailed.class);
        }
        return (int) updateResult.getModifiedCount();
    }


    /**
     * @Title deleteHistoryPlace
     * @Description:TODO 根据phone和date删除历史出行的单个景点
     * @Param [phone, place_id, date]
     * @return int
     * @Author: 陈一心
     * @Date: 2019/9/8  22:02
     **/
    @Override
    public int deleteHistoryPlace(String phone, String place_id, String date) {

        int result = 0;
        Query query = new Query(Criteria.where("phone").is(phone).and("myhistories.date").is(date));
        // 计算剩余的历史出行景点数，为0，则把整个历史出行记录删除
        query.fields().include("myhistories");

        UserDetailed UserDetailed = mongoTemplate.findOne(query, UserDetailed.class);

        for (Object myhistoriesoObject : UserDetailed.getMyHistories()) {
			MyHistory myhistory = (MyHistory) myhistoriesoObject;
            if (myhistory.getDate().equals(date)) {
                if (myhistory.getPlaces_time().size() == 1) {
                    BasicDBObject object1 = new BasicDBObject();
                    object1.put("date", date);
                    Update update1 = new Update().pull("myhistories", object1);
                    mongoTemplate.updateFirst(query, update1, UserDetailed.class);
                    result = 1;
                }
            } else {
                // 删除操作构建一个带place_id的DB对象
                BasicDBObject object = new BasicDBObject();
                object.put("place_id", place_id);
                Update update = new Update().pull("myhistories.$.places_time", object);
                UpdateResult updateResult = mongoTemplate.updateFirst(query, update, UserDetailed.class);
                result = (int) updateResult.getModifiedCount();
            }
        }

        return result;
    }


    /**
     * @Title deleteHistory
     * @Description:TODO 根据phone和date删除历史出行记录
     * @Param [phone, date]
     * @return int
     * @Author: 陈一心
     * @Date: 2019/9/8  22:02
     **/
    @Override
    public int deleteHistory(String phone, String date) {

        Query query = new Query(Criteria.where("phone").is(phone));
        //删除操作构建一个带date的DB对象
        BasicDBObject object = new BasicDBObject();
        object.put("date", date);

        Update update = new Update().pull("myhistories", object);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, UserDetailed.class);
        return (int) updateResult.getModifiedCount();

    }


    /**
     * @Title findMyCollections
     * @Description:TODO 根据phone联表(UserDetailed和place)查询所有个人收藏的景点
     * @Param [phone]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/8  22:03
     **/
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public String findMyCollections(String phone) {

        //拼装信息
        AggregationOperation lookup = Aggregation.lookup("place", "mycollections.place_id", "place_id", "mycollections_detailed");        //关联从表名, 主表关联字段,从表关联的字段,设置查询结果集合名
        AggregationOperation unwind = Aggregation.unwind("mycollections_detailed");         //以什么分散
        AggregationOperation match = Aggregation.match(Criteria.where("phone").is(phone));    //主表的条件
        AggregationOperation project = Aggregation.project("mycollections_detailed.place_id", "mycollections_detailed.name", "mycollections_detailed.picture", "mycollections.time").andExclude("_id");    //返回的字段，不返回自带的“_id”
        //将条件封装到Aggregate管道
        Aggregation aggregation = Aggregation.newAggregation(lookup, match, unwind, project);
        // 可能需要这个
        // withOptions(Aggregation.newAggregationOptions().cursorBatchSize(2000).build());
        // withOptions(Aggregation.newAggregationOptions().cursor(new Document()).build());
        List<Map> results = mongoTemplate.aggregate(aggregation, "UserDetailed", Map.class).getMappedResults();
        /**
         * 获取正确的时间time
         * PS:代码不友好，如果有其他方法最好，例如直接在Aggregation聚合中获取最好
         */
        int i = 0;
        for (Map<String, Object> map : results) {
            List<String> dates = (List<String>) map.get("time");
            map.put("time", dates.get(i));
            i++;
        }
        //results转为json格式

        return JSONObject.toJSONString(results);
    }


    /**
     * @Title findMyHistories
     * @Description:TODO 根据phone查询所有历史出行(返回所有日期的历史出行)
     * @Param [phone]
     * @return java.util.List<Myhistory>
     * @Author: 陈一心
     * @Date: 2019/9/8  22:03
     **/
    @Override
    public List<MyHistory> findMyHistories(String phone) {

        Query query = new Query(Criteria.where("phone").is(phone));
        query.fields().include("myhistories.places_time").include("myhistories.date");
        UserDetailed UserDetailed = mongoTemplate.findOne(query, UserDetailed.class);
        return UserDetailed.getMyHistories();
    }



    /**
     * @Title findMyHistoriesDetailed
     * @Description:TODO 根据phone和date联表(UserDetailed和place)查找某一天的历史出行详情列表
     * @Param [phone, date]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/8  22:03
     **/
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public String findMyHistoriesDetailed(String phone, String date) {
        // TODO Auto-generated method stub
        //拼装信息
        AggregationOperation lookup = Aggregation.lookup("place", "myhistories.places_time.place_id", "place_id", "myhistories_detailed");        //关联从表名, 主表关联字段,从表关联的字段,设置查询结果集合名
        AggregationOperation unwind = Aggregation.unwind("myhistories_detailed");         //以什么分散
        BasicDBObject object = new BasicDBObject();
        object.put("date", date);
        AggregationOperation match = Aggregation.match(Criteria.where("phone").is(phone).and("myhistories").elemMatch(Criteria.where("date").is(date)));    //主表的条件
        AggregationOperation project = Aggregation.project("myhistories_detailed.place_id", "myhistories_detailed.name", "myhistories_detailed.picture").andExclude("_id");    //返回的字段，不返回自带的“_id”
        //将条件封装到Aggregate管道
        Aggregation aggregation = Aggregation.newAggregation(lookup, match, unwind, project);
        List<Map> results = mongoTemplate.aggregate(aggregation, "UserDetailed", Map.class).getMappedResults();
        /**
         * 返回历史出行景点对应的时间time
         * PS:代码很不友好，如果有其他方法最好，例如直接在Aggregation聚合中获取最好
         */
        Query query = new Query(Criteria.where("phone").is(phone));
        query.fields().include("myhistories");
        UserDetailed UserDetailed = mongoTemplate.findOne(query, UserDetailed.class);
        int i = 0;
        for (Object myhistoryObject : UserDetailed.getMyHistories()) {
            MyHistory myhistory = (MyHistory) myhistoryObject;
            if (myhistory.getDate().equals(date)) //查找日期为date的当天出行历史记录
                System.out.println("myhistory.getPlaces_time().size():" + myhistory.getPlaces_time().size() + "----------------results:" + results.size());
            for (Map<String, Object> map : results) {
                //map.put("time", myhistory.getPlaces_time().get(i).getTime());
                i++;
            }
        }
        //results转为json格式
		return JSONObject.toJSONString(results);
    }

    /**
     * @Title findMyPlans
     * @Description:TODO 根据phone查找所有出行计划(返回所有出行计划的名称列表)
     * @Param [phone]
     * @return java.util.List<Myplan>
     * @Author: 陈一心
     * @Date: 2019/9/8  22:03
     **/
    @Override
    public List<MyPlan> findMyPlans(String phone) {

        Query query = new Query(Criteria.where("phone").is(phone));
        query.fields().include("myplans");
        query.with(new Sort(Direction.DESC, "myplans.time"));                                        //没有效果
        UserDetailed UserDetailed = mongoTemplate.findOne(query, UserDetailed.class);
        return UserDetailed.getMyPlans();
    }


    /**
     * @Title findMyPlansDetailed
     * @Description:TODO 根据phone和time联表(UserDetailed和place)查询出行计划详情列表
     * @Param [phone, time]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/8  22:04
     **/
    @Override
    public String findMyPlansDetailed(String phone, Date time) {
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
//		List<Map> results = mongoTemplate.aggregate(aggregation, "UserDetailed", Map.class).getMappedResults();
//		Gson gson = new Gson();	
//		String jsonString = gson.toJson(results);	
        /**
         * 临时采用这种方法
         */
        String plan_name = null;
        List<String> listplaces = null;
        List<Place> places = new ArrayList<Place>();
        JSONObject jsonObject = new JSONObject();
        Query query = new Query(Criteria.where("phone").is(phone));
        query.fields().include("myplans");
        UserDetailed UserDetailed = mongoTemplate.findOne(query, UserDetailed.class);
        for (Object myplanObject : UserDetailed.getMyPlans()) {
            MyPlan myplan = (MyPlan) myplanObject;
            if (myplan.getTime().equals(time)) {          //获取出行计划的制定时间(time)为time的出行计划
                plan_name = myplan.getPlan_name();
                listplaces = myplan.getPlaces_id();        //获取计划中的景点编号列表
                for (String place_id : listplaces) {
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
     * @return java.util.List<java.lang.String>
     * @Author: chenyx
     * @Date: 2019/11/8  21:37
     **/
    @Override
    public List<String> findMyCollectionsPlaceId(String phone) {

        Query query = new Query(Criteria.where("phone").is(phone));
        query.fields().include("mycollections");

        UserDetailed UserDetailed = mongoTemplate.findOne(query, UserDetailed.class);
        List<String> collectionsPlaceIds = new ArrayList<>();

        for (PlaceIdTime PlaceIdTime : UserDetailed.getMyCollections()) {
            collectionsPlaceIds.add(PlaceIdTime.getPlace_id());
        }
        return collectionsPlaceIds;
    }
}
