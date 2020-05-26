package com.gotravel.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @Name: Icon
 * @Description:
 * @Author:chenyx
 * @Date: 2020/5/12 11:45
 **/
@Data
@Entity
@Table(name = "icon")
public class Icon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 路径
     */
    private String route;

    private Date createTime;

    private Date updateTime;


}
