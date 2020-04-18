package com.gotravel.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @Name: PagePlaceCommentVO
 * @Description:
 * @Author:chenyx
 * @Date: 2020/4/17 20:39
 **/
@Data
public class PagePlaceCommentVO {


    /**
     * 当前页
     */
    private Integer page;


    /**
     * 当前页的评论数
     */
    private Integer total;


    /**
     * 总页数
     */
    private Integer totalPage;

    /**
     * 当页的评论
     */
    @JsonProperty("placeComments")
    private List<PlaceCommentVO> placeCommentVOList;


}
