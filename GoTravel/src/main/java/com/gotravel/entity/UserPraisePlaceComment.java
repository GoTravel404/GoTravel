package com.gotravel.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Name: PlaceComment
 * @Description: 用户点赞景点评论表
 * @Author:chenyx
 * @Date: 2020/4/15 17:50
 **/
@Data
@Entity
@Table(name = "user_praise_place_comment")
public class UserPraisePlaceComment {


  /**
   * id
   */
  @Id
  private String id;

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

  /**
   * 创建时间
   */
  private Date createTime;

  /**
   * 修改时间
   */
  private Date updateTime;




}
