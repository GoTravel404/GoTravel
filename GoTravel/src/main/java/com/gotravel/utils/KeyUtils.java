package com.gotravel.utils;

import java.util.Random;

/**
 * @Name: KeyUtils
 * @Description: 生成主键
 * @Author:chenyx
 * @Date: 2020/3/31 20:39
 **/
public class KeyUtils {


    /**
     * 生成唯一主键
     * 格式：时间戳+随机数
     * @return
     */
    public static synchronized String getUniqueKry() {

        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;

        return System.currentTimeMillis() + String.valueOf(number);
    }



    public static void main(String[] args) {
        System.out.println(getUniqueKry());
    }


}
