package com.gotravel.repository.nosqldao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Name: UserDetailedDaoImplTest
 * @Description:TODO
 * @Author:chenyx
 * @Date: 2020/3/21 20:07
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDetailedDaoImplTest {

    @Autowired
    private UserDetailedDaoImpl userDetailedDao;

    @Test
    public void addHistory() {
        userDetailedDao.addHistory("100","222");
    }

    @Test
    public void deleteHistoryPlace() {
        userDetailedDao.deleteHistoryPlace("100",1584793832919L,"2020-03-21");
    }



}