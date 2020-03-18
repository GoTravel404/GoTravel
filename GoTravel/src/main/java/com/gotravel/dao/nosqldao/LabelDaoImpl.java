package com.gotravel.dao.nosqldao;


import com.gotravel.entity.Label;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 
 * @Description: mongo的Label表的CURD实现层
 *  @date 2019年8月10日 上午11:18:27
 */
@Slf4j
@Repository
public class LabelDaoImpl implements LabelDao {

	//Redis缓存的所有景点的Label的key值
	private final static String REDIS_KEY = "AllPlacesLabel";

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	//注入springboot自动配置好的redisTemplate
	private RedisTemplate<Object, Object> redisTemplate;


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

		//字符串序列化器
		RedisSerializer redisSerializer=new StringRedisSerializer();
		redisTemplate.setKeySerializer(redisSerializer);

		//查询缓存
		List<Label> labels= (List<Label>) redisTemplate.opsForValue().get(REDIS_KEY);

		//双重检测锁
		if(null==labels) {
			synchronized (this) {  //加锁
				//从redis获取一下
				labels = (List<Label>) redisTemplate.opsForValue().get(REDIS_KEY);
				if (null == labels) {
					log.info("-----------查询数据库-------------");
					//缓存为空,查询一遍数据库
					labels =  mongoTemplate.findAll(Label.class);
					//把数据库查询出来的数据，放到redis中
					redisTemplate.opsForValue().set(REDIS_KEY, labels);
				}else{
					log.info("-----------查询缓存-------------");
				}
			}
		}else {
			log.info("-----------查询缓存-------------");
		}
		return labels;
	}

}
