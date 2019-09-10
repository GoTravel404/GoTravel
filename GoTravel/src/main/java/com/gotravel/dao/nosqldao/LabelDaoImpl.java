package com.gotravel.dao.nosqldao;


import com.gotravel.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 
 * @Description: mongo的Label表的CURD实现层
 *  @date 2019年8月10日 上午11:18:27
 */
@Repository
public class LabelDaoImpl implements LabelDao {

	@Autowired
	private MongoTemplate mongoTemplate;


    /**
     * @Title findLabel
     * @Description:TODO 返回官方的标签
	 * @Param []
     * @return java.util.List<Label>
     * @Author: 陈一心
     * @Date: 2019/9/8  21:48
     **/
	@Override
	public List<Label> findLabel() {
		// TODO Auto-generated method stub
		List<Label> labels = mongoTemplate.findAll(Label.class);
		return labels;
	}
}
