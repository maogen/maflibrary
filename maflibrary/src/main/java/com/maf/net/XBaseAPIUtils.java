package com.maf.net;


import com.maf.utils.LogUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Map;

/**
 * Created by ZGMAO on 2016/3/28.
 * 网络请求基类，主要设置请求头和参数
 */
public class XBaseAPIUtils {
    private static String token = "";
//    private static boolean isRequest = false;// 是否在请求中，如果在，则无法重复请求

    /**
     * 设置token值
     *
     * @param _token
     */
    public static void setToken(String _token) {
        token = _token;
    }

    /**
     * Post请求
     *
     * @param url      请求地址
     * @param action   请求的接口名
     * @param value    参数
     * @param callback 回调接口
     * @param <T>
     */
    public static <T> Callback.Cancelable post(String url, String action, Map<String, String> value, Callback.CommonCallback<T> callback) {
        if (null == token) {
            LogUtils.d("token值为空，请设置后再请求");
            callback.onFinished();
            return null;
        }
//        if (isRequest) {
//            // 如果当前正在网络请求，不重复请求
//            LogUtils.d("上次请求未结束，继续请求网络");
//            callback.onFinished();
//            return null;
//        }
        LogUtils.d("请求地址：" + url);
        LogUtils.d("请求方法：" + action);
        RequestParams params = new RequestParams(url + action);
        if (null != value) {
            for (Map.Entry<String, String> entry : value.entrySet()) {
                LogUtils.d(entry.getKey() + ";" + entry.getValue());
                params.addParameter(entry.getKey(), entry.getValue());
            }
        } else {
            LogUtils.d("请求参数为空");
        }
        params.setHeader("Authorization", token);
        LogUtils.d("token:" + token);
//        isRequest = true;
        Callback.Cancelable cancelable = x.http().post(params, callback);
//        isRequest = false;
        return cancelable;
        // 上传文件方式
        // 有上传文件时使用multipart表单, 否则上传原始文件流.
        // params.setMultipart(true);
        // 上传文件方式 1
        // params.uploadFile = new File("/sdcard/test.txt");
        // 上传文件方式 2
        // params.addBodyParameter("uploadFile", new File("/sdcard/test.txt"));
    }

    /**
     * Get请求
     *
     * @param url      请求地址
     * @param action   请求的接口名
     * @param value    参数
     * @param callback 回调接口
     * @param <T>
     */
    public static <T> Callback.Cancelable get(String url, String action, Map<String, String> value, Callback.CommonCallback<T> callback) {
        if (null == token) {
            LogUtils.d("token值为空，请设置后再请求");
            callback.onFinished();
            return null;
        }
//        if (isRequest) {
//            // 如果当前正在网络请求，不重复请求
//            LogUtils.d("上次请求未结束，继续请求网络");
//            callback.onFinished();
//            return null;
//        }
        LogUtils.d("请求地址：" + url);
        LogUtils.d("请求方法：" + action);
        RequestParams params = new RequestParams(url + action);
        if (null != value) {
            for (Map.Entry<String, String> entry : value.entrySet()) {
                LogUtils.d(entry.getKey() + ";" + entry.getValue());
                params.addQueryStringParameter(entry.getKey(), entry.getValue());
            }
        } else {
            LogUtils.d("请求参数为空");
        }
        params.setHeader("Authorization", token);
        LogUtils.d("token:" + token);
//        isRequest = true;
        Callback.Cancelable cancelable = x.http().get(params, callback);
//        isRequest = false;
        return cancelable;
    }
}
