/**
 *
 */
package com.gotravel.service;

import com.gotravel.entity.User;
import com.gotravel.vo.ResultVO;

import java.text.ParseException;

/**
 * @Description: TODO User用户基本信息表的Service接口
 * @date 2019年8月9日 下午11:58:12
 */
public interface UserService {


    /**
     * 用户注册走·旅行账号
     * @param phone
     * @param password
     * @return
     */
    ResultVO register(String phone, String password);



    /**
     * 用户登录走·旅行
     * @param phone
     * @param password
     * @return
     */
    ResultVO login(String phone, String password);


    /**
     * 用户编辑基本信息
     * @param userId
     * @param name
     * @param gender
     * @param birthday
     * @param image
     * @return
     * @throws ParseException
     */
    User editUserInfo(Integer userId, String name, String gender, String birthday, String image);

}
