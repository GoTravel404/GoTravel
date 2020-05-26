package com.gotravel.vo;

import lombok.Data;

import java.util.List;

/**
 * @Name: PlaceItemVO
 * @Description:
 * @Author:chenyx
 * @Date: 2020/5/26 19:26
 **/
@Data
public class PlaceItemVO {

    /**
     * 景点id 加索引
     */
    private String place_id;

    /**
     * 景点名称
     */
    private String name;

    /**
     * 景点好评率
     */
    private int praise;

    /**
     * 景点收藏数
     */
    private int collection;

    /**
     * 景点地址
     */
    private String address;

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


}
