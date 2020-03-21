/**
 *
 */
package com.gotravel.controller;

import com.gotravel.entity.User;
import com.gotravel.service.UserService;
import com.gotravel.utils.ResultVOUtil;
import com.gotravel.vo.ResultVO;
import com.gotravel.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: User用户基本信息表的Controller层
 *  @date 2019年8月9日 下午11:55:42
 */
@RequestMapping("/goTravel/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * @Title register
     * @Description: 用户注册走·旅行账号
     * @param phone
     * @param password
     * @Return: ResultVO
     * @Author: chenyx
     * @Date: 2020/3/20 13:03
     **/
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResultVO register(@RequestParam String phone, @RequestParam String password) {

        return userService.register(phone, password);

    }



    /**
     * @Title login
     * @Description:  用户登录走·旅行
     * @Param [phone, password]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  22:56
     **/
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultVO login(@RequestParam String phone, @RequestParam String password) {

        return userService.login(phone, password);
    }



    /**
     * @Title editUserInfo
     * @Description: 用户编辑基本信息O
     * @param userId
     * @param name
     * @param gender
     * @param birthday
     * @param image
     * @Return: com.gotravel.vo.ResultVO
     * @Author: chenyx
     * @Date: 2020/3/20 15:51
     **/
    @RequestMapping(value = "/editUserInfo", method = RequestMethod.POST)
    public ResultVO editUserInfo(@RequestParam Integer userId, @RequestParam String name, @RequestParam String gender, @RequestParam String birthday, @RequestParam String image) throws Exception {

        User user = userService.editUserInfo(userId, name, gender, birthday, image);

        UserVO userVO=new UserVO();

        BeanUtils.copyProperties(user,userVO);

        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("user", userVO);

        return ResultVOUtil.success(resultMap);

    }


}
