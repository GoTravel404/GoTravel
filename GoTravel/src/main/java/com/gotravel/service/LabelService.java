package com.gotravel.service;

import java.util.Map;

/**
 *
 * @Description: Label官方标签表的Service接口层
 *  @date 2019年8月11日 下午7:46:00
 */
public interface LabelService {


    /**
     * 返回官方的所有标签
     * @return
     */
    Map<String, Object> getAllLabel();


    /**
     * 返回范围标签
     * @return
     */
    Map<String, Object> getLabelOfRange();
}
