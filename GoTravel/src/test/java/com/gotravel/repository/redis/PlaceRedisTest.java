package com.gotravel.repository.redis;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


/**
 * @Name: PlaceRedisTest
 * @Description:TODO
 * @Author:chenyx
 * @Date: 2020/3/19 15:25
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class PlaceRedisTest {

@Autowired
private PlaceRedis placeRedis;

    @Test
    public void getRedisAllPlacesList() {
    }

    @Test
    public void getARedisPlaceByKey() {
    }

    @Test
    public void getPlaceListByPipeline() {

        List<String> stringList=new ArrayList<>();
        stringList.add("1584274526351944840");
        stringList.add("1584274526394623760");
        stringList.add("1584274526397447828");
        stringList.add("1584274526399217719");
        stringList.add("1584274526351944840");

        placeRedis.getPlaceListByPipeline(stringList);
    }
}
