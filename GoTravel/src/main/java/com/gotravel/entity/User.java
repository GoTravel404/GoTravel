
package com.gotravel.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user", indexes = {@Index(columnList = "phone")})//设置索引
public class User {

    /**
     * 唯一id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    /**
     * 手机号
     */

    private String phone;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 昵称
     */
    private String name;

    /**
     * 性别
     */
    private String gender;

    /**
     * 出生日期
     */
    private Date birthday;

    /**
     * 头像
     */
    private String image;

    /**
     * 用户的激活状态，默认为0，0=未激活，1=激活，2=禁用
     */
    private Integer status;

    @CreatedDate
    private Date createTime;

    //如果没有修改任何字段的值的话，即便走了save方法，updateTime也是不会更改的
    @LastModifiedDate
    private Date updateTime;


}
