package com.gotravel.vo;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.List;

/**
 * @Name: PlaceVO
 * @Description:
 * @Author:chenyx
 * @Date: 2020/5/26 20:59
 **/
@Data
public class PlaceVO {

    /**
     * 景点id 加索引
     */
    @Indexed
    private String place_id;

    /**
     * 景点名称
     */
    private String name;

    /**
     * 景点介绍
     */
    private String introduce;

    /**
     * 景点图片url
     */
    private List<String> picture;

    /**
     * 景点好评率
     */
    private int praise;

    /**
     * 景点收藏数
     */
    private int collection;

    /**
     * 距离/公里
     */
    private double distance;

    /**
     * 景点地址
     */
    private String address;

    /**
     * 景点经纬度
     */
    private String longitude_latitude;

    /**
     * 景点类型
     */
    private List<String> place_type;

    /**
     * 爱好
     */
    private List<String> hobby;

    /**
     * 用户定制
     */
    private List<String> customization;

    /**
     * 启用状态 1为可用 0为禁用
     */
    private int status;




}
