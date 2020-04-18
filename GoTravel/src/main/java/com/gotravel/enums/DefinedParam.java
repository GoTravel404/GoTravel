package com.gotravel.enums;

/**
 * @Name: DefinedParam
 * @Description:TODO
 * @Author:chenyx
 * @Date: 2020/3/7 18:08
 **/
public class DefinedParam {

    /**
     * Redis缓存的所有景点List的key值
     */
    public final static String REDIS_KEY_AllPlaces = "AllPlaces";


    /**
     * Redis缓存的所有景点的Label的key值
     */
    public final static String REDIS_KEY_AllPlacesLabel = "AllPlacesLabel";


    /**
     * Redis缓存的景点评论的点赞数的key值
     */
    public final static String REDIS_KEY_PlaceCommentPraise = "PlaceCommentPraise";


    /**
     * Redis缓存的用户点赞的景点评论的key值
     */
    public final static String REDIS_KEY_UserPraisePlaceComment = "UserPraisePlaceComment";


    /**
     * place_comment的id前缀
     */
    public final static String PLACE_COMMENT_PREFIX = "pc";


    /**
     * user_praise_place_comment的id前缀
     */
    public final static String USER_PRAISE_PLACE_COMMENT_PREFIX = "uppc";


    /**
     * 返回景点的前置景点评论数量
     */
    public final static int PLACE_COMMENT_QUANTITY = 3;

    /**
     * 返回分页每页景点的景点评论数量
     */
    public final static int PLACE_COMMENT_SIZE = 32;


}
