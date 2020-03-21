package com.gotravel.repository.nosqldao;


import com.gotravel.entity.UserDetailed;
import com.gotravel.entity.node.MyHistory;
import com.gotravel.entity.node.MyPlan;
import com.gotravel.entity.node.PlaceIdTime;
import com.mongodb.BasicDBObject;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
     * @Description: 根据手机phone查找用户的详细信息
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
     * @Description: 注册账号添加用户详细信息
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
     * @Description: 根据phone编辑个人标签
     * @Param [phone, hobby, customization]
     * @return void
     * @Author: 陈一心
     * @Date: 2019/9/8  22:00
     **/
    @Override
    public void editLabel(String phone, List<String> hobby, List<String> customization) {

        Query query = new Query(Criteria.where("phone").is(phone));
        Update update = new Update().set("hobby", hobby).set("customization", customization);

        mongoTemplate.updateFirst(query, update, UserDetailed.class);

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
     * @Title addMyCollection
     * @Description: 根据phone添加个人收藏景点
     * 参考;https://www.cnblogs.com/bien94/p/11974659.html
     * @param phone
     * @param place_id
     * @Return: int
     * @Author: chenyx
     * @Date: 2020/3/20 17:36
     **/
    @Override
    public int addMyCollection(String phone, String place_id) {

        Query query = new Query(Criteria.where("phone").is(phone));

        //无论是否存在先删除可能已经存在相同景点了
        BasicDBObject object = new BasicDBObject();
        object.put("place_id", place_id);

        Update update_1 = new Update();
        update_1.pull("mycollections", object);

        //添加新的景点收藏
        PlaceIdTime PlaceIdTime = new PlaceIdTime(place_id, System.currentTimeMillis());

        Update update_2 = new Update();
        update_2.addToSet("mycollections", PlaceIdTime);


        //MongoDb的批量操作
        BulkOperations operations = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, UserDetailed.class);

        operations.updateOne(query, update_1);
        operations.updateOne(query, update_2);

        BulkWriteResult result = operations.execute();

        return result.getModifiedCount();

    }


    /**
     * @Title deleteMyCollection
     * @Description: 根据phone和places_id删除个人收藏景点
     * @param phone
     * @param place_id
     * @Return: int
     * @Author: chenyx
     * @Date: 2020/3/20 18:43
     **/
    @Override
    public int deleteMyCollection(String phone, String place_id) {

        Query query = new Query(Criteria.where("phone").is(phone));

        //删除操作构建一个带places_id的DB对象
        BasicDBObject object = new BasicDBObject();
        object.put("place_id", place_id);

        Update update = new Update().pull("mycollections", object);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, UserDetailed.class);

        return (int) updateResult.getModifiedCount();
    }


    /**
     * @Title findMyCollections
     * @Description: 根据phone查询所有个人收藏的景点
     * @param phone
     * @Return: com.gotravel.entity.UserDetailed
     * @Author: chenyx
     * @Date: 2020/3/20 19:06
     **/
    @Override
    public UserDetailed findMyCollections(String phone) {

        Query query = new Query(Criteria.where("phone").is(phone));
        query.fields().include("mycollections");

        return mongoTemplate.findOne(query, UserDetailed.class);

    }


    /**
     * @Title addMyPlan
     * @Description: 根据phone添加个人出行计划
     * @param phone
     * @param plan_name
     * @param places_id
     * @Return: int
     * @Author: chenyx
     * @Date: 2020/3/20 20:24
     **/
    @Override
    public int addMyPlan(String phone, String plan_name, List<String> places_id) {

        Query query = new Query(Criteria.where("phone").is(phone));

        MyPlan myplan = new MyPlan(plan_name, places_id, System.currentTimeMillis());
        Update update = new Update().push("myplans", myplan);

        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, UserDetailed.class);

        return (int) updateResult.getModifiedCount();

    }


    /**
     * @Title editMyPlan
     * @Description: 根据phone和time修改个人出行计划
     * @param phone
     * @param plan_name
     * @param places_id
     * @param time
     * @Return: int
     * @Author: chenyx
     * @Date: 2020/3/20 20:39
     **/
    @Override
    public int editMyPlan(String phone, String plan_name, List<String> places_id, long time) {

        Query query = new Query(Criteria.where("phone").is(phone).and("myplans.time").is(time));

        Update update = new Update().set("myplans.$.places_id", places_id).set("myplans.$.plan_name", plan_name);

        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, UserDetailed.class);

        return (int) updateResult.getModifiedCount();

    }


    /**
     * @Title deleteMyPlan
     * @Description: 根据phone和time删除个人出行计划
     * @param phone
     * @param time
     * @Return: int
     * @Author: chenyx
     * @Date: 2020/3/20 22:16
     **/
    @Override
    public int deleteMyPlan(String phone, long time) {

        Query query = new Query(Criteria.where("phone").is(phone));

        //删除操作构建一个带time的DB对象
        BasicDBObject object = new BasicDBObject();
        object.put("time", time);

        Update update = new Update().pull("myplans", object);

        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, UserDetailed.class);

        return (int) updateResult.getModifiedCount();
    }


    /**
     * @Title findMyPlans
     * @Description: 根据phone查找所有出行计划
     * @param phone
     * @Return: com.gotravel.entity.UserDetailed
     * @Author: chenyx
     * @Date: 2020/3/20 22:36
     **/
    @Override
    public UserDetailed findMyPlans(String phone) {

        Query query = new Query(Criteria.where("phone").is(phone));
        query.fields().include("myplans");

        return mongoTemplate.findOne(query, UserDetailed.class);

    }


    /**
     * @Title addHistory
     * @Description: 根据phone和place_id添加历史出行
     * @param phone
     * @param place_id
     * @Return: int
     * @Author: chenyx
     * @Date: 2020/3/21 19:57
     **/
    @Override
    public int addHistory(String phone, String place_id) {

        //创建当天的出现的年月日，作为一个历史出现的主键
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        PlaceIdTime places_time = new PlaceIdTime(place_id, System.currentTimeMillis());        //到达一个景点和时间

        UpdateResult updateResult = null;

        //判断是否已存在当天的年月日(date)
        Query query_1 = new Query(Criteria.where("phone").is(phone).and("myhistories.date").is(date));
        query_1.fields().include("phone");
        UserDetailed userDetailed = mongoTemplate.findOne(query_1, UserDetailed.class);


        // 判断是否已经存在当天的历史出行
        if (null == userDetailed) {    //不存在则创建当天的历史出行

            List<PlaceIdTime> PlaceIdTimes = new ArrayList<>();    //到达的多个景点和时间
            PlaceIdTimes.add(places_time);

            MyHistory myhistory = new MyHistory(PlaceIdTimes, date);        //当天的历史出行

            Query query_2 = new Query(Criteria.where("phone").is(phone));

            Update update = new Update().push("myhistories", myhistory);

            updateResult = mongoTemplate.updateFirst(query_2, update, UserDetailed.class);

        } else {    //存在则直接添加到当天的出行计划

            Update update = new Update().push("myhistories.$.places_time", places_time);

            updateResult = mongoTemplate.updateFirst(query_1, update, UserDetailed.class);
        }

        return (int) updateResult.getModifiedCount();

    }


    /**
     * @Title deleteHistoryPlace
     * @Description: 根据phone, time, date删除历史出行的单个景点
     * @param phone
     * @param time
     * @param date
     * @Return: int
     * @Author: chenyx
     * @Date: 2020/3/21 20:35
     **/
    @Override
    public int deleteHistoryPlace(String phone, long time, String date) {

        UpdateResult updateResult = null;

        Query query = new Query(Criteria.where("phone").is(phone).and("myhistories.date").is(date));

        query.fields().include("myhistories");

        UserDetailed userDetailed = mongoTemplate.findOne(query, UserDetailed.class);

        if (null == userDetailed) {
            return 0;
        }

        if (null == userDetailed.getMyHistories() || 0 == userDetailed.getMyHistories().size()) {
            return 1;
        }


        for (MyHistory myhistory : userDetailed.getMyHistories()) {

            if (myhistory.getDate().equals(date)) {

                BasicDBObject object = new BasicDBObject();

                //当某天的历史出行只剩一个景点,且该景点就是需要删除的景点，则把当天的历史出行全部删除
                if (1 == myhistory.getPlaces_time().size() && myhistory.getPlaces_time().get(0).getTime() == time) {

                    object.put("date", date);

                    Update update_1 = new Update().pull("myhistories", object);

                    updateResult = mongoTemplate.updateFirst(query, update_1, UserDetailed.class);

                } else {

                    // 删除操作构建一个带time的DB对象
                    object.put("time", time);

                    Update update_2 = new Update().pull("myhistories.$.places_time", object);

                    updateResult = mongoTemplate.updateFirst(query, update_2, UserDetailed.class);

                }

                return (int) updateResult.getModifiedCount();
            }

        }

        return 0;

    }


    /**
     * @Title deleteHistory
     * @Description: 根据phone和date删除历史出行记录
     * @param phone
     * @param date
     * @Return: int
     * @Author: chenyx
     * @Date: 2020/3/21 21:30
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
     * @Title findMyHistories
     * @Description: 根据phone查询所有历史出行(返回所有日期的历史出行)
     * @param phone
     * @Return: com.gotravel.entity.UserDetailed
     * @Author: chenyx
     * @Date: 2020/3/21 22:01
     **/
    @Override
    public UserDetailed findMyHistories(String phone) {

        Query query = new Query(Criteria.where("phone").is(phone));

        query.fields().include("myhistories");

        return mongoTemplate.findOne(query, UserDetailed.class);

    }



    /**
     * @Title findMyCollectionsPlaceId
     * @Description:  根据phone查找用户收藏的所有景点，组成List<Integer>
     * @param phone 手机号
     * @return java.util.List<java.lang.String>
     * @Author: chenyx
     * @Date: 2019/11/8  21:37
     **/
    @Override
    public List<String> findMyCollectionsPlaceId(String phone) {

        Query query = new Query(Criteria.where("phone").is(phone));
        query.fields().include("mycollections");

        UserDetailed userDetailed = mongoTemplate.findOne(query, UserDetailed.class);

        if (null == userDetailed)
            return null;

        List<String> collectionsPlaceIds = new ArrayList<>();

        for (PlaceIdTime PlaceIdTime : userDetailed.getMyCollections()) {
            collectionsPlaceIds.add(PlaceIdTime.getPlace_id());
        }

        return collectionsPlaceIds;
    }

}
