package com.gotravel.dto;

import lombok.Data;

/**
 * @Name: UserPraisePlaceCommentRedisDTO
 * @Description:
 * @Author:chenyx
 * @Date: 2020/4/15 21:51
 **/
@Data
public class UserPraisePlaceCommentRedisDTO {

    /**
     * 点赞的用户手机号
     */
    private String phone;

    /**
     * 用户点赞的景点评论id
     */
    private String placeCommentId;

    /**
     * 用户点赞的景点评论所属的景点id
     */
    private String placeId;

    public UserPraisePlaceCommentRedisDTO() {

    }

    public UserPraisePlaceCommentRedisDTO(String phone, String placeId,String placeCommentId) {
        this.phone = phone;
        this.placeId = placeId;
        this.placeCommentId = placeCommentId;
    }
}
