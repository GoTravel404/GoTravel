package com.gotravel.service.Impl;


import com.gotravel.dao.nosqldao.LabelDao;
import com.gotravel.pojo.Label;
import com.gotravel.service.LabelService;
import org.json.JSONObject;
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
     * @Title findLabel
     * @Description:TODO 查找官方的所有标签
     * @Param []
     * @return java.util.List<Label>
     * @Author: 陈一心
     * @Date: 2019/9/8  22:10
     **/
    @Override
    public List<Label> findLabel() {
        // TODO Auto-generated method stub
        return labelDao.findLabel();
    }


    /**
     * @Title getLabel
     * @Description:TODO 返回官方的所有标签
     * @Param []
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/8  22:10
     **/
    @Override
    public String getLabel() {
        // TODO Auto-generated method stub
        List<Label> labels = labelDao.findLabel();
        List<String> label_hobby = labels.get(0).getHobby();
        List<String> label_customization = labels.get(0).getCustomization();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("label_hobby", label_hobby);
        jsonObject.put("label_customization", label_customization);
        return jsonObject.toString();
    }

}
