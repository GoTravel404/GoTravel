package com.gotravel.service;

import java.util.List;

/**
 * @Name: IconService
 * @Description:
 * @Author:chenyx
 * @Date: 2020/5/12 11:49
 **/
public interface IconService {


    /**
     * 查询所有头像路径
     * @return
     */
    List<String> findAllIcon();

}
