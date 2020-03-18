package com.gotravel.enums;

import lombok.Getter;

/**
 * @Name: UserEnum
 * @Description:TODO
 * 用户的激活状态：默认为0，-1全部状态，0=未激活，1=激活，2=禁用
 * 用户的性别：-1=全部性别，0=女生 1=男生 3=保密
 * 用户的年龄段：-1=所有年龄段，0=15岁以下，1=15岁-30岁，2=30岁-45岁 ，3=45岁以上
 * @Author:chenyx
 * @Date: 2020/2/19 19:28
 **/
@Getter
public enum UserEnum {

    ALL_ACTIVE(-1, "全部状态"),
    NOT_ACTIVE(0, "未激活"),
    ACTIVE(1, "激活"),
    BAN(2, "禁用"),

    ALL_GENDER(-1, "全部性别"),
    FEMALE(0, "女"),
    MALE(1, "男"),
    SECRECY(2, "保密"),


    ALL_AGE(-1, new Integer[]{0, 150}),
    LESS_THAN_15(0, new Integer[]{0, 14}),
    FORM_15_TO_30(1, new Integer[]{15, 30}),
    FORM_31_TO_45(2, new Integer[]{31, 45}),
    GREATER_THAN_45(3, new Integer[]{46, 150});


    private Integer code;

    private Object message;


    UserEnum(Integer code, Object message) {
        this.code = code;
        this.message = message;
    }
}
