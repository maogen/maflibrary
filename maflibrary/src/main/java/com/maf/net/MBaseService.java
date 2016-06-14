package com.maf.net;

import com.maf.utils.LogUtils;
import com.maf.utils.StringUtils;

import java.util.Map;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import javax.net.ssl.SSLSocketFactory;

/**
 * Created by mzg on 2016/5/30.
 * 使用XUtils进行简单的网络访问，需要网络权限
 */
public class MBaseService {
    private static String token;//部分网络请求需要设置token值
    private static SSLSocketFactory sslSocketFactory;

    /**
     * 设置token值
     *
     * @param _token token值
     */
    public static void setToken(String _token) {
        token = _token;
    }

    /**
     * 设置证书
     *
     * @param _sslSocketFactory
     */
    public static void setSslSocketFactory(SSLSocketFactory _sslSocketFactory) {
        sslSocketFactory = _sslSocketFactory;
    }

    /**
     * 使用xUtils执行post请求
     *
     * @param url      请求的url
     * @param params   请求的参数
     * @param callback 结果的回调
     * @param <T>      回调类型
     * @return 返回Callback.Cancelable
     */
    public static <T> Callback.Cancelable post(String url, Map<String, String> params, Callback.CommonCallback<T> callback) {
        LogUtils.d("请求地址：" + url);
        RequestParams requestParams = new RequestParams(url);
        if (null != params) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                LogUtils.d(entry.getKey() + ";" + entry.getValue());
                requestParams.addParameter(entry.getKey(), entry.getValue());
            }
        } else {
            LogUtils.d("请求参数为空");
        }
        if (StringUtils.isNotEmpty(token)) {
            LogUtils.d("token:" + token);
            requestParams.setHeader("Authorization", token);
        }
        if (sslSocketFactory != null) {
            requestParams.setSslSocketFactory(sslSocketFactory);
        }
        Callback.Cancelable cancelable = x.http().post(requestParams, callback);
        return cancelable;
    }

    /**
     * Get请求
     *
     * @param url      请求地址
     * @param params   参数
     * @param callback 回调接口
     * @param <T>
     */
    public static <T> Callback.Cancelable get(String url, Map<String, String> params, Callback.CommonCallback<T> callback) {
        LogUtils.d("请求地址：" + url);
        RequestParams requestParams = new RequestParams(url);
        if (null != params) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                LogUtils.d(entry.getKey() + ";" + entry.getValue());
                requestParams.addQueryStringParameter(entry.getKey(), entry.getValue());
            }
        } else {
            LogUtils.d("请求参数为空");
        }
        if (StringUtils.isNotEmpty(token)) {
            LogUtils.d("token:" + token);
            requestParams.setHeader("Authorization", token);
        }
        if (sslSocketFactory != null) {
            requestParams.setSslSocketFactory(sslSocketFactory);
        }
        Callback.Cancelable cancelable = x.http().get(requestParams, callback);
        return cancelable;
    }
}
