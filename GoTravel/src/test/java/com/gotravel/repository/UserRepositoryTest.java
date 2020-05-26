package com.gotravel.repository;

import com.gotravel.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @Name: UserRepositoryTest
 * @Description:
 * @Author:chenyx
 * @Date: 2020/5/15 21:30
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    public void findByPhoneIn() {
        List<User> userList = repository.findByPhoneIn(Arrays.asList("101", "101", "101"));

        System.out.println(userList);
    }
}