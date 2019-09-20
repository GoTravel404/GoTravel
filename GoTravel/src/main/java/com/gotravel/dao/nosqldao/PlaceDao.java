package com.gotravel.dao.nosqldao;


import com.gotravel.model.Place;
import com.gotravel.model.User_detailed;
import java.util.List;

/**
 * 
 * @Description:  monodb的Place表的CURD接口
 *  @date 2019年8月11日 下午9:53:35
 */
public interface PlaceDao {

	/**
	 * @Title findByuser_label
	 * @Description:TODO 根据用户的标签为用户提供景点且按好评度排序
	 * @Param [user_detailed]
	 * @return java.util.List<Place>
	 * @Author: 陈一心
	 * @Date: 2019/9/8  21:49
	 **/
	public List<Place> findByuser_label(User_detailed user_detailed);


	/**
	 * @Title findPlaceByplace_id
	 * @Description:TODO 根据景点的place_id返回景点信息
	 * @Param [place_id]
	 * @return Place
	 * @Author: 陈一心
	 * @Date: 2019/9/8  21:50
	 **/
	public Place findPlaceByplace_id(int place_id);


	/**
	 * @Title findPlaceByplace_type
	 * @Description:TODO 根据景点的place_type(封装成List类型)返回景点信息且按好评度排序
	 * @Param [place_type]
	 * @return java.util.List<Place>
	 * @Author: 陈一心
	 * @Date: 2019/9/8  21:50
	 **/
	public List<Place> findPlaceByplace_type(List<String> place_type);

	/**
	 * @Title findPlacesByPraise
	 * @Description: TODO 根据好评度(热门)+地点设定的范围返回景点信息且按好评度排序
	 * @param distance 搜索范围
	 * @param lon 经度
	 * @param lat 维度
	 * @return java.util.List<com.gotravel.model.Place>
	 * @Author: chenyx
	 * @Date: 2019/9/20  16:27
	 **/
    List findPlacesByPraise(int distance, double lon, double lat);
}
