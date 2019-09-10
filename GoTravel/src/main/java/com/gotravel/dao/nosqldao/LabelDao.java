package com.gotravel.dao.nosqldao;


import com.gotravel.pojo.Label;

import java.util.List;

/**
 * 
 * @Description: mongo的label表的CURD接口
 *  @date 2019年8月10日 上午11:17:46
 */
public interface LabelDao {

    /**
     * @Title findLabel
     * @Description:TODO 返回官方的标签
     * @Param []
     * @return java.util.List<Label>
     * @Author: 陈一心
     * @Date: 2019/9/8  21:48
     **/
	public List<Label> findLabel();

	
}
