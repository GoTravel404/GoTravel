package com.gotravel.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Name: PlaceCommentRepositoryTest
 * @Description:
 * @Author:chenyx
 * @Date: 2020/5/15 22:10
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class PlaceCommentRepositoryTest {

    @Autowired
    private PlaceCommentRepository placeCommentRepository;

    @Test
    public void checkTodayPlaceComment() {

       long result= placeCommentRepository.checkTodayPlaceComment("100","1584274526754913423");

        System.out.println(result);

    }
}