package com.gotravel.service.Impl;


import com.gotravel.repository.redis.LabelRedis;
import com.gotravel.entity.Label;
import com.gotravel.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: abel官方标签表的Service实现层
 * @date 2019年8月11日 下午7:46:35
 */
@Service("labelService")
@Transactional
public class LabelServiceImpl implements LabelService {

    @Autowired
    private LabelRedis labelRedis;


    /**
     * @Title getAllLabel
     * @Description:  返回官方的所有标签
     * @param
     * @Return: java.util.Map<java.lang.String, java.lang.Object>
     * @Author: chenyx
     * @Date: 2020/3/19 19:05
     **/
    @Override
    public Map<String, Object> getAllLabel() {

        Label label = labelRedis.findAllLabel();

        List<String> label_hobby = label.getHobby();
        List<String> label_customization = label.getCustomization();
        List<String> label_place_type = label.getPlace_type();

        Map<String, Object> map = new HashMap<>();
        map.put("label_hobby", label_hobby);
        map.put("label_customization", label_customization);
        map.put("label_place_type", label_place_type);

        return map;
    }
}
