package com.gotravel.enums;

import lombok.Getter;

/**
 * @Name: DeleteLabelEnum
 * @Description:TODO （1：place_type, 2 : hobby ,3 : customization ）
 * @Author:chenyx
 * @Date: 2020/2/28 21:51
 **/
@Getter
public enum LabelEnum {

    PLACE_TYPE(1,"place_type"),
    HOBBY(2,"hobby"),
    CUSTOMIZATION(3,"customization");

    private Integer code;

    private String message;


   LabelEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
