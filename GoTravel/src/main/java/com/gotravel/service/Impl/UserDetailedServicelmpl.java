package com.gotravel.service.Impl;

import com.gotravel.entity.Place;
import com.gotravel.entity.UserDetailed;
import com.gotravel.entity.node.MyHistory;
import com.gotravel.entity.node.MyPlan;
import com.gotravel.entity.node.PlaceIdTime;
import com.gotravel.enums.PlaceEnum;
import com.gotravel.repository.nosqldao.UserDetailedDao;
import com.gotravel.repository.redis.PlaceRedis;
import com.gotravel.service.UserDetailedService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: TODO User_Detailed用户信息详细表的service的实现类
 * @date 2019年8月4日 下午8:36:35
 */
@Service("userDetailedService")
@Transactional
public class UserDetailedServicelmpl implements UserDetailedService {

    @Autowired
    private UserDetailedDao userDetailedDao;

    @Autowired
    private PlaceRedis placeRedis;


    /**
     * @Title chooseLabel
     * @Description: 用户选择个人标签
     * @param map phone,hobby,customization
     * @Return: void
     * @Author: chenyx
     * @Date: 2020/3/20 15:59
     **/
    @SuppressWarnings("unchecked")
    @Override
    public void chooseLabel(Map<String, Object> map) {

        List<String> hobby = (List<String>) map.get("hobby");
        List<String> customization = (List<String>) map.get("customization");
        String phone = (String) map.get("phone");

        //编辑数据库
        userDetailedDao.editLabel(phone, hobby, customization);

    }


    /**
     * @Title addMyCollection
     * @Description: TODO 用户添加个人收藏景点
     * @param phone
     * @param place_id
     * @Return: int
     * @Author: chenyx
     * @Date: 2020/3/20 17:48
     **/
    @Override
    public int addMyCollection(String phone, String place_id) {

        //收藏添加到数据库
        return userDetailedDao.addMyCollection(phone, place_id);

    }


    /**
     * @Title deleteMyCollection
     * @Description: TODO 用户删除个人收藏景点
     * @param phone
     * @param place_id
     * @Return: int
     * @Author: chenyx
     * @Date: 2020/3/20 18:41
     **/
    @Override
    public int deleteMyCollection(String phone, String place_id) {

        //数据库删除收藏
        return userDetailedDao.deleteMyCollection(phone, place_id);

    }


    /**
     * @Title findMyCollections
     * @Description: 用户查找所有个人收藏的景点
     * @param phone
     * @Return: java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @Author: chenyx
     * @Date: 2020/3/20 19:24
     **/
    @Override
    public List<Map<String, Object>> findMyCollections(String phone) {

        //根据phone查询所有个人收藏的景点
        UserDetailed userDetailed = userDetailedDao.findMyCollections(phone);

        if (null == userDetailed) {
            return null;
        }

        List<PlaceIdTime> placeIdTimeList = userDetailed.getMyCollections();

        if (null == placeIdTimeList || 0 == placeIdTimeList.size()) {
            return null;
        }

        //转成placeId的List
        List<String> placeIdList = placeIdTimeList.stream().map(PlaceIdTime::getPlace_id).collect(Collectors.toList());

        //转成placeId，time的Map
        Map<String, Long> place_IdAndTimeMap = placeIdTimeList.stream().collect(Collectors.toMap(PlaceIdTime::getPlace_id, PlaceIdTime::getTime));


        //redis查询
        List<Place> placeList = placeRedis.getPlaceListByPipeline(placeIdList);

        List<Map<String, Object>> resultList = new ArrayList<>();

        if (!CollectionUtils.isEmpty(placeIdList)) {

            for (Place place : placeList) {

                if (null != place && PlaceEnum.ACTIVE.getCode() == place.getStatus()) {

                    Map<String, Object> map = new HashMap<>();

                    map.put("place_id", place.getPlace_id());
                    map.put("name", place.getName());

                    String picture = "";
                    if (null != place.getPicture() && 0 != place.getPicture().size()) {
                        picture = place.getPicture().get(0);
                    }
                    map.put("picture", picture);

                    map.put("time", place_IdAndTimeMap.get(place.getPlace_id()));

                    resultList.add(map);

                }

            }

        }

        //倒序
        Collections.reverse(resultList);

        return resultList;

    }


    /**
     * @Title addMyPlan
     * @Description: 用户添加个人出行计划
     * @param map     phone,plan_name,places_id(List<Integer>),postscript
     * @Return: java.lang.String
     * @Author: chenyx
     * @Date: 2020/3/20 20:14
     **/
    @SuppressWarnings("unchecked")
    @Override
    public int addMyPlan(Map<String, Object> map) {

        String phone = (String) map.get("phone");
        String plan_name = (String) map.get("plan_name");
        List<String> places_id = (List<String>) map.get("places_id");
        String postscript = (String) map.get("postscript");

        if (StringUtils.isBlank(phone) || StringUtils.isBlank(plan_name) || places_id.isEmpty()) {
            return 0;
        }

        if (null == postscript)
            postscript = "";


        //数据库添加个人出行计划
        return userDetailedDao.addMyPlan(phone, plan_name, places_id, postscript);

    }


    /**
     * @Title editmyplans
     * @Description:TODO 用户编辑个人出行计划
     * @Param [map] phone,plan_name,place_ids(List<Integer>),time,postscript
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/8  22:19
     **/
    @SuppressWarnings("unchecked")
    @Override
    public int editMyPlan(Map<String, Object> map) {

        String phone = (String) map.get("phone");
        String plan_name = (String) map.get("plan_name");
        List<String> places_id = (List<String>) map.get("places_id");
        String postscript = (String) map.get("postscript");
        Long time = (Long) map.get("time");

        if (StringUtils.isBlank(phone) || StringUtils.isBlank(plan_name) || time == null) {
            return 0;
        }

        if (null == postscript)
            postscript = "";


        //数据库修改个人出行计划
        int a= userDetailedDao.editMyPlan(phone, plan_name, places_id, time, postscript);

        return a;
    }


    /**
     * @Title deleteMyPlan
     * @Description: 用户删除个人出行计划
     * @param phone
     * @param time
     * @Return: int
     * @Author: chenyx
     * @Date: 2020/3/20 22:14
     **/
    @Override
    public int deleteMyPlan(String phone, long time) {

        //数据库删除个人出行计划
        return userDetailedDao.deleteMyPlan(phone, time);

    }


    /**
     * @Title findMyPlans
     * @Description: 用户查找所有出行计划(返回所有出行计划的名称列表)
     * @param phone
     * @Return: java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @Author: chenyx
     * @Date: 2020/3/20 22:32
     **/
    @Override
    public List<Map<String, Object>> findMyPlans(String phone) {

        //数据库查询所有出行计划
        UserDetailed userDetailed = userDetailedDao.findMyPlans(phone);

        if (null == userDetailed) {
            return null;
        }

        List<MyPlan> myPlans = userDetailed.getMyPlans();

        if (null == myPlans) {
            return null;
        }

     //按时间排序，时间最近的靠前
     // Collections.sort(myPlans);

        //封装返回出行计划名称(plan_name) , 计划时间(time)的json数组类型数据

        List<Map<String, Object>> resultList = new ArrayList<>();

        for (MyPlan myplan : myPlans) {

            Map<String, Object> map = new HashMap<>();

            map.put("plan_name", myplan.getPlan_name());
            map.put("time", myplan.getTime());
            map.put("postscript", myplan.getPostscript());

            resultList.add(map);
        }

        Collections.reverse(resultList);

        return resultList;

    }


    /**
     * @Title findMyPlanDetailed
     * @Description: 用户根据计划制定的时间(time)查询出行计划详情列表
     * @param phone
     * @param time
     * @Return: java.util.Map<java.lang.String, java.lang.Object>
     * @Author: chenyx
     * @Date: 2020/3/20 23:20
     **/
    @Override
    public Map<String, Object> findMyPlanDetailed(String phone, long time) {

        //数据库查询所有出行计划
        UserDetailed userDetailed = userDetailedDao.findMyPlans(phone);

        if (null == userDetailed) {
            return null;
        }

        List<MyPlan> myPlans = userDetailed.getMyPlans();

        if (null == myPlans) {
            return null;
        }

        //是否匹配time
        boolean match = false;

        Map<String, Object> resultMap = new HashMap<>();

        //匹配到对应time的计划
        for (MyPlan myplan : myPlans) {

            if (time == myplan.getTime()) {

                //匹配到time
                match = true;

                //计划名称
                resultMap.put("plan_name", myplan.getPlan_name());

                //计划备注
                resultMap.put("postscript", myplan.getPostscript());

                //计划时间
                resultMap.put("time", myplan.getTime());

                List<String> placeIds = myplan.getPlaces_id();

                if (null == placeIds) {
                    return null;
                }

                //查询redis
                List<Place> placeList = placeRedis.getPlaceListByPipeline(placeIds);

                List<Map<String, Object>> resultList = new ArrayList<>();

                if (!CollectionUtils.isEmpty(placeIds)) {

                    for (Place place : placeList) {

                        if (null != place) {

                            Map<String, Object> map = new HashMap<>();

                            map.put("place_id", place.getPlace_id());
                            map.put("name", place.getName());

                            String longitude_latitude = "";

                            if (null != place.getLongitude_latitude() && !place.getLongitude_latitude().equals("")) {
                                longitude_latitude = place.getLongitude_latitude();
                            }
                            map.put("longitude_latitude", longitude_latitude);

                            resultList.add(map);

                        }

                    }

                }

                //计划的景点
                resultMap.put("places", resultList);

            }

        }

        //没有匹配到time
        if (!match) {
            return null;
        }

        return resultMap;

    }


    /**
     * @Title searchMyPlanByPhoneAndPlanName
     * @Description: 用户根据手机号+出行计划的名称查询计划列表
     * @param phone
     * @param plan_name
     * @Return: java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @Author: chenyx
     * @Date: 2020/4/8 17:36
     **/
    @Override
    public List<Map<String, Object>> searchMyPlanByPhoneAndPlanName(String phone, String plan_name) {

        //数据库查询所有出行计划
        UserDetailed userDetailed = userDetailedDao.findMyPlans(phone);

        if (null == userDetailed) {
            return null;
        }

        List<MyPlan> myPlans = userDetailed.getMyPlans();

        if (null == myPlans) {
            return null;
        }

        //按时间排序，时间最近的靠前
        Collections.sort(myPlans);

        //封装返回出行计划名称(plan_name) , 计划时间(time)的json数组类型数据

        List<Map<String, Object>> resultList = new ArrayList<>();

        for (MyPlan myplan : myPlans) {

            //出行计划的名称模糊匹配
            if (myplan.getPlan_name().contains(plan_name)) {

                Map<String, Object> map = new HashMap<>();

                map.put("plan_name", myplan.getPlan_name());
                map.put("time", myplan.getTime());
                map.put("postscript", myplan.getPostscript());

                resultList.add(map);
            }

        }

        if (resultList.isEmpty()) {
            return null;
        }

        return resultList;

    }


    /**
     * @Title addHistory
     * @Description: 用户到达景点后将景点添加到历史出行
     * @param phone
     * @param place_id
     * @Return: int
     * @Author: chenyx
     * @Date: 2020/3/21 19:51
     **/
    @Override
    public int addHistory(String phone, String place_id) {

        //数据库添加个人历史出行
        return userDetailedDao.addHistory(phone, place_id);

    }


    /**
     * @Title deleteHistoryPlace
     * @Description: 用户删除历史出行的单个景点
     * @param phone
     * @param time
     * @param date
     * @Return: int
     * @Author: chenyx
     * @Date: 2020/3/21 20:34
     **/
    @Override
    public int deleteHistoryPlace(String phone, long time, String date) {

        //数据库删除历史出行的单个景点
        return userDetailedDao.deleteHistoryPlace(phone, time, date);

    }


    /**
     * @Title deleteHistory
     * @Description: 用户删除历史出行记录
     * @param phone
     * @param date
     * @Return: int
     * @Author: chenyx
     * @Date: 2020/3/21 21:29
     **/
    @Override
    public int deleteHistory(String phone, String date) {

        //数据库删除历史出行记录
        return userDetailedDao.deleteHistory(phone, date);

    }


    /**
     * @Title findMyHistories
     * @Description: 用户查找所有历史出行(返回所有日期的历史出行列表)
     * @param phone
     * @Return: java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @Author: chenyx
     * @Date: 2020/3/21 21:57
     **/
    @Override
    public List<Map<String, Object>> findMyHistories(String phone) {

        //数据库查询所有历史出行
        UserDetailed userDetailed = userDetailedDao.findMyHistories(phone);


        if (null == userDetailed) {
            return null;
        }

        if (null == userDetailed.getMyHistories() || 0 == userDetailed.getMyHistories().size()) {
            return null;
        }


        List<Map<String, Object>> resultList = new ArrayList<>();

        //封装返回出行日期(date) , 浏览景点数量(place_number)的json数组类型数据
        for (MyHistory myhistory : userDetailed.getMyHistories()) {

            Map<String, Object> map = new HashMap<>();

            map.put("date", myhistory.getDate());
            map.put("place_number", myhistory.getPlaces_time().size());

            resultList.add(map);

        }

        //倒序
        Collections.reverse(resultList);

        return resultList;

    }


    /**
     * @Title findMyHistoriesDetailed
     * @Description: 用户根据日期查找某一天的历史出行详情列表
     * @param phone
     * @param date
     * @Return: java.util.Map<java.lang.String, java.lang.Object>
     * @Author: chenyx
     * @Date: 2020/3/21 22:39
     **/
    @Override
    public Map<String, Object> findMyHistoriesDetailed(String phone, String date) {

        //数据库返回历史出行详情
        UserDetailed userDetailed = userDetailedDao.findMyHistories(phone);

        if (null == userDetailed) {
            return null;
        }

        if (null == userDetailed.getMyHistories() || 0 == userDetailed.getMyHistories().size()) {
            return null;
        }

        Map<String, Object> resultMap = new HashMap<>();

        //是否匹配date
        boolean match = false;


        for (MyHistory myhistory : userDetailed.getMyHistories()) {

            if (myhistory.getDate().equals(date)) {

                //匹配到date
                match = true;

                //历史出行的日期
                resultMap.put("date", myhistory.getDate());

                //获取当天的所有的出行景点id与时间戳
                List<PlaceIdTime> placeIdTimeList = myhistory.getPlaces_time();

                //获取的所有的景点id
                List<String> placeIdList = placeIdTimeList.stream().map(PlaceIdTime::getPlace_id).collect(Collectors.toList());

                if (0 == placeIdList.size()) {
                    return null;
                }

                //redis查询景点
                List<Place> placeList = placeRedis.getPlaceListByPipeline(placeIdList);

                //转成place_id，Place的Map,存在重复的key时，选择最后的key
                Map<String, Place> place_IdAndPlaceMap = placeList.stream().collect(Collectors.toMap(Place::getPlace_id, u -> u, (u1, u2) -> u2));

                if (0 == placeIdList.size()) {
                    return null;
                }


                //到达的景点List
                List<Map<String, Object>> resultList = new ArrayList<>();

                for (PlaceIdTime placeIdTime : placeIdTimeList) {

                    if (null != placeIdTime) {

                        Place place = place_IdAndPlaceMap.get(placeIdTime.getPlace_id());

                        if (null != place) {

                            //每个景点的信息
                            Map<String, Object> map = new HashMap<>();

                            map.put("place_id", placeIdTime.getPlace_id());

                            map.put("name", place.getName());
                            map.put("praise", place.getPraise());

                            String picture = "";
                            if (null != place.getPicture() && 0 != place.getPicture().size()) {
                                picture = place.getPicture().get(0);
                            }
                            map.put("picture", picture);

                            //景点达到的时间
                            map.put("time", placeIdTime.getTime());

                            resultList.add(map);
                        }
                    }

                }

                //到达的景点
                resultMap.put("places", resultList);

            }

        }

        //没有匹配到date
        if (!match) {
            return null;
        }

        return resultMap;

    }


}
