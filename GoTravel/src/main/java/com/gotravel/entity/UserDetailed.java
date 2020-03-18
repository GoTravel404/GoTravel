package com.gotravel.entity;


import com.gotravel.entity.node.MyHistory;
import com.gotravel.entity.node.MyPlan;
import com.gotravel.entity.node.PlaceIdTime;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * 用户的详细信息表(user_detailed)
 * 参考：https://www.cnblogs.com/kxm87/p/9633456.html
 **/
@Data
@Document(collection = "user_detailed")
public class UserDetailed {

    /**
     * 手机号
     */
    @Indexed(unique = true)
    private String phone;

    /**
     * 爱好
     */
    private List<String> hobby;

    /**
     * 用户定制
     */
    private List<String> customization;

    /**
     * 用户收藏的景点
     */
    @Field("mycollections")
    private List<PlaceIdTime> myCollections;

    /**
     * 用户的出行记录
     */
    @Field("myhistories")
    private List<MyHistory> myHistories;


    /**
     * 用户出行计划
     */
    @Field("myplans")
    private List<MyPlan> myPlans;


}
