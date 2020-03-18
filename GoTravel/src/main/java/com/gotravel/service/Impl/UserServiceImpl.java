/**
 *
 */
package com.gotravel.service.Impl;


import com.alibaba.fastjson.JSONObject;
import com.gotravel.dao.nosqldao.LabelDao;
import com.gotravel.dao.nosqldao.UserDetailedDao;
import com.gotravel.dao.sqldao.UserDao;
import com.gotravel.entity.Label;
import com.gotravel.entity.User;
import com.gotravel.entity.UserDetailed;
import com.gotravel.entity.node.MyHistory;
import com.gotravel.entity.node.MyPlan;
import com.gotravel.entity.node.PlaceIdTime;
import com.gotravel.enums.UserEnum;
import com.gotravel.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @Description: User用户基本信息表的Service实现层
 * @date 2019年8月10日 上午12:11:45
 */
@Slf4j
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

        JSONObject jsonObject = new JSONObject();

        //判断是否已经存在该phone
        int exist = userDao.check_phone_is_exist(phone);

        if (exist == 0) {    //不存在

            //注册该用户基本信息
            userDao.register(phone, password);

            //注册该用户详细信息
            UserDetailed user_detailed = newUserDetailed(phone);

            userDetailedDao.addUserDetailed(user_detailed);

            jsonObject.put("msg", "注册成功");
            jsonObject.put("code", 200);

        } else {    //已存在

            jsonObject.put("msg", "该手机号码已存在");
            jsonObject.put("code", 100);

        }

        return jsonObject.toJSONString();
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
            jsonObject.put("birthday", user.getBirthday());
            jsonObject.put("image", user.getImage());

            //用户详细信息
//			UserDetailed user_detailed=userDetailedDao.findOne(phone);
//			jsonObject.put("hobby", user_detailed.getHobby());
//			jsonObject.put("customization", user_detailed.getCustomization());		
//			jsonObject.put("mycollections",user_detailed.getMycollections());
//			jsonObject.put("myhistories", user_detailed.getMyhistories());
//			jsonObject.put("myplans", user_detailed.getMyPlans());

            if (user.getStatus().equals(UserEnum.NOT_ACTIVE.getCode())) {        //判断用户第几次登录，第一次的话返回官方的hobby和customization
                List<Label> labels = labelDao.findLabel();

                List<String> label_hobby = labels.get(0).getHobby();
                List<String> label_customization = labels.get(0).getCustomization();

                jsonObject.put("label_hobby", label_hobby);
                jsonObject.put("label_customization", label_customization);

                //将status状态变成激活状态
                userDao.editStatusToActive(phone);

            }

        } else {    //验证不通过

            jsonObject.put("msg", "账号或密码错误");
            jsonObject.put("code", 100);    //保留，以后前端可能会用于其他功能的触发标准

        }
        return jsonObject.toJSONString();
    }


    /**
     * @Title editUserInfo
     * @Description: TODO 用户编辑基本信息
     * @Param [phone, name, gender, age, image]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/9  23:02
     **/
    @Override
    public String editUserInfo(String phone, String name, String gender, String birthday, String image) {

        Date date = null;

        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");//日期格式
            date = format.parse(birthday);
        } catch (Exception e) {
            log.info("【用户编辑基本信息】：日期转换失败 ,date={}", date);
        }

        User user = new User(phone, name, gender, date, image);

        //修改用户基本信息
        userDao.editUser(user);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "修改成功");
        jsonObject.put("code", 200);

        return jsonObject.toJSONString();
    }


    /**
     * @Title newuserdetailed
     * @Description:TODO 创建一个用户的详细信息
     * @Param [phone]
     * @return UserDetailed
     * @Author: 陈一心
     * @Date: 2019/9/8  22:13
     **/
    public UserDetailed newUserDetailed(String phone) {

        UserDetailed user_detailed = new UserDetailed();
        user_detailed.setPhone(phone);

        List<String> hobby = new ArrayList<String>();
        user_detailed.setHobby(hobby);

        List<String> customization = new ArrayList<String>();
        user_detailed.setCustomization(customization);

        /*
         * 用户的收藏景点
         **/
        List<PlaceIdTime> mycollections = new ArrayList<PlaceIdTime>();
        user_detailed.setMyCollections(mycollections);

        /*
         * 用户的历史出行
         **/
        List<MyHistory> myhistories = new ArrayList<MyHistory>();
        user_detailed.setMyHistories(myhistories);

        /*
         * 用户的出行计划
         **/
        List<MyPlan> MyPlans = new ArrayList<MyPlan>();
        user_detailed.setMyPlans(MyPlans);
        return user_detailed;
    }


}
