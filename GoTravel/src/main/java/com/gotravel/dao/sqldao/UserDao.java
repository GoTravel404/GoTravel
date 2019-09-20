/**
 * 
 */
package com.gotravel.dao.sqldao;


import com.gotravel.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @Description: User表的CURD接口
 *  @date 2019年8月8日 下午11:50:50
 */

@Repository("userDao")
@Mapper
public interface UserDao {

	/**
	 * @Title check_phone_isexist
	 * @Description:TODO 检查是否已经存在phone
	 * @Param [phone]
	 * @return int
	 * @Author: 陈一心
	 * @Date: 2019/9/8  22:04
	 **/
	public int check_phone_isexist(String phone);

	/**
	 * @Title register
	 * @Description:TODO 注册用户账号
	 * @Param [phone, password]
	 * @return void
	 * @Author: 陈一心
	 * @Date: 2019/9/8  22:04
	 **/
	public void register(String phone, String password);


	/**
	 * @Title login
	 * @Description:TODO 用户登录验证
	 * @Param [phone, password]
	 * @return java.util.List<User>
	 * @Author: 陈一心
	 * @Date: 2019/9/8  22:04
	 **/
	public List<User> login(String phone, String password);


	/**
	 * @Title addlogins
	 * @Description:TODO 登录次数logins+1，用于判断登录时候是否返回官方label
	 * @Param [phone]
	 * @return void
	 * @Author: 陈一心
	 * @Date: 2019/9/8  22:04
	 **/
	public void addlogins(String phone);


	/**
	 * @Title editUser
	 * @Description:TODO 修改用户基本信息
	 * @Param [user]
	 * @return void
	 * @Author: 陈一心
	 * @Date: 2019/9/8  22:05
	 **/
	public void editUser(User user);



}
