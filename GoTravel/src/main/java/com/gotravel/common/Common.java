/**
 * 
 */
package com.gotravel.common;

import com.alibaba.fastjson.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: TODO 公共类
 *  @date 2019年8月12日 下午11:39:53
 */
public class Common {

    /**
     * @Description:TODO 时间转换
     * @Param [dateStr]
     * @return java.util.Date
     * @Author: 陈一心
     * @Date: 2019/9/8  20:52
     **/
	public static Date TimeConversion(String dateStr) {
		Date time = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			time = simpleDateFormat.parse(dateStr);
			return time;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return time;
	}


    /**
     * @Title sendMessage
     * @Description: TODO 返回成功或失败的json信息
     * @Param [modifiedcount, message]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/10  10:06
     **/
	public static String sendMessage(int code,String message){
		JSONObject jsonObject = new JSONObject();
		if (code >= 1) {
			jsonObject.put("msg", message+"成功");
			jsonObject.put("code", 200);    //保留，以后前端可能会用于其他功能的触发标准
		} else {
			jsonObject.put("msg", message+"失败");
			jsonObject.put("code", 100);    //保留，以后前端可能会用于其他功能的触发标准
		}
		return jsonObject.toJSONString();
	}

	
}
