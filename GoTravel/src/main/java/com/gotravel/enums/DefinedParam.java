package com.gotravel.enums;

import org.springframework.beans.factory.annotation.Value;

/**
 * @Name: DefinedParam
 * @Description:TODO 定义application-param.properties的各种参数
 * @Author:chenyx
 * @Date: 2020/3/7 18:08
 **/
public class DefinedParam {

    //景点默认好评
    @Value("${PLACE.PRAISE}")
    public static int PLACE_PRAISE = 1000;

    //分页的每页记录数
    @Value("${PAGE.SIZE}")
    public static int PAGE_SIZE = 10;

    //分析用户收藏的最多的景点个数
    @Value("${COLLECTION.PLACE.SIZE}")
    public static int COLLECTION_PLACE_SIZE = 30;

    //分析用户踏足的最多的景点个数
    @Value("${HISTORY.PLACE.SIZE}")
    public static int HISTORY_PLACE_SIZE = 30;

    //分析用户计划的最多的景点个数
    @Value("${PLAN.PLACE.SIZE}")
    public static int PLAN_PLACE_SIZE = 30;

    //分析最多好评数的景点个数
    @Value("${PLACE.PRAISE.SIZ}")
    public static int PLACE_PRAISE_SIZE = 30;

    //统计分析最多的景点标签个数
    @Value("${ANALYSE.LABEL.SIZE}")
    public static int ANALYSE_LABEL_SIZE = 30;

    //统计分析最多的用户个数
    @Value("${ANALYSE.USER.SIZE}")
    public static int ANALYSE_USER_SIZE = 1000;

    //Label标签表的唯一ID
    @Value("${LABEL.ID}")
    public static int LABEL_ID = 100;

}
