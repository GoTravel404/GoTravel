/**
 *
 */
package com.gotravel.controller;

import com.gotravel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: User用户基本信息表的Controller层
 *  @date 2019年8月9日 下午11:55:42
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * @Title register
     * @Description: TODO 用户注册走·旅行账号
     * @Param [phone, password]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  22:55
     **/
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestParam String phone, @RequestParam String password) {
        return userService.register(phone, password);
    }


    /**
     * @Title login
     * @Description: TODO 用户登录走·旅行
     * @Param [phone, password]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  22:56
     **/
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam String phone, @RequestParam String password) {
        return userService.login(phone, password);
    }


    /**
     * @Title edit_userinfo
     * @Description: TODO 用户编辑基本信息
     * @Param [phone, name, gender, age, image]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:02
     **/
    @RequestMapping(value = "/editUserInfo", method = RequestMethod.POST)
    public String editUserInfo(@RequestParam String phone, @RequestParam String name, @RequestParam String gender, @RequestParam String birthday, @RequestParam String image) throws Exception {
        return userService.editUserInfo(phone, name, gender, birthday, image);
    }
}
