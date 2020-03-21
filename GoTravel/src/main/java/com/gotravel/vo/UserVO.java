package com.gotravel.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import com.gotravel.utils.serialize.*;
import java.util.Date;

/**
 * @Name: UserVO
 * @Description:TODO User的Vo
 * @Author:chenyx
 * @Date: 2020/3/20 14:12
 **/
@Data
public class UserVO {

    /**
     * 唯一id
     */
    private Integer userId;

    /**
     * 手机号
     */
    private String phone;

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
    @JsonSerialize(using = DateToLongSerializer.class)
    private Date birthday;

    /**
     * 头像
     */
    private String image;

    /**
     * 用户的激活状态，默认为0，0=未激活，1=激活，2=禁用
     */
    private Integer status;


    @JsonSerialize(using = DateToLongSerializer.class)
    private Date createTime;


}
