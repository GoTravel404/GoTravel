package com.gotravel.controller;

import com.gotravel.service.LabelService;
import com.gotravel.utils.ResultVOUtil;
import com.gotravel.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 *
 * @Description:  Label集合的所有操作接口
 *  @date 2019年8月4日 下午8:59:41
 */
@RequestMapping("/goTravel/label")
@RestController
public class LabelController {

    @Autowired
    private LabelService labelService;


    /**
     * @Title getAllLabel
     * @Description:  返回官方的所有标签
     * @param
     * @Return: com.gotravel.vo.ResultVO
     * @Author: chenyx
     * @Date: 2020/3/19 19:17
     **/
    @RequestMapping(value = "/getAllLabel", method = RequestMethod.GET)
    public ResultVO getAllLabel() {

        Map<String, Object> map = labelService.getAllLabel();

        return ResultVOUtil.success(map);

    }

}
