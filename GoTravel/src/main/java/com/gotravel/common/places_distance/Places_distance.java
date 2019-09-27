package com.gotravel.common.places_distance;

import com.gotravel.model.Place;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName:Places_distance
 * @Description:TODO 根据经纬度计算两点之间的距离
 * @Author:chenyx
 * @Date:Create in  2019/9/20 15:46
 **/
@Slf4j
public class Places_distance {

    private static final double EARTH_RADIUS = 6378137;// 赤道半径(单位m)


    /**
     * 转化为弧度(rad)
     */
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }


    /**
     * @Title GetDistance
     * @Description: TODO 基于googleMap中的算法得到两经纬度之间的距离,计算精度与谷歌地图的距离精度差不多，相差范围在0.2米以下（单位m）
     * @param lon1 第一点的经度
     * @param lat1 第一点的纬度
     * @param lon2 第二点的经度
     * @param lat2 第二点的纬度
     * @return double 返回的距离，单位km
     * @Author: chenyx
     * @Date: 2019/9/20  15:47
     **/
    public static double getDistance(double lon1, double lat1, double lon2, double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lon1) - rad(lon2);
        double s = 2 * Math.asin(Math.sqrt(
                Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000000;
        return s;
    }


    /**
     * @Title getFitDistancePlaces
     * @Description: TODO 匹配符合距离范围的景点
     * @param placesList 景点Lsit
     * @param distance 范围
     * @param lon 经度
     * @param lat 维度
     * @return java.util.List<java.util.Map < java.lang.String , java.lang.Object>>
     * @Author: chenyx
     * @Date: 2019/9/27  22:44
     **/
    public static List<Map<String, Object>> getFitDistancePlaces(List<Place> placesList, int distance, double lon, double lat) {

        List<Map<String, Object>> placeList = new ArrayList<>(); //转载符合的范围的景点
        String str = ""; //日志用

        for (Place place : placesList) {
            String Longitude_latitude = place.getLongitude_latitude();

            if (null != Longitude_latitude && !Longitude_latitude.equals("") && !Longitude_latitude.equals("null") && Longitude_latitude.length() != 0 && Longitude_latitude != "") {//经纬度不为空才进行判断
                String[] lal = Longitude_latitude.trim().split(",");//分解经纬度字符串
                Double longitude = Double.parseDouble(lal[1]);  //经度
                Double latitude = Double.parseDouble(lal[0]); //维度

                //调用两经纬度之间的距离方法
                Double sure_distance = getDistance(lon, lat, longitude, latitude);
                int int_distance = Integer.parseInt(new java.text.DecimalFormat("0").format(sure_distance));//double转为int

                if (int_distance <= distance) { //地点在指定的范围
                    str += " " + int_distance;
                    Map<String, Object> map = new HashMap<>();
                    map.put("distance", int_distance);
                    map.put("place", place);
                    placeList.add(map);
                }
            }
        }
        log.info("----符合的景点的距离-----" + str);
        return placeList;
    }


}



