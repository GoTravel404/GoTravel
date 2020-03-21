package com.gotravel.utils;
import com.gotravel.enums.ResultEnum;
import com.gotravel.vo.ResultVO;

/**
 * @Name: ResultVOUtil
 * @Description:TODO 返回的模板
 * @Author:chenyx
 * @Date: 2020/1/13 21:57
 **/
public class ResultVOUtil {


    /**
     * 成功
     * @param object
     * @return
     */
    public static ResultVO success(Object object){

        ResultVO resultVO=new ResultVO();
        resultVO.setCode(ResultEnum.SUCCESS.getCode());
        resultVO.setMessage(ResultEnum.SUCCESS.getMessage());
        resultVO.setData(object);

        return  resultVO;
    }



    public static ResultVO success(){
        return  success(null);
    }



    /**
     * 失败
     * @param code
     * @param msg
     * @return
     */
    public static ResultVO error(Integer code, String msg){

        ResultVO resultVO=new ResultVO();
        resultVO.setCode(code);
        resultVO.setMessage(msg);

        return resultVO;
    }



    /**
     * 异常
     * @param code
     * @param msg
     * @return
     */
    public static ResultVO exception(Integer code, String msg){

        ResultVO resultVO=new ResultVO();
        resultVO.setCode(code);
        resultVO.setMessage(msg);

        return resultVO;
    }


}
