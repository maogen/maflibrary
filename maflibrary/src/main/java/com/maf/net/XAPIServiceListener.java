package com.maf.net;

/**
 * Created by ZGMAO on 2016/3/28.
 * 手机登录请求工具
 */
public interface XAPIServiceListener {
    /**
     * 请求成功回调
     *
     * @param result
     */
    void onSuccess(String result);

    /**
     * 请求失败或者请求取消回调
     *
     * @param result
     */
    void onError(String result);

    /**
     * 请求结束回调
     */
    void onFinished();

}
