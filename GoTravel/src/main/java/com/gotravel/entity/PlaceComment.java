package com.gotravel.entity;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Name: PlaceComment
 * @Description: 景点评论表
 * @Author:chenyx
 * @Date: 2020/4/15 17:50
 **/
@Data
@Entity
@Table(name = "place_comment")
public class PlaceComment {

    /**
     * 景点评论id
     */
    @Id
    private String placeCommentId;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 评论的景点id
     */
    private String placeId;

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
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

}
