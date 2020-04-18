package com.gotravel.dto;

import lombok.Data;

/**
 * @Name: PlaceCommentRedisDTO
 * @Description:
 * @Author:chenyx
 * @Date: 2020/4/15 19:14
 **/
@Data
public class PlaceCommentRedisDTO {

    /**
     * 景点评论id
     */
    private String placeCommentId;

    /**
     * 点赞数
     */
    private Integer praise;


}
