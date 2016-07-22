package com.maf.net;

import org.xutils.common.Callback;

import java.util.Map;

/**
 * Created by ZGMAO on 2016/3/28.
 * 手机登录请求工具
 */
public class XAPIServiceUtils {
    private static Callback.Cancelable cancelable;

    /**
     * 公用网络请求，默认地址是手机后台服务器地址
     *
     * @param listener 网络请求监听器
     * @param action   地址
     * @param value    参数
     */
    public static Callback.Cancelable post(XAPIServiceListener listener, String action, Map<String, String> value) {
        cancelable = XBaseAPIUtils.post(BaseXConst.MOBILE_ADDRESS, action, value, new XAPIServiceCallBack(listener));
        return cancelable;
    }

    /**
     * 特殊网络请求一，可以设置不用类型参数
     *
     * @param listener 网络请求监听器
     * @param action   地址
     * @param value    参数
     */
    public static Callback.Cancelable postObject(final XAPIServiceListener listener, String action, Map<String, Object> value) {
        cancelable = XBaseAPIUtils.postObject(BaseXConst.MOBILE_ADDRESS, action, value, new XAPIServiceCallBack(listener));
        return cancelable;
    }

    /**
     * 特殊网络请求二，可以设置任何Object类型参数
     *
     * @param listener 网络请求监听器
     * @param action   地址
     * @param value    参数
     */
    public static Callback.Cancelable postObject(XAPIServiceListener listener, String action, BaseRequestBean value) {
        cancelable = XBaseAPIUtils.postObject(BaseXConst.MOBILE_ADDRESS, action, value, new XAPIServiceCallBack(listener));
        return cancelable;
    }

    /**
     * 停止请求
     */
    public static void stop() {
        if (cancelable != null) {
            cancelable.cancel();
            cancelable = null;
        }
    }
}
