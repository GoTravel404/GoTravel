/**
 * 
{
"places_id":[
{"place_id":20,"time":"2019-10-1 10:02:23"},
{"place_id":10,"time":"2019-10-1 12:08:23"},
{"place_id":2,"time":"2019-10-1 16:22:23"}
],
"date":"2019-10-1"
},

 */
package com.gotravel.entity.node;
import lombok.Data;

import java.util.List;

/**
 * 用户出行记录的节点
 *
 */
@Data
public class MyHistory {

	/**
	 * 到达的景点与时间
	 */
	private List<PlaceIdTime> places_time;

	/**
	 * 出行日期
	 */
	private String date;

	public MyHistory(List<PlaceIdTime> places_time, String date) {
		this.places_time = places_time;
		this.date = date;
	}

}
