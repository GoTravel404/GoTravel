package com.gotravel.controller;

import com.gotravel.enums.ResultEnum;
import com.gotravel.repository.PlaceCommentRepository;
import com.gotravel.service.PlaceCommentService;
import com.gotravel.utils.ResultVOUtil;
import com.gotravel.vo.PagePlaceCommentVO;
import com.gotravel.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.gotravel.enums.DefinedParam.PLACE_COMMENT_SIZE;
import static com.gotravel.enums.DefinedParam.USER_PLACE_COMMENT_QUANTITY;

/**
 * @Name: PlaceCommentController
 * @Description:景点评论Controller
 * @Author:chenyx
 * @Date: 2020/4/15 17:47
 **/
@Slf4j
@RequestMapping("/goTravel/placeComment")
@RestController
public class PlaceCommentController {

    @Autowired
    private PlaceCommentService placeCommentService;

    @Autowired
    private PlaceCommentRepository placeCommentRepository;

    /**
     * @Title increasePlacePraise
     * @Description: 景点添加好评
     * @param place_id
     * @Return: com.gotravel.vo.ResultVO
     * @Author: chenyx
     * @Date: 2020/3/29 11:46
     **/
    @RequestMapping(value = "/increasePlacePraise", method = RequestMethod.POST)
    public ResultVO increasePlacePraise(@RequestParam String place_id) {

        int modifiedCount = placeCommentService.increasePlacePraise(place_id);

        if (modifiedCount > 0) {

            return ResultVOUtil.success();

        } else {

            return ResultVOUtil.error(ResultEnum.INCREASE_PLACE_PRAISE.getCode(), ResultEnum.INCREASE_PLACE_PRAISE.getMessage());
        }

    }



    /**
     * @Title addPlaceComment
     * @Description: 用户添加景点评论
     * @param phone
     * @param place_id
     * @Return: com.gotravel.vo.ResultVO
     * @Author: chenyx
     * @Date: 2020/4/15 19:36
     **/
    @RequestMapping(value = "/addPlaceComment", method = RequestMethod.POST)
    public ResultVO addPlaceComment(@RequestParam String phone, @RequestParam String comment, @RequestParam String place_id) {

        long result= placeCommentRepository.checkTodayPlaceComment(phone,place_id);

        //判断每天每个用户最多给每个景点评论次数
        if(result>=USER_PLACE_COMMENT_QUANTITY){

            return ResultVOUtil.error(ResultEnum.ADD_PLACE_COMMENT_ERROR.getCode(),ResultEnum.ADD_PLACE_COMMENT_ERROR.getMessage());

        }

        placeCommentService.addPlaceComment(phone, comment, place_id);

        return ResultVOUtil.success();

    }



    /**
     * @Title increasePlaceCommentPraise
     * @Description: 用户点赞景点评论
     * @param phone 评论的用户phone
     * @param place_id 评论的景点id
     * @param placeCommentId 景点评论的id
     * @Return: com.gotravel.vo.ResultVO
     * @Author: chenyx
     * @Date: 2020/4/15 20:49
     **/
    @RequestMapping(value = "/increasePlaceCommentPraise")
    public ResultVO increasePlaceCommentPraise(@RequestParam String phone, @RequestParam String place_id, @RequestParam String placeCommentId) {

        placeCommentService.increasePlaceCommentPraise(phone, place_id, placeCommentId);

        return ResultVOUtil.success();
    }



    /**
     * @Title decreasePlaceCommentPraise
     * @Description: 用户取消景点评论点赞
     * @param phone
     * @param place_id
     * @param placeCommentId
     * @Return: com.gotravel.vo.ResultVO
     * @Author: chenyx
     * @Date: 2020/4/16 20:47
     **/
    @PostMapping(value = "/decreasePlaceCommentPraise")
    public ResultVO decreasePlaceCommentPraise(@RequestParam String phone, @RequestParam String place_id, @RequestParam String placeCommentId) {

        placeCommentService.decreasePlaceCommentPraise(phone, place_id, placeCommentId);

        return ResultVOUtil.success();
    }



    /**
     * @Title findPlaceCommentPage
     * @Description: 根据景点id分页查询景点评论
     * @param phone
     * @param place_id
     * @param page
     * @Return: com.gotravel.vo.ResultVO
     * @Author: chenyx
     * @Date: 2020/4/18 13:39
     **/
    @PostMapping(value = "/findPlaceCommentPage")
    public ResultVO findPlaceCommentPage(@RequestParam String phone, @RequestParam String place_id, @RequestParam(defaultValue = "0") Integer page) {

        PagePlaceCommentVO pagePlaceCommentVO = placeCommentService.selectPlaceCommentPageByPlaceId(phone, place_id, page, PLACE_COMMENT_SIZE);

        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("comment", pagePlaceCommentVO);

        return ResultVOUtil.success(resultMap);

    }



    /**
     * @Title deletePlaceComment
     * @Description: 删除景点评论
     * @param place_id
     * @param placeCommentId
     * @Return: com.gotravel.vo.ResultVO
     * @Author: chenyx
     * @Date: 2020/4/18 13:43
     **/
    @PostMapping(value = "/deletePlaceComment")
    public ResultVO deletePlaceComment(@RequestParam String place_id, @RequestParam String placeCommentId) {

        placeCommentService.deletePlaceComment(place_id, placeCommentId);

        return ResultVOUtil.success();

    }


}
