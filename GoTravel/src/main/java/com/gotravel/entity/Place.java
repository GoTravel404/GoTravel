package com.gotravel.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 * 景点表(place)
 * **/
@Data
@Document(collection = "place")
public class Place implements Serializable ,Comparable<Place> {


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

    @Override
    public int compareTo(Place o) {
        //首次执行，o.praise代表List里第一个元素，this.praise是List里第二个元素
        return Integer.compare(o.praise, this.praise);

    }

}