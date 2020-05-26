package com.gotravel.service.Impl;

import com.gotravel.entity.Icon;
import com.gotravel.repository.IconRepository;
import com.gotravel.service.IconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Name: IconServiceImpl
 * @Description:
 * @Author:chenyx
 * @Date: 2020/5/12 11:50
 **/
@Service
public class IconServiceImpl implements IconService {

    @Autowired
    private IconRepository iconRepository;


    /**
     * @Title findAllIcon
     * @Description: 查询所有头像路径
     * @param
     * @Return: java.util.List<java.lang.String>
     * @Author: chenyx
     * @Date: 2020/5/12 11:51
     **/
    @Override
    public List<String> findAllIcon() {

        List<Icon> iconList = iconRepository.findAll();

        return iconList.stream().map(Icon::getRoute).collect(Collectors.toList());
    }


}
