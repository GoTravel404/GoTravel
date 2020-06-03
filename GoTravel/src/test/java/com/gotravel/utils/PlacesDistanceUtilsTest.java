package com.gotravel.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @Name: PlacesDistanceUtilsTest
 * @Description:
 * @Author:chenyx
 * @Date: 2020/5/26 15:40
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class PlacesDistanceUtilsTest {

    @Test
    public void getFitDistancePlaces() {

        double a = 110.922792123;

        double b = 21.650403451651;

        System.out.println(a + "," + b);


    }

    @Test
    public void getDistance_2() {

        List<String> a = new ArrayList<>();
        a.add("aa");
        a.add("bb");
        a.add("cc");
        a.add("dd");
        a.add("ee");
        a.add("ff");



        String cc = "cc";

        a.add(0, cc);
        boolean status = true;

//        for (String s : a) {
//
//            if (status && s.equals(cc)) {
//
//
//
//                status = false;
//                continue;
//            }
//
//            System.out.println(s);
//
//        }

        System.out.println(a.toString());


        //  System.out.println(PlacesDistanceUtils.getDistance_2(113.264434,23.129162,113.324535,23.10642));
        //  System.out.println(PlacesDistanceUtils.getDistance_2(113.264434,23.129162,113.264434,23.129162));
    }




}