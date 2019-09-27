package com.gotravel.exception;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

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
        @ResponseStatus
        public String runtimeExceptionHandler(Exception e){
            JSONObject json = new JSONObject();
            json.put("msg", "系统出错啦");
            json.put("code", 500);
           // e.printStackTrace();
            return json.toString();
    }
}
