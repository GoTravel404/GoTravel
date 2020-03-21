package com.gotravel.enums;

import lombok.Getter;

/**
 * @Name: ResultEnum
 * @Description:TODO  返回的状态码与提示信息
 * @Author:chenyx
 * @Date: 2020/2/28 21:51
 **/
@Getter
public enum ResultEnum {

    PHONE_EXIST(100,"手机号码已被注册"),
    LOGIN_ERROR(101,"账号或密码错误"),


    SUCCESS(200,"成功"),


    PLACE_BAN(300,"景点已禁用"),


    ADD_COLLECTION_ERROR(400,"添加个人收藏景点失败"),
    DELETE_COLLECTION_ERROR(401,"删除个人收藏景点失败"),

    ADD_PLAN_ERROR(402,"添加计划失败"),
    EDIT_PLAN_ERROR(403,"编辑计划失败"),
    DELETE_PLAN_ERROR(404,"删除计划失败"),

    ADD_HISTORY_ERROR(405,"添加历史出行失败"),
    DELETE_HISTORY_PLACE_ERROR(405,"删除历史出行单个景点失败"),
    DELETE_HISTORY__ERROR(406,"删除历史出行记录失败"),

    EXCEPTION(500,"系统出错啦");


    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
