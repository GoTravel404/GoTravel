package com.gotravel.enums;

import lombok.Getter;

/**
 * @Name: LabelEnum
 * @Description:TODO
 * 景点启用状态 0=禁用，1=启用，2=综合
 * 好评率 0=无序，1=升序，2=降序
 * @Author:chenyx
 * @Date: 2020/2/27 19:07
 **/
@Getter
public enum PlaceEnum {

    //景点启用状态
    BAN(0, "禁用"),
    ACTIVE(1, "启用"),
    ALL(2, "综合"),

    COLLECTION_INCREASE(0,"收藏数加一"),
    COLLECTION_DECREASE(1,"收藏数减一")


    ;



    private Integer code;

    private String message;


    PlaceEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
