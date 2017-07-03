package com.maf.request.request_base;

/**
 * 项目名称：RentalRoom
 * 类描述：请求相关配置
 * 创建人：zgmao
 * 创建时间：2017/6/2
 * 修改人：zgmao
 * 修改时间：2017/6/2
 * 修改备注：
 * Created by zgmao on 2017/6/2.
 */

public class ProtocolConfigure {
    /**
     * 请求超时错误
     */
    public static final int ERROR_TIME_OUT = 600;
    /**
     * 网络请求网络异常
     */
    public static final int ERROR_NET_ERROR = 601;
    /**
     * 其他请求错误
     */
    public static final int ERROR = 700;
    // 接口请求失败消息
    public static final int REQUEST_ERROR_MSG = 1000;
    // 接口请求成功消息
    public static final int REQUEST_SUCCESS_MSG = 1001;
    // 超时时间
    public static final int timeOut = 10 * 1000;
}
