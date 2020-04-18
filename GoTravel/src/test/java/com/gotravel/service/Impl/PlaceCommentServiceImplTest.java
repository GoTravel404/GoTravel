package com.gotravel.service.Impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Name: PlaceCommentServiceImplTest
 * @Description:
 * @Author:chenyx
 * @Date: 2020/4/15 18:56
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class PlaceCommentServiceImplTest {

    @Autowired
    private PlaceCommentServiceImpl placeCommentService;


    @Test
    public void addPlaceComment() {
        // placeCommentService.addPlaceComment("110", "渐渐放松放松", "1586947120118666878");

    }

    @Test
    public void increasePlacePraise() {
    }

    @Test
    public void increasePlaceCommentPraise() {
    }

    @Test
    public void decreasePlaceCommentPraise() {
    }

    @Test
    public void selectPlaceCommentPageByPlaceId() {

        placeCommentService.selectPlaceCommentPageByPlaceId("10012","1584274526754913423",0,3);

    }
}