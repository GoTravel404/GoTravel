package com.gotravel.service.Impl;

import com.gotravel.dto.UserPraisePlaceCommentRedisDTO;
import com.gotravel.entity.PlaceComment;
import com.gotravel.enums.PlaceCommentEnum;
import com.gotravel.repository.PlaceCommentRepository;
import com.gotravel.repository.nosqldao.PlaceDao;
import com.gotravel.repository.redis.PlaceCommentRedis;
import com.gotravel.repository.redis.UserPraisePlaceCommentRedis;
import com.gotravel.service.PlaceCommentService;
import com.gotravel.utils.KeyUtils;
import com.gotravel.vo.PagePlaceCommentVO;
import com.gotravel.vo.PlaceCommentVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.gotravel.enums.DefinedParam.PLACE_COMMENT_PREFIX;

/**
 * @Name: PlaceCommentServiceImpl
 * @Description: 景点评论
 * @Author:chenyx
 * @Date: 2020/4/15 18:34
 **/
@Transactional
@Service
public class PlaceCommentServiceImpl implements PlaceCommentService {

    @Autowired
    private PlaceCommentRepository placeCommentRepository;

    @Autowired
    private UserPraisePlaceCommentRedis userPraisePlaceCommentRedis;

    @Autowired
    private PlaceDao placeDao;

    @Autowired
    private PlaceCommentRedis placeCommentRedis;


    /**
     * @Title increasePlacePraise
     * @Description: 景点添加好评
     * @param place_id
     * @Return: int
     * @Author: chenyx
     * @Date: 2020/3/29 11:47
     **/
    @Override
    public int increasePlacePraise(String place_id) {

        return placeDao.increasePlacePraise(place_id);
    }


    /**
     * @Title addPlaceComment
     * @Description: 用户添加景点评论
     * @param phone
     * @param name
     * @param comment
     * @param place_id
     * @Return: void
     * @Author: chenyx
     * @Date: 2020/4/15 18:35
     **/
    @Override
    public void addPlaceComment(String phone, String name, String comment, String place_id) {

        String placeCommentId = PLACE_COMMENT_PREFIX + KeyUtils.getUniqueKry();

        PlaceComment placeComment = new PlaceComment();

        placeComment.setPlaceCommentId(placeCommentId);
        placeComment.setPhone(phone);
        placeComment.setName(name);
        placeComment.setPlaceComment(comment);
        placeComment.setPlaceId(place_id);
        placeComment.setPraise(0);

        placeCommentRepository.save(placeComment);

    }


    /**
     * @Title increasePlaceCommentPraise
     * @Description: 用户点赞景点评论
     * @param phone
     * @param place_id
     * @param placeCommentId
     * @Return: void
     * @Author: chenyx
     * @Date: 2020/4/15 19:53
     **/
    @Override
    public void increasePlaceCommentPraise(String phone, String place_id, String placeCommentId) {

        placeCommentRedis.increasePlaceCommentPraise(phone, place_id, placeCommentId);

    }


    /**
     * @Title decreasePlaceCommentPraise
     * @Description: 用户取消景点评论点赞
     * @param phone
     * @param place_id
     * @param placeCommentId
     * @Return: void
     * @Author: chenyx
     * @Date: 2020/4/16 20:35
     **/
    @Override
    public void decreasePlaceCommentPraise(String phone, String place_id, String placeCommentId) {

        placeCommentRedis.decreasePlaceCommentPraise(phone, place_id, placeCommentId);

    }


    /**
     * @Title selectPlaceCommentPageByPlaceId
     * @Description: 根据景点id分页查询景点评论
     * @param phone
     * @param place_id
     * @param page
     * @param size
     * @Return: com.gotravel.vo.PagePlaceCommentVO
     * @Author: chenyx
     * @Date: 2020/4/17 20:47
     **/
    @Override
    public PagePlaceCommentVO selectPlaceCommentPageByPlaceId(String phone, String place_id, int page, int size) {

        //根据点赞数降序
        Sort sort = new Sort(Sort.Direction.DESC, "praise");

        //分页，每页多少条记录
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<PlaceComment> placeCommentPage = placeCommentRepository.findPlaceCommentByPlaceId(place_id, pageable);


        //一共多少页
        int totalPage = placeCommentPage.getTotalPages();

        //获取景点评论
        List<PlaceComment> placeCommentList = placeCommentPage.getContent();

        //当页景点评论总数
        int total = placeCommentList.size();

        if (0 == total)
            return null;

        //返回评论是否被该用户点赞
        List<UserPraisePlaceCommentRedisDTO> userPraisePlaceCommentRedisDTOList = placeCommentList.stream().map(e -> new UserPraisePlaceCommentRedisDTO(phone, place_id, e.getPlaceCommentId())).collect(Collectors.toList());

        List<Boolean> isPraiseList = userPraisePlaceCommentRedis.determineHasKey(userPraisePlaceCommentRedisDTOList);


        List<PlaceCommentVO> placeCommentVOList = new ArrayList<>();

        for (int i = 0; i < placeCommentList.size(); i++) {

            PlaceCommentVO placeCommentVO = new PlaceCommentVO();
            BeanUtils.copyProperties(placeCommentList.get(i), placeCommentVO);

            if (isPraiseList.get(i)) {

                placeCommentVO.setIsPraise(PlaceCommentEnum.PRAISE.getCode());

            } else {

                placeCommentVO.setIsPraise(PlaceCommentEnum.NO_PRAISE.getCode());

            }

            placeCommentVOList.add(placeCommentVO);
        }


        //封装数据
        PagePlaceCommentVO pagePlaceCommentVO = new PagePlaceCommentVO();
        pagePlaceCommentVO.setPage(page);
        pagePlaceCommentVO.setTotal(total);
        pagePlaceCommentVO.setTotalPage(totalPage);
        pagePlaceCommentVO.setPlaceCommentVOList(placeCommentVOList);

        return pagePlaceCommentVO;

    }


    /**
     * @Title deletePlaceComment
     * @Description: 删除景点评论
     * @param place_id
     * @param placeCommentId
     * @Return: void
     * @Author: chenyx
     * @Date: 2020/4/18 14:08
     **/
    @Override
    public void deletePlaceComment(String place_id, String placeCommentId) {

        //删除数据库
        placeCommentRepository.deletePlaceCommentByPlaceCommentId(placeCommentId);

        //删除redis中关于该景点评论的点赞数和点赞用户
        placeCommentRedis.deletePlaceComment(place_id,placeCommentId);

    }


}
