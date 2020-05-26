package com.gotravel.repository;

import com.gotravel.entity.PlaceComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @Name: PlaceCommentRepository
 * @Description: 景点评论
 * @Author:chenyx
 * @Date: 2020/4/15 18:25
 **/
public interface PlaceCommentRepository extends JpaRepository<PlaceComment, String> {


    /**
     * 根据景点id分页查询景点评论
     * @param placeId
     * @param pageable
     * @return
     */
    Page<PlaceComment> findPlaceCommentByPlaceId(String placeId, Pageable pageable);


    /**
     * 根据景点评论id删除景点评论
     * @param placeCommentId
     */
    void deletePlaceCommentByPlaceCommentId(String placeCommentId);


    /**
     * 查询今天某一用户的评论某一景点数量
     */
    @Query(value = "select count(*) from place_comment where phone=?1 and place_id=?2 and date_format(create_time,'%Y%m%d')=date_format(now(),'%Y%m%d') LIMIT 3;", nativeQuery = true)
    long checkTodayPlaceComment(String phone,String place_id);



}
