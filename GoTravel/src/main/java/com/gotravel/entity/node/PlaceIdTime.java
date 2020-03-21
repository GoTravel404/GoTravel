package com.gotravel.entity.node;

import lombok.Data;

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
    private long time;


    public PlaceIdTime(String place_id, long time) {
        this.place_id = place_id;
        this.time = time;
    }

}
