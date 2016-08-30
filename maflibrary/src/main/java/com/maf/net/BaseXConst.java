package com.maf.net;

/**
 * Created by ZGMAO on 2016/3/28.
 * 网络请求基本变量定义
 */
public class BaseXConst {
    /**
     * 登录成功一般会有token值，这里定义一个测试的token值
     */
    public static String token = "";
    /**
     * 手机接口地址
     */
    public static final String MOBILE_ADDRESS = "https://192.168.1.205/TestDataServer/";
    /**
     * 网络请求超时
     */
    public static final String XAPI_ERROR_TIME_OUT = "error_time_out";
    /**
     * 网络请求网络异常
     */
    public static final String XAPI_ERROR_NET_ERROR = "error_net_error";
}
