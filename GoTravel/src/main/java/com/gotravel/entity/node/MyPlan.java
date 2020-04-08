package com.gotravel.entity.node;

import lombok.Data;

import java.util.List;

/**
 * 出行计划节点
{
"plan_name":"第一个计划",
"places_id":[152, 13, 58, 561],
"time":"2019-1-1 15:50:30"
}
**/
@Data
public class MyPlan implements  Comparable<MyPlan>{

	/**
	 * 计划名称
	 */
	private String plan_name;

	/**
	 * 计划的景点的id
	 */
	private List<String> places_id;

	/**
	 *  计划备注
	 */
	private String postscript;

	/**
	 * 创建计划时间
	 */
	private long time;


	public MyPlan(String plan_name, List<String> places_id,String postscript, long time) {
		this.plan_name = plan_name;
		this.places_id = places_id;
		this.postscript = postscript;
		this.time = time;
	}

	@Override
	public int compareTo(MyPlan o) {
		//o.time代表List里第一个元素，this.time是List里第二个元

		return Long.compare(o.time, this.time);
	}

}
