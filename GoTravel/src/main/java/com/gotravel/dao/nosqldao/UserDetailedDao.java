package com.gotravel.dao.nosqldao;


import com.gotravel.model.Myhistory;
import com.gotravel.model.Myplan;
import com.gotravel.model.User_detailed;

import java.util.Date;
import java.util.List;

/**
 * 
 * @Description: mongodb的User_Detailed表的CURD接口
 *  @date 2019年8月10日 下午9:05:01
 */
public interface UserDetailedDao {


	/**
	 * @Title findOne
	 * @Description:TODO 根据手机phone查找用户的详细信息
	 * @Param [phone]
	 * @return User_detailed
	 * @Author: 陈一心
	 * @Date: 2019/9/8  21:53
	 **/
	 User_detailed findOne(String phone);


	/**
	 * @Title adduserdetailed
	 * @Description:TODO 注册账号添加用户详细信息
	 * @Param [user_detailed]
	 * @return void
	 * @Author: 陈一心
	 * @Date: 2019/9/8  21:54
	 **/
	 void adduserdetailed(User_detailed user_detailed);


	/**
	 * @Title edit_label
	 * @Description:TODO 根据phone编辑个人标签
	 * @Param [phone, hobby, customization]
	 * @return int
	 * @Author: 陈一心
	 * @Date: 2019/9/8  21:54
	 **/
	 int edit_label(String phone, List<String> hobby, List<String> customization);


	/**
	 * @Title findByphone
	 * @Description:TODO 根据phone提供该用户的个人详细信息
	 * @Param [phone]
	 * @return User_detailed
	 * @Author: 陈一心
	 * @Date: 2019/9/8  21:54
	 **/
	 User_detailed findByphone(String phone);

	/**
	 * @Title: addmycollections 
	 * @Description:  根据phone添加个人收藏景点
	 * @param 	phone, place_id,  time
	 * @return int  
	 * @author 陈一心
	 * @date 2019年8月12日 下午3:38:49
	 */
	 int addmycollections(String phone, int place_id, Date time);


	/**
	 * @Title deletemycollections
	 * @Description:TODO 根据phone和places_id删除个人收藏景点
	 * @Param [phone, place_id]
	 * @return int
	 * @Author: 陈一心
	 * @Date: 2019/9/8  21:54
	 **/
	 int deletemycollections(String phone, int place_id);


	/**
	 * @Title addmyplans
	 * @Description:TODO 根据phone添加个人出行计划
	 * @Param [phone, plan_name, places_id, time]
	 * @return int
	 * @Author: 陈一心
	 * @Date: 2019/9/8  21:54
	 **/
	 int addmyplans(String phone, String plan_name, List<Integer> places_id, Date time);


	/**
	 * @Title editmyplans
	 * @Description:TODO 根据phone和time修改个人出行计划
	 * @Param [phone, plan_name, places_id, time]
	 * @return int
	 * @Author: 陈一心
	 * @Date: 2019/9/8  21:55
	 **/
	 int editmyplans(String phone, String plan_name, List<Integer> places_id, Date time);
	

	/**
	 * @Title deletemyplans
	 * @Description:TODO 根据phone和time删除个人出行计划
	 * @Param [phone, time]
	 * @return int
	 * @Author: 陈一心
	 * @Date: 2019/9/8  21:55
	 **/
	 int deletemyplans(String phone, Date time);


	/**
	 * @Title addhistory
	 * @Description:TODO 根据phone和time(date)添加历史出行
	 * @Param [phone, place_id, time]
	 * @return int
	 * @Author: 陈一心
	 * @Date: 2019/9/8  21:55
	 **/
	 int addhistory(String phone, int place_id, String dateStr) ;


	/**
	 * @Title deletehistoryplace
	 * @Description:TODO 根据phone和date删除历史出行的单个景点
	 * @Param [phone, place_id, date]
	 * @return int
	 * @Author: 陈一心
	 * @Date: 2019/9/8  21:55
	 **/
	 int deletehistoryplace(String phone, int place_id, String date);


	/**
	 * @Title deletehistory
	 * @Description:TODO 根据phone和date删除历史出行记录
	 * @Param [phone, date]
	 * @return int
	 * @Author: 陈一心
	 * @Date: 2019/9/8  21:56
	 **/
	 int deletehistory(String phone, String date);


	/**
	 * @Title findmycollections
	 * @Description:TODO  根据phone联表(user_detailed和place)查询所有个人收藏的景点
	 * @Param [phone]
	 * @return java.lang.String
	 * @Author: 陈一心
	 * @Date: 2019/9/8  21:58
	 **/
	 String findmycollections(String phone);


	/**
	 * @Title findmyhistories
	 * @Description:TODO 根据phone查询所有历史出行(返回所有日期的历史出行)
	 * @Param [phone]
	 * @return java.util.List<Myhistory>
	 * @Author: 陈一心
	 * @Date: 2019/9/8  21:59
	 **/
	 List<Myhistory> findmyhistories(String phone);


	/**
	 * @Title findmyhistories_detailed
	 * @Description:TODO 根据phone和date联表(user_detailed和place)查找某一天的历史出行详情列表
	 * @Param [phone, date]
	 * @return java.lang.String
	 * @Author: 陈一心
	 * @Date: 2019/9/8  21:59
	 **/
	 String findmyhistories_detailed(String phone, String date);


	/**
	 * @Title findmyplans
	 * @Description:TODO 根据phone查找所有出行计划(返回所有出行计划的名称列表)
	 * @Param [phone]
	 * @return java.util.List<Myplan>
	 * @Author: 陈一心
	 * @Date: 2019/9/8  21:59
	 **/
	 List<Myplan> findmyplans(String phone);


	/**
	 * @Title findmyplans_detailed
	 * @Description:TODO 根据phone和time联表(user_detailed和place)查询出行计划详情列表
	 * @Param [phone, time]
	 * @return java.lang.String
	 * @Author: 陈一心
	 * @Date: 2019/9/8  21:59
	 **/
	 String findmyplans_detailed(String phone, Date time);

	 /**
	  * @Title findMyCollectionsPlaceId
	  * @Description: TODO 根据phone查找用户收藏的所有景点，组成List<Integer>
	  * @param phone 手机号
	  * @return java.util.List<java.lang.Integer>
	  * @Author: chenyx
	  * @Date: 2019/11/8  21:35
	  **/
	 List<Integer> findMyCollectionsPlaceId(String phone);

}
