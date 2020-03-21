package com.gotravel.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @Name: ResultVo
 * @Description:TODO http请求返回的最外层对象
 * @Author:chenyx
 * @Date: 2020/1/13 20:42
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL) //字段为null时候不展示该字段
public class ResultVO {

    /**
     * 状态码
     */
    private  Integer code;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 具体内容
     */
    private Object data;
}
