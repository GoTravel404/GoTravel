package com.gotravel.entity.node;

import lombok.Data;

import java.util.Date;

/**
 *节点 {"places_id":12, "time":"2019-10-1 12:02:23"}
 **/
@Data
public class PlaceIdTime {

    /**
     * 景点Id
     */
    private String place_id;

    /**
     * 时间
     */
    private Date time;


    public PlaceIdTime(String place_id, Date time) {
        this.place_id = place_id;
        this.time = time;
    }

}
