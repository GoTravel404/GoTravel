package com.gotravel.repository;

import com.gotravel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @Name: UserRepository
 * @Description:TODO 用户信息
 * @Author:chenyx
 * @Date: 2020/2/19 16:26
 **/
public interface UserRepository extends JpaRepository<User, Integer> {


    /**
     * 检查是否已经存在phone
     */
    @Query(value = "select count(*) from user where phone=?1", nativeQuery = true)
    long checkPhoneIsExist(String phone);


    /**
     * 用户登录验证
     */
    User findByPhoneAndPassword(String phone, String password);


    /**
     * 修改用户状态
     */
    @Modifying
    @Query("update User as u set u.status =?2 where u.userId=?1")
    int updateUserStatus(Integer userId, Integer status);



}
