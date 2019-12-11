package com.gotravel.controller;

import com.gotravel.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @Description: TODO Label集合的所有操作接口
 *  @date 2019年8月4日 下午8:59:41
 */
@RestController
public class Labelcontroller {

    @Autowired
    private LabelService labelService;

    /**
     * @Title getlabel
     * @Description:TODO 返回官方的标签；只返回了爱好和制定
     * @Param [request]
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/8  21:17
     **/
    @RequestMapping(value = "/getlabel", method = RequestMethod.GET)
    public String getlabel() {
        return labelService.getLabel();
    }


    /**
     * @Title getAllLabel
     * @Description: TODO 返回官方的所有标签
     * @param
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/12/11  11:26
     **/
    @RequestMapping(value = "/getAllLabel", method = RequestMethod.GET)
    public String getAllLabel() {
        return labelService.getAllLabel();
    }
}
