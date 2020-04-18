package com.gotravel.repository.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Name: PlaceCommentRedisTest
 * @Description:
 * @Author:chenyx
 * @Date: 2020/4/15 20:20
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class PlaceCommentRedisTest {

    @Autowired
    private PlaceCommentRedis placeCommentRedis;

    @Test
    public void addPlaceCommentListToRedis() {
    }

    @Test
    public void addAPlaceCommentToRedis() {
    }

    @Test
    public void increasePlaceCommentPraise() {
       // placeCommentRedis.increasePlaceCommentPraise("1000","pc1586948695129545732");
    }

    @Test
    public void deletePlaceComment() {

        placeCommentRedis.deletePlaceComment("1584274526754913423","pc1587126180476710399");

    }
}