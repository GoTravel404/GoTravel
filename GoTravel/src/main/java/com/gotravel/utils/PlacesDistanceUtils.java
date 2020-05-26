package com.gotravel.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gotravel.entity.Place;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.logging.log4j.util.Strings;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName:Places_distance
 * @Description:TODO 根据经纬度计算两点之间的距离
 * @Author:chenyx
 * @Date:Create in  2019/9/20 15:46
 **/
@Slf4j
public class PlacesDistanceUtils {

    private static final double EARTH_RADIUS = 6378137;// 赤道半径(单位m)

    private final static String key = "5209fc688cc25ebd2cbf6b0b5cdaa8af";

    private final static int type = 0;//直线距离

    private static double distance = 9999.99;//默认直线距离(公里)


    /**
     * 转化为弧度(rad)
     */
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }


    /**
     * @Title getDistance_1
     * @Description: 基于googleMap中的算法得到两经纬度之间的距离, 计算精度与谷歌地图的距离精度差不多，相差范围在0.2米以下（单位m）
     * @param lon1 第一点的经度
     * @param lat1 第一点的纬度
     * @param lon2 第二点的经度
     * @param lat2 第二点的纬度
     * @return double 返回的距离，单位km
     * @Author: chenyx
     * @Date: 2019/9/20  15:47
     **/
    public static double getDistance_1(double lon1, double lat1, double lon2, double lat2) {
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
     * @Title getDistance_2
     * @Description: 计算得到两经纬度之间的距离
     * @param longitude1
     * @param latitude1
     * @param longitude2
     * @param latitude2
     * @Return: double
     * @Author: chenyx
     * @Date: 2020/5/26 19:07
     **/
    public static double getDistance_2(double longitude1, double latitude1, double longitude2, double latitude2) {

        double lon1 = (Math.PI / 180) * longitude1;
        double lon2 = (Math.PI / 180) * longitude2;
        double lat1 = (Math.PI / 180) * latitude1;
        double lat2 = (Math.PI / 180) * latitude2;

        // 地球半径
        double R = 6371;

        // 两点间距离 km
        double d = Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1))
                * R;

        //四舍五入，保留一位小数
        BigDecimal b = new BigDecimal(d);

        d = b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();

        return d;
    }


    /**
     * @Title getDistanceFromLaL
     * @Description: 调用高德API计算两个经纬度的距离
     * 参考：https://lbs.amap.com/api/webservice/guide/api/direction
     * @param origins
     * @param destination
     * @Return: double
     * @Author: chenyx
     * @Date: 2020/5/26 14:51
     **/
    public static double getDistanceFromLaL(String origins, String destination) {

        try {
            String url = "https://restapi.amap.com/v3/distance?origins=" + origins + "&destination=" + destination + "&output=JSON&key=" + key + "&type=" + type;

            // log.info("【url】: {}", url);

            String result = sendGet(url);

            JSONObject jsonObject = JSON.parseObject(result);


            //成功status返回1
            if (jsonObject.getString("status").equals("1")) {

                JSONArray jsonArray = jsonObject.getJSONArray("results");

                JSONObject result_1 = (JSONObject) jsonArray.get(0);

                String result_1_distance = result_1.getString("distance");

                //四舍五入，保留两位小数
                BigDecimal b = BigDecimal.valueOf(Double.parseDouble(result_1_distance) / 1000);

                return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();//公里

            }

            return distance;

        } catch (Exception e) {

            e.printStackTrace();
            log.error("【调用高德API计算两个经纬度的距离异常】");

            return distance;
        }


    }


    /**
     * @Title getDistance
     * @Description: 计算景点到某经纬度的距离
     * @param place
     * @param lon
     * @param lat
     * @Return: double
     * @Author: chenyx
     * @Date: 2020/5/26 21:13
     **/
    public static double getDistance(Place place, double lon, double lat) {

        String longitude_latitude = place.getLongitude_latitude();

        //经纬度不为空
        if (!Strings.isEmpty(longitude_latitude) && !longitude_latitude.equals("null")) {

            String origins = lon + "," + lat;

            String destination = convertLALPosition(longitude_latitude);

            return getDistanceFromLaL(origins, destination);

        }

        return distance;

    }


    /**
     * @Title getFitDistancePlaces
     * @Description: 匹配符合距离范围的景点
     * @param placesList 景点
     * @param distance 范围
     * @param lon 经度
     * @param lat 维度
     * @Return: List<Place>
     * @Author: chenyx
     * @Date: 2020/5/26 19:05
     **/
    public static List<Place> getFitDistancePlaces(List<Place> placesList, int distance, double lon, double lat) {

        List<Place> resultPlaceList = new ArrayList<>(); //转载符合的范围的景点

        for (Place place : placesList) {
            String longitude_latitude = place.getLongitude_latitude();

            //经纬度不为空
            if (!Strings.isEmpty(longitude_latitude) && !longitude_latitude.equals("null")) {

                try {

                    String[] lal = longitude_latitude.trim().split(",");//分解经纬度字符串
                    double longitude = Double.parseDouble(lal[1]);  //经度
                    double latitude = Double.parseDouble(lal[0]); //维度

                    //调用两经纬度之间的距离方法
                    double sure_distance = getDistance_2(lon, lat, longitude, latitude);
                    //int int_distance = Integer.parseInt(new java.text.DecimalFormat("0").format(sure_distance));//double转为int

                    if (sure_distance <= distance) { //地点在指定的范围
                        //log.info("sure_distance,{}",sure_distance);

                        resultPlaceList.add(place);
                    }

                } catch (Exception e) {

                    log.info("【匹配符合距离范围的景点】：经纬度转换失败 Longitude_latitude={}", longitude_latitude);
                }

            }

        }

        return resultPlaceList;
    }


    /**
     * @Title sendGet
     * @Description: get方法
     * @param urlParam
     * @Return: java.lang.String
     * @Author: chenyx
     * @Date: 2020/5/26 16:11
     **/
    public static String sendGet(String urlParam) throws HttpException, IOException {
        // 创建httpClient实例对象
        HttpClient httpClient = new HttpClient();
        // 设置httpClient连接主机服务器超时时间：15000毫秒
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(15000);
        // 创建GET请求方法实例对象
        GetMethod getMethod = new GetMethod(urlParam);
        // 设置GET请求超时时间
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);
        getMethod.addRequestHeader("Content-Type", "application/json");

        httpClient.executeMethod(getMethod);

        String result = getMethod.getResponseBodyAsString();
        getMethod.releaseConnection();
        return result;
    }


    /**
     * @Title convertLALPosition
     * @Description: 经纬度调换位置 (维度，经度)==>(经度，维度)
     * @param lal
     * @Return: java.lang.String
     * @Author: chenyx
     * @Date: 2020/3/3 21:42
     **/
    public static String convertLALPosition(String lal) {

        String[] strings = lal.split(",");
        return strings[1] + "," + strings[0];
    }

}



