/**
 *
 */
package com.gotravel.service;

import java.text.ParseException;

/**
 * @Description: TODO User用户基本信息表的Service接口
 * @date 2019年8月9日 下午11:58:12
 */
public interface UserService {


    /**
     * @Title register
     * @Description: TODO 用户注册走·旅行账号
     * @Param [phone, password]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  22:53
     **/
    String register(String phone, String password);


    /**
     * @Title login
     * @Description: TODO 用户登录走·旅行
     * @Param [phone, password]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  22:52
     **/
    String login(String phone, String password);


    /**
     * @Title editUserInfo
     * @Description: TODO 用户编辑基本信息
     * @Param [phone, name, gender, age, image]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:02
     **/
    String editUserInfo(String phone, String name, String gender, String birthday, String image) throws ParseException;

}
