package com.gotravel.repository.redis;

import com.gotravel.dto.UserPraisePlaceCommentRedisDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

/**
 * @Name: UserPraisePlaceCommentRedisTest
 * @Description:
 * @Author:chenyx
 * @Date: 2020/4/17 18:41
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserPraisePlaceCommentRedisTest {

    @Autowired
    private UserPraisePlaceCommentRedis userPraisePlaceCommentRedis;

    @Test
    public void determineHasKey() {

        UserPraisePlaceCommentRedisDTO userPraisePlaceCommentRedisDTO1=new UserPraisePlaceCommentRedisDTO();
        userPraisePlaceCommentRedisDTO1.setPhone("10011");
        userPraisePlaceCommentRedisDTO1.setPlaceCommentId("pc1587110971941665840");
        userPraisePlaceCommentRedisDTO1.setPlaceId("1584274526754913423");


        UserPraisePlaceCommentRedisDTO userPraisePlaceCommentRedisDTO2=new UserPraisePlaceCommentRedisDTO();
        userPraisePlaceCommentRedisDTO2.setPhone("100121");
        userPraisePlaceCommentRedisDTO2.setPlaceCommentId("pc1587110971941665840");
        userPraisePlaceCommentRedisDTO2.setPlaceId("1584274526754913423");

        userPraisePlaceCommentRedis.determineHasKey(Arrays.asList(userPraisePlaceCommentRedisDTO1,userPraisePlaceCommentRedisDTO2));
    }
}