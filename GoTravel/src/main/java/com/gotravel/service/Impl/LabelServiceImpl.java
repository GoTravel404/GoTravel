package com.gotravel.service.Impl;


import com.alibaba.fastjson.JSONObject;
import com.gotravel.dao.nosqldao.LabelDao;
import com.gotravel.entity.Label;
import com.gotravel.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description: abel官方标签表的Service实现层
 * @date 2019年8月11日 下午7:46:35
 */
@Service("labelService")
@Transactional
public class LabelServiceImpl implements LabelService {

    @Autowired
    private LabelDao labelDao;


    /**
     * @Title getLabel
     * @Description:TODO 返回官方的标签
     * @Param []
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/8  22:10
     **/
    @Override
    public String getLabel() {

        List<Label> labels = labelDao.findLabel();

        List<String> label_hobby = labels.get(0).getHobby();
        List<String> label_customization = labels.get(0).getCustomization();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("label_hobby", label_hobby);
        jsonObject.put("label_customization", label_customization);

        return jsonObject.toJSONString();
    }


    /**
     * @Title getAllLabel
     * @Description: TODO 返回官方的所有标签
     * @param
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/12/11  11:28
     **/
    @Override
    public String getAllLabel() {

        List<Label> labels = labelDao.findLabel();

        List<String> label_hobby = labels.get(0).getHobby();
        List<String> label_customization = labels.get(0).getCustomization();
        List<String> label_place_type = labels.get(0).getPlace_type();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("label_hobby", label_hobby);
        jsonObject.put("label_customization", label_customization);
        jsonObject.put("label_place_type", label_place_type);

        return jsonObject.toJSONString();
    }
}
