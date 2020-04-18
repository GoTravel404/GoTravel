package com.gotravel.service;

import com.gotravel.vo.PagePlaceCommentVO;

/**
 * @Name: PlaceCommentService
 * @Description: 景点评论
 * @Author:chenyx
 * @Date: 2020/4/15 18:27
 **/
public interface PlaceCommentService {

    /**
     * 景点添加好评
     * @param place_id
     * @return
     */
    int increasePlacePraise(String place_id);


    /**
     * 用户添加景点评论
     * @param phone
     * @param name
     * @param comment
     * @param place_id
     * @return
     */
    void addPlaceComment(String phone, String name,String comment, String place_id);


    /**
     * 用户点赞景点评论
     * @param phone
     * @param place_id
     * @param placeCommentId
     */
    void increasePlaceCommentPraise(String phone,String place_id,String placeCommentId);


    /**
     * 用户取消景点评论点赞
     * @param phone
     * @param place_id
     * @param placeCommentId
     */
    void decreasePlaceCommentPraise(String phone,String place_id,String placeCommentId);


    /**
     * 根据景点id分页查询景点评论
     * @param phone
     * @param place_id
     * @param page
     * @param size
     * @return
     */
    PagePlaceCommentVO selectPlaceCommentPageByPlaceId(String phone,String place_id, int page, int size);


    /**
     * 删除景点评论
     * @param place_id
     * @param placeCommentId
     */
    void deletePlaceComment(String place_id, String placeCommentId);
}
