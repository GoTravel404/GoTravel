/**
 * 
 */
package com.gotravel.dao.sqldao;


import com.gotravel.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @Description: TODO User表的CURD接口
 *  @date 2019年8月8日 下午11:50:50
 */

@Repository("userDao")
@Mapper
public interface UserDao {

	/**
	 * @Title check_phone_is_exist
	 * @Description:TODO 检查是否已经存在phone
	 * @Param [phone]
	 * @return int
	 * @Author: 陈一心
	 * @Date: 2019/9/8  22:04
	 **/
	 int check_phone_is_exist(String phone);

	/**
	 * @Title register
	 * @Description:TODO 注册用户账号
	 * @Param [phone, password]
	 * @return void
	 * @Author: 陈一心
	 * @Date: 2019/9/8  22:04
	 **/
	 void register(String phone, String password);


	/**
	 * @Title login
	 * @Description:TODO 用户登录验证
	 * @Param [phone, password]
	 * @return java.util.List<User>
	 * @Author: 陈一心
	 * @Date: 2019/9/8  22:04
	 **/
	 List<User> login(String phone, String password);


	/**
	 * @Title editStatusToActive
	 * @Description:TODO 将status状态变成激活状态
	 * @Param [phone]
	 * @return void
	 * @Author: 陈一心
	 * @Date: 2019/9/8  22:04
	 **/
	 void editStatusToActive(String phone);


	/**
	 * @Title editUser
	 * @Description:TODO 修改用户基本信息
	 * @Param [user]
	 * @return void
	 * @Author: 陈一心
	 * @Date: 2019/9/8  22:05
	 **/
	 void editUser(User user);



}
