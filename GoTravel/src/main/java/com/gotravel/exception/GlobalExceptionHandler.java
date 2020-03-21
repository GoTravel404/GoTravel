package com.gotravel.exception;

import com.gotravel.enums.ResultEnum;
import com.gotravel.utils.ResultVOUtil;
import com.gotravel.vo.ResultVO;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName:GlobalExceptionHandler
 * @Description:TODO 全局异常处理
 * @Author:陈一心
 * @Date:Create in  2019/9/9 22:39
 **/

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler
    //@ResponseStatus
    public ResultVO runtimeExceptionHandler(Exception e) {

        e.printStackTrace();

        return ResultVOUtil.exception(ResultEnum.EXCEPTION.getCode(), ResultEnum.EXCEPTION.getMessage());
    }


}
