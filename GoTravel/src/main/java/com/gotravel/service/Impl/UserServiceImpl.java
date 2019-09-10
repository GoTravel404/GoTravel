/**
 *
 */
package com.gotravel.service.Impl;


import com.gotravel.dao.nosqldao.LabelDao;
import com.gotravel.dao.nosqldao.UserDetailedDao;
import com.gotravel.dao.sqldao.UserDao;
import com.gotravel.pojo.*;
import com.gotravel.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * @Description: User用户基本信息表的Service实现层
 * @date 2019年8月10日 上午12:11:45
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private LabelDao labelDao;
    @Autowired
    private UserDetailedDao userDetailedDao;


    /**
     * @Title register
     * @Description: TODO 用户注册走·旅行账号
     * @Param [phone, password]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  22:56
     **/
    @Override
    public String register(String phone, String password) {
        // TODO Auto-generated method stub
        JSONObject jsonObject = new JSONObject();
        //判断是否已经存在该phone
        int exist = userDao.check_phone_isexist(phone);
        if (exist == 0) {    //不存在
            //注册该用户基本信息
            userDao.register(phone, password);
            //注册该用户详细信息
            User_detailed user_detailed = newuserdetailed(phone);
            userDetailedDao.adduserdetailed(user_detailed);
            jsonObject.put("msg", "注册成功");
            jsonObject.put("code", 200);    //保留，以后前端可能会用于其他功能的触发标准
        } else {    //已存在
            jsonObject.put("msg", "该手机号码已存在");
            jsonObject.put("code", 100);    //保留，以后前端可能会用于其他功能的触发标准
        }
        return jsonObject.toString();
    }


    /**
     * @Title login
     * @Description: TODO 用户登录走·旅行
     * @Param [phone, password]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  22:57
     **/
    @Override
    public String login(String phone, String password) {
        // TODO Auto-generated method stub
        JSONObject jsonObject = new JSONObject();
        //判断是否登录账号是否正确
        List<User> userlist = userDao.login(phone, password);
        if (userlist.size() > 0) {    //验证通过
            User user = userlist.get(0);
            jsonObject.put("msg", "登录成功");
            jsonObject.put("code", 200);    //保留，以后前端可能会用于其他功能的触发标准
            //用户基本信息
            jsonObject.put("phone", phone);
            jsonObject.put("name", user.getName());
            jsonObject.put("gender", user.getGender());
            jsonObject.put("age", user.getAge());
            jsonObject.put("image", user.getImage());
            //用户详细信息
//			User_detailed user_detailed=userDetailedDao.findOne(phone);
//			jsonObject.put("hobby", user_detailed.getHobby());
//			jsonObject.put("customization", user_detailed.getCustomization());		
//			jsonObject.put("mycollections",user_detailed.getMycollections());
//			jsonObject.put("myhistories", user_detailed.getMyhistories());
//			jsonObject.put("myplans", user_detailed.getMyplans());
            if (user.getLogins() == 0) {        //判断用户第几次登录，第一次的话返回官方的hobby和customization
                List<Label> labels = labelDao.findLabel();
                List<String> label_hobby = labels.get(0).getHobby();
                List<String> label_customization = labels.get(0).getCustomization();
                jsonObject.put("label_hobby", label_hobby);
                jsonObject.put("label_customization", label_customization);
                //将logins登录次数+1
                userDao.addlogins(phone);
            }
        } else {    //验证不通过
            jsonObject.put("msg", "账号或密码错误");
            jsonObject.put("code", 100);    //保留，以后前端可能会用于其他功能的触发标准
        }
        return jsonObject.toString();
    }


    /**
     * @Title edit_userinfo
     * @Description: TODO 用户编辑基本信息
     * @Param [phone, name, gender, age, image]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:02
     **/
    @Override
    public String edit_userinfo(String phone, String name, String gender, int age, String image) {
        // TODO Auto-generated method stub
        User user = new User(phone, name, gender, age, image);
        //修改用户基本信息
        userDao.editUser(user);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "修改成功");
        jsonObject.put("code", 200);
        return jsonObject.toString();
    }


    /**
     * @Title newuserdetailed
     * @Description:TODO 创建一个用户的详细信息
     * @Param [phone]
     * @return User_detailed
     * @Author: 陈一心
     * @Date: 2019/9/8  22:13
     **/
    public User_detailed newuserdetailed(String phone) {
        User_detailed user_detailed = new User_detailed();
        user_detailed.setPhone(phone);
        List<String> hobby = new ArrayList<String>();
        user_detailed.setHobby(hobby);
        List<String> customization = new ArrayList<String>();
        user_detailed.setCustomization(customization);
        /*
         * 用户的收藏景点
         **/
        List<Placeid_Time> mycollections = new ArrayList<Placeid_Time>();
        user_detailed.setMycollections(mycollections);
        /*
         * 用户的历史出行
         **/
        List<Myhistory> myhistories = new ArrayList<Myhistory>();
        user_detailed.setMyhistories(myhistories);
        /*
         * 用户的出行计划
         **/
        List<Myplan> Myplans = new ArrayList<Myplan>();
        user_detailed.setMyplans(Myplans);
        return user_detailed;
    }
}
