package com.gotravel.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gotravel.utils.serialize.DateToLongSerializer;
import lombok.Data;

import java.util.Date;

/**
 * @Name: PlaceCommentVO
 * @Description:
 * @Author:chenyx
 * @Date: 2020/4/17 19:19
 **/
@Data
public class PlaceCommentVO {

    /**
     * 景点评论id
     */
    private String placeCommentId;

    /**
     * 手机号
     */
    @JsonProperty("senderPhone")
    private String phone;

    /**
     * 用户昵称
     */
    @JsonProperty("senderName")
    private String name;

    /**
     * 用户头像
     */
    @JsonProperty("senderIcon")
    private String icon;

    /**
     * 评论内容
     */
    private String placeComment;

    /**
     * 点赞数
     */
    private Integer praise;

    /**
     * 创建时间
     */
    @JsonSerialize(using = DateToLongSerializer.class)
    private Date createTime;

    /**
     * 是否曾为该评论点赞过,0否，1是
     */
    private Integer isPraise;


}
