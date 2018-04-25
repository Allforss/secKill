package com.sukidesu.seckill.base.common.constants;

/**
 * @author xudong.cheng
 * @date 2018/1/18 下午4:00
 */
public class RedisConstants {

    /**
     * session key
     * TODO baseweb 改为应用名称
     */
    public static String SPRING_SESSION_MY = "seckill:session:";

    public static int SPRING_SESSION_EXPIRE = 1200;

    /**
     * menu cache key
     * TODO baseweb 改为应用名称
     */
    public static String CURRENT_USER_MENU_KEY = "seckill:current:menu";

    /**
     * user type key
     * TODO baseweb 改为应用名称
     */
    public static String CURRENT_USER_TYPE = "current:type";

    public static String LOG_OPERATE_SET_KEY= "seckill:log:operate:set";



}