package com.gotravel.service;

import com.gotravel.model.Label;

import java.util.List;

/**
 *
 * @Description: Label官方标签表的Service接口层
 *  @date 2019年8月11日 下午7:46:00
 */
public interface LabelService {


    /**
     * @Title findLabel
     * @Description:TODO 查找官方的所有标签
     * @Param []
     * @return java.util.List<Label>
     * @Author: 陈一心
     * @Date: 2019/9/8  22:09
     **/
    List<Label> findLabel();


    /**
     * @Title getLabel
     * @Description:TODO 返回官方的标签
     * @Param []
     * @return java.lang.String
     * @Author: 陈一心
     * @Date: 2019/9/8  22:09
     **/
    String getLabel();


    /**
     * @Title getAllLabel
     * @Description: TODO 返回官方的所有标签
     * @param
     * @return java.lang.String
     * @Author: chenyx
     * @Date: 2019/12/11  11:27
     **/
    String getAllLabel();
}
