package com.gotravel.repository;

import com.gotravel.entity.PlaceComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

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


}
