/**
 *
 */
package com.gotravel.service.Impl;


import com.gotravel.entity.Label;
import com.gotravel.entity.User;
import com.gotravel.entity.UserDetailed;
import com.gotravel.enums.ResultEnum;
import com.gotravel.enums.UserEnum;
import com.gotravel.repository.UserRepository;
import com.gotravel.repository.nosqldao.UserDetailedDao;
import com.gotravel.repository.redis.LabelRedis;
import com.gotravel.service.UserService;
import com.gotravel.utils.OtherUtils;
import com.gotravel.utils.ResultVOUtil;
import com.gotravel.vo.ResultVO;
import com.gotravel.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Description: User用户基本信息表的Service实现层
 * @date 2019年8月10日 上午12:11:45
 */
@Slf4j
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private LabelRedis labelRedis;

    @Autowired
    private UserDetailedDao userDetailedDao;

    @Autowired
    private UserRepository userRepository;


    /**
     * @Title register
     * @Description: 用户注册走·旅行账号
     * @param phone
     * @param password
     * @Return: com.gotravel.vo.ResultVO
     * @Author: chenyx
     * @Date: 2020/3/20 14:20
     **/
    @Override
    public ResultVO register(String phone, String password) {

        Map<String, Object> resultMap = new HashMap<>();

        //判断是否已经存在该phone
        long exist = userRepository.checkPhoneIsExist(phone);

        if (exist == 0) {    //不存在

            //注册该用户基本信息
            User user = new User();
            user.setPhone(phone);
            user.setPassword(password);
            user.setBirthday(new Date());
            user.setGender(UserEnum.SECRECY.getMessage());
            user.setName(UserEnum.DEFAULT_NAME.getMessage());
            user.setImage("");
            user.setStatus(UserEnum.NOT_ACTIVE.getCode());

            userRepository.save(user);

            //注册该用户详细信息
            UserDetailed user_detailed = OtherUtils.newUserDetailed(phone);

            userDetailedDao.addUserDetailed(user_detailed);

            //返回官方的hobby和customization
            Label label = labelRedis.findAllLabel();

            List<String> label_hobby = label.getHobby();
            List<String> label_customization = label.getCustomization();

            resultMap.put("label_hobby", label_hobby);
            resultMap.put("label_customization", label_customization);

            //将status状态变成激活状态
            userRepository.updateUserStatus(user.getUserId(), UserEnum.ACTIVE.getCode());

            return ResultVOUtil.success(resultMap);

        } else {    //已存在

            return ResultVOUtil.error(ResultEnum.PHONE_EXIST.getCode(), ResultEnum.PHONE_EXIST.getMessage());
        }

    }



    /**
     * @Title login
     * @Description: 用户登录走·旅行
     * @param phone
     * @param password
     * @Return: com.gotravel.vo.ResultVO
     * @Author: chenyx
     * @Date: 2020/3/20 13:30
     **/
    @Override
    public ResultVO login(String phone, String password) {

        Map<String, Object> resultMap = new HashMap<>();

        //判断是否登录账号是否正确
        User user = userRepository.findByPhoneAndPassword(phone, password);

        if (null != user) {    //验证通过

            //用户基本信息
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);

            resultMap.put("user", userVO);

            return ResultVOUtil.success(resultMap);

        } else {    //验证不通过

            return ResultVOUtil.error(ResultEnum.LOGIN_ERROR.getCode(), ResultEnum.LOGIN_ERROR.getMessage());

        }

    }



    /**
     * @Title editUserInfo
     * @Description:  用户编辑基本信息
     * @param userId
     * @param name
     * @param gender
     * @param birthday
     * @param image
     * @Return: com.gotravel.entity.User
     * @Author: chenyx
     * @Date: 2020/3/20 15:31
     **/
    @Override
    public User editUserInfo(Integer userId, String name, String gender, String birthday, String image) {

        Date date = null;

        User user=userRepository.getOne(userId);

        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");//日期格式
            date = format.parse(birthday);

             user.setBirthday(date);

        } catch (Exception e) {
            log.info("【用户编辑基本信息】：日期转换失败 ,birthday={}", birthday);
        }

        user.setName(name);
        user.setGender(gender);
        user.setImage(image);

        //修改用户基本信息
        return userRepository.save(user);

    }


}
