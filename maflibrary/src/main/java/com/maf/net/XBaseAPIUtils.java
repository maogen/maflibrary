package com.maf.net;


import com.google.gson.Gson;
import com.maf.utils.LogUtils;
import com.maf.utils.StringUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.http.body.RequestBody;
import org.xutils.http.body.StringBody;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by ZGMAO on 2016/3/28.
 * 网络请求基类，主要设置请求头和参数
 */
public class XBaseAPIUtils {

    /**
     * 公用Post网络请求，默认地址是手机后台服务器地址
     *
     * @param url      请求地址
     * @param action   请求的接口名
     * @param value    参数
     * @param callback 回调接口
     */
    public static Callback.Cancelable post(String url, String action, Map<String, String> value, XAPIServiceCallBack callback) {
        String content = "";
        if (null != value) {
            content = new Gson().toJson(value);
        }
        return basePost(url, action, content, callback);
        // 上传文件方式
        // 有上传文件时使用multipart表单, 否则上传原始文件流.
        // params.setMultipart(true);
        // 上传文件方式 1
        // params.uploadFile = new File("/sdcard/test.txt");
        // 上传文件方式 2
        // params.addBodyParameter("uploadFile", new File("/sdcard/test.txt"));
    }

    /**
     * 特殊Post网络请求一，可以设置任何类型Map参数
     *
     * @param url      请求地址
     * @param action   请求的接口名
     * @param value    参数
     * @param callback 回调接口
     */
    public static Callback.Cancelable postObject(String url, String action, Map<String, Object> value, XAPIServiceCallBack callback) {
        String content = "";
        if (null != value) {
            content = new Gson().toJson(value);
        }
        return basePost(url, action, content, callback);
    }

    /**
     * 特殊Post网络请求二，可以设置任何Object类型参数
     *
     * @param url      请求地址
     * @param action   请求的接口名
     * @param value    参数
     * @param callback 回调接口
     */
    public static Callback.Cancelable postObject(String url, String action, BaseRequestBean value, XAPIServiceCallBack callback) {
        String content = "";
        if (null != value) {
            content = new Gson().toJson(value);
        }
        return basePost(url, action, content, callback);
    }

    /**
     * 基础的Post请求，参数是设置在header里面
     *
     * @param url      请求地址
     * @param action   请求的接口名
     * @param value    参数
     * @param callback 回调接口
     */
    public static Callback.Cancelable baseHeaderPost(String url, String action, Map<String, String> value, XAPIServiceCallBack callback) {
        if (null == BaseXConst.token) {
            LogUtils.d("token值为空，请设置后再请求");
            callback.onFinished();
            return null;
        }
        RequestParams params = getParams(url, action, null, value);
        Callback.Cancelable cancelable = x.http().post(params, callback);
        return cancelable;
    }

    /**
     * Get请求
     *
     * @param url      请求地址
     * @param action   请求的接口名
     * @param value    参数
     * @param callback 回调接口
     */
    public static Callback.Cancelable get(String url, String action, Map<String, String> value, XAPIServiceCallBack callback) {
        String content = "";
        if (null != value) {
            content = new Gson().toJson(value);
            LogUtils.d("body::::" + content);
        }
        return baseGet(url, action, content, callback);
    }

    /**
     * 基础的Post请求，参数设置在body中
     *
     * @param url      请求地址
     * @param action   请求的接口名
     * @param content  参数
     * @param callback 回调接口
     */
    private static Callback.Cancelable basePost(String url, String action, String content, XAPIServiceCallBack callback) {
        if (null == BaseXConst.token) {
            LogUtils.d("token值为空，请设置后再请求");
            callback.onFinished();
            return null;
        }
        RequestParams params = getParams(url, action, content, null);
        Callback.Cancelable cancelable = x.http().post(params, callback);
        return cancelable;
    }

    /**
     * 构造请求参数
     *
     * @return 返回构造的参数
     */
    private static RequestParams getParams(String url, String action, String body, Map<String, String> value) {
        LogUtils.d("请求地址：" + url);
        LogUtils.d("请求方法：" + action);
        RequestParams params = new RequestParams(url + action);
        params.setCacheMaxAge(1000 * 60);// 设置缓存1分钟
        // 设置header
        if (null != value) {
            for (Map.Entry<String, String> entry : value.entrySet()) {
                LogUtils.d(entry.getKey() + ":::" + entry.getValue());
                params.addParameter(entry.getKey(), entry.getValue());
            }
        } else {
            LogUtils.d("请求参数为空");
        }
        // 设置body
        if (StringUtils.isNotEmpty(body)) {
            params.setAsJsonContent(true);
//            params.addBodyParameter("storeType", 0, "");
//            params.addBodyParameter("parameter", body);
            LogUtils.d("body::::" + body);
//            params.setBodyContent(body);
            try {
                RequestBody requestBody = new StringBody(body, "utf-8");
                requestBody.setContentType("application/json");
                params.setRequestBody(requestBody);
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
        } else {
            LogUtils.d("body参数为空");
        }
        params.addHeader("Authorization", BaseXConst.token);
        LogUtils.d("token:" + BaseXConst.token);
        return params;
    }


    /**
     * 基础的Get请求
     *
     * @param url      请求地址
     * @param action   请求的接口名
     * @param value    参数
     * @param callback 回调接口
     */
    public static Callback.Cancelable baseGet(String url, String action, String value, XAPIServiceCallBack callback) {
        if (null == BaseXConst.token) {
            LogUtils.d("token值为空，请设置后再请求");
            callback.onFinished();
            return null;
        }
        LogUtils.d("请求地址：" + url);
        LogUtils.d("请求方法：" + action);
        RequestParams params = getParams(url, action, value, null);
        Callback.Cancelable cancelable = x.http().get(params, callback);
        return cancelable;
    }
}
