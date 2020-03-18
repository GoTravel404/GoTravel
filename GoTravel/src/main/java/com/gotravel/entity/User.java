
package com.gotravel.entity;

import lombok.Data;

import java.util.Date;

/**
 * 用户信息表
 **/
@Data
public class User {

    /**
     * 唯一id
     */
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


    private Date createTime;


    private Date updateTime;

    public User(String phone, String name, String gender, Date birthday, String image) {
        this.phone = phone;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.image = image;
    }
}
