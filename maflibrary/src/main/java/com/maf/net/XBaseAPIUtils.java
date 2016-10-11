package com.maf.net;


import com.google.gson.Gson;
import com.maf.utils.LogUtils;
import com.maf.utils.StringUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.http.body.RequestBody;
import org.xutils.http.body.StringBody;
import org.xutils.x;

import java.io.File;
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
     * @param body     body参数
     * @param value    header参数
     * @param callback 回调接口
     */
    public static Callback.Cancelable post(String url, String action, Map<String, String> body,
                                           Map<String, String> value, XAPIServiceCallBack callback) {
        String content = "";
        if (null != body) {
            content = new Gson().toJson(body);
        }
        return basePost(url, action, content, value, callback);
    }

    /**
     * 特殊Post网络请求一，可以设置任何类型Map参数
     *
     * @param url      请求地址
     * @param action   请求的接口名
     * @param body     body参数
     * @param value    参数
     * @param callback 回调接口
     */
    public static Callback.Cancelable postObject(String url, String action, Map<String, Object> body,
                                                 Map<String, String> value, XAPIServiceCallBack callback) {
        String content = "";
        if (null != body) {
            content = new Gson().toJson(body);
        }
        return basePost(url, action, content, value, callback);
    }

    /**
     * 特殊Post网络请求二，可以设置任何Object类型参数
     *
     * @param url      请求地址
     * @param action   请求的接口名
     * @param value    参数
     * @param callback 回调接口
     */
    public static Callback.Cancelable postObject(String url, String action, Object body,
                                                 Map<String, String> value, XAPIServiceCallBack callback) {
        String content = "";
        if (null != body) {
            content = new Gson().toJson(body);
        }
        return basePost(url, action, content, value, callback);
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
    public static Callback.Cancelable get(String url, String action, Map<String, String> body,
                                          Map<String, String> value, XAPIServiceCallBack callback) {
        String content = "";
        if (null != body) {
            content = new Gson().toJson(body);
            LogUtils.d("body::::" + content);
        }
        return baseGet(url, action, content, value, callback);
    }

    /**
     * 特殊get网络请求一，可以设置任何类型Map参数
     *
     * @param url      请求地址
     * @param action   请求的接口名
     * @param value    参数
     * @param callback 回调接口
     */
    public static Callback.Cancelable getObject(String url, String action, Map<String, Object> body,
                                                Map<String, String> value, XAPIServiceCallBack callback) {
        String content = "";
        if (null != body) {
            content = new Gson().toJson(body);
        }
        return baseGet(url, action, content, value, callback);
    }

    /**
     * 特殊get网络请求二，可以设置任何Object类型参数
     *
     * @param url      请求地址
     * @param action   请求的接口名
     * @param value    参数
     * @param callback 回调接口
     */
    public static Callback.Cancelable getObject(String url, String action, Object body,
                                                Map<String, String> value, XAPIServiceCallBack callback) {
        String content = "";
        if (null != body) {
            content = new Gson().toJson(body);
        }
        return baseGet(url, action, content, value, callback);
    }

    /**
     * 基础的Post请求，参数设置在body中
     *
     * @param url      请求地址
     * @param action   请求的接口名
     * @param content  body参数
     * @param value    header参数，一般常用
     * @param callback 回调接口
     */
    private static Callback.Cancelable basePost(String url, String action, String content,
                                                Map<String, String> value, XAPIServiceCallBack callback) {
        if (null == BaseXConst.token) {
            LogUtils.d("token值为空，请设置后再请求");
            callback.onFinished();
            return null;
        }
        RequestParams params = getParams(url, action, content, value);
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
    public static Callback.Cancelable baseGet(String url, String action, String body,
                                              Map<String, String> value, XAPIServiceCallBack callback) {
        if (null == BaseXConst.token) {
            LogUtils.d("token值为空，请设置后再请求");
            callback.onFinished();
            return null;
        }
        LogUtils.d("请求地址：" + url);
        LogUtils.d("请求方法：" + action);
        RequestParams params = getParams(url, action, body, value);
        Callback.Cancelable cancelable = x.http().get(params, callback);
        return cancelable;
    }

    /**
     * 下载文件
     *
     * @param downUrl  下载地址
     * @param saveFile 文件保存路径
     * @param callback 回调
     * @return
     */
    public static Callback.Cancelable loadFile(String downUrl, String saveFile, Callback.ProgressCallback<File> callback) {
        RequestParams params = new RequestParams(downUrl);
        params.setAutoRename(true);//断点下载
        params.setSaveFilePath(saveFile);
        LogUtils.d("下载文件：" + downUrl);
        LogUtils.d("文件保存地址：" + saveFile);
        Callback.Cancelable cancelable = x.http().get(params, callback);
        return cancelable;
    }
}
