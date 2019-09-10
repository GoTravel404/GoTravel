package com.gotravel.service;

import java.util.Map;

/**
 * 
 * @Description: Place景点表的Service接口
 *  @date 2019年8月11日 下午9:21:32
 */
public interface PlaceService {

	/**
	 * @Title findPlaceByuserlabel
	 * @Description:TODO 根据用户的标签为用户提供景点且按好评度排序
	 * @Param [phone]
	 * @return java.lang.String
	 * @Author: 陈一心
	 * @Date: 2019/9/9  22:24
	 **/
	String findPlaceByuserlabel(String phone);


	/**
	 * @Title findPlaceByplace_id
	 * @Description:TODO 根据景点的place_id返回景点信息
	 * @Param [place_id]
	 * @return java.lang.String
	 * @Author: 陈一心
	 * @Date: 2019/9/9  22:45
	 **/
	String findPlaceByplace_id(int place_id);


	/**
	 * @Title findPlaceByplace_type
	 * @Description:TODO 根据景点的place_type(封装成List类型)返回景点信息且按好评度排序
	 * @Param [map]
	 * @return java.lang.String
	 * @Author: 陈一心
	 * @Date: 2019/9/8  22:11
	 **/
	String findPlaceByplace_type(Map<String, Object> map);



}
