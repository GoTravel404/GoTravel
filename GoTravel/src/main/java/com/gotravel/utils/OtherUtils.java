package com.gotravel.utils;

import com.gotravel.entity.UserDetailed;
import com.gotravel.entity.node.MyHistory;
import com.gotravel.entity.node.MyPlan;
import com.gotravel.entity.node.PlaceIdTime;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.ArrayList;
import java.util.List;

/**
 * @Name: OtherUtils
 * @Description:TODO
 * @Author:chenyx
 * @Date: 2020/3/19 21:09
 **/
public class OtherUtils {


    /**
     * @Title getCriteriaOfOrOperatorForLabel
     * @Description: 根据返回适合标签的OrOperator
     * @param place_type
     * @param hobby
     * @param customization
     * @Return: org.springframework.data.mongodb.core.query.Criteria
     * @Author: chenyx
     * @Date: 2020/3/19 17:31
     **/
    public static Criteria getCriteriaOfOrOperatorForLabel(List<String> place_type, List<String> hobby, List<String> customization) {

        Criteria criteriaPlace_type = null, criteriaHobby = null, criteriaCustomization = null;

        Criteria criteria = new Criteria();

        if (null != place_type && 0 != place_type.size())
            criteriaPlace_type = Criteria.where("place_type").in(place_type);

        if (null != hobby && 0 != hobby.size())
            criteriaHobby = Criteria.where("hobby").in(hobby);

        if (null != customization && 0 != customization.size())
            criteriaCustomization = Criteria.where("customization").in(customization);


        if (null != criteriaPlace_type && null != criteriaHobby && null != criteriaCustomization) {
            criteria.orOperator(criteriaPlace_type, criteriaHobby, criteriaCustomization);

        } else if (null != criteriaPlace_type && null != criteriaHobby) {
            criteria.orOperator(criteriaPlace_type, criteriaHobby);

        } else if (null != criteriaHobby && null != criteriaCustomization) {
            criteria.orOperator(criteriaHobby, criteriaCustomization);

        } else if (null != criteriaPlace_type && null != criteriaCustomization) {
            criteria.orOperator(criteriaPlace_type, criteriaCustomization);

        } else if (null != criteriaPlace_type) {
            criteria.orOperator(criteriaPlace_type);

        } else if (null != criteriaHobby) {
            criteria.orOperator(criteriaHobby);

        } else if (null != criteriaCustomization) {
            criteria.orOperator(criteriaCustomization);
        }

        return criteria;

    }


    /**
     * @Title newUserDetailed
     * @Description: 创建一个用户的详细信息
     * @param phone
     * @Return: com.gotravel.entity.UserDetailed
     * @Author: chenyx
     * @Date: 2020/3/20 12:43
     **/
    public static UserDetailed newUserDetailed(String phone) {

        UserDetailed user_detailed = new UserDetailed();
        user_detailed.setPhone(phone);

        List<String> hobby = new ArrayList<String>();
        user_detailed.setHobby(hobby);

        List<String> customization = new ArrayList<String>();
        user_detailed.setCustomization(customization);

        /*
         * 用户的收藏景点
         **/
        List<PlaceIdTime> mycollections = new ArrayList<PlaceIdTime>();
        user_detailed.setMyCollections(mycollections);

        /*
         * 用户的历史出行
         **/
        List<MyHistory> myhistories = new ArrayList<MyHistory>();
        user_detailed.setMyHistories(myhistories);

        /*
         * 用户的出行计划
         **/
        List<MyPlan> MyPlans = new ArrayList<MyPlan>();
        user_detailed.setMyPlans(MyPlans);
        return user_detailed;
    }


    /**
     * @Title getHistoryPlaceIdListByUserDetailed
     * @Description: 解析用户详细信息结构，获取用户的踏足(历史出行记录)的景点List<id>
     * @param userDetailed
     * @Return: java.util.List<java.lang.String>
     * @Author: chenyx
     * @Date: 2020/3/6 20:35
     **/
    public static List<String> getHistoryPlaceIdListByUserDetailed(UserDetailed userDetailed) {


        List<String> historyPlaceIdList = new ArrayList<>();

        //解析结构
        for (MyHistory myHistoryList : userDetailed.getMyHistories()) {

            for (PlaceIdTime placeIdTime : myHistoryList.getPlaces_time()) {

                historyPlaceIdList.add(placeIdTime.getPlace_id());
            }
        }


        return historyPlaceIdList;

    }


    /**
     * @Title getCollectionPlaceIdListByUserDetailed
     * @Description: 解析用户详细信息结构，获取用户的收藏景点List<id>
     * @param userDetailed
     * @Return: java.util.List<java.lang.String>
     * @Author: chenyx
     * @Date: 2020/5/27 21:47
     **/
    public static List<String> getCollectionPlaceIdListByUserDetailed(UserDetailed userDetailed) {


        List<String> collectionPlaceIdList = new ArrayList<>();

        //解析结构
        for (PlaceIdTime placeIdTime : userDetailed.getMyCollections()) {

            collectionPlaceIdList.add(placeIdTime.getPlace_id());

        }


        return collectionPlaceIdList;

    }


}
