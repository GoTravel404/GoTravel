package com.gotravel.enums;

import lombok.Getter;

/**
 * @Name: PlaceCommentEnum
 * @Description:
 * @Author:chenyx
 * @Date: 2020/4/17 19:30
 **/
@Getter
public enum PlaceCommentEnum {


    NO_PRAISE(0, "不曾为该评论点赞过"),
    PRAISE(1, "曾为该评论点赞过"),

    ;

    private Integer code;

    private String message;


    PlaceCommentEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


}
