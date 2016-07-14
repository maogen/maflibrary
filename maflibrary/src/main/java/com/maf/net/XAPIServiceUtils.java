package com.maf.net;

import com.maf.utils.LogUtils;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;

import java.util.Map;

/**
 * Created by ZGMAO on 2016/3/28.
 * 手机登录请求工具
 */
public class XAPIServiceUtils {

    /**
     * 设置token值，在第一次登录后设置
     *
     * @param token
     */
    public static void setToken(String token) {
        XBaseAPIUtils.setToken(token);
    }

    /**
     * 开始请求，默认地址是手机后台服务器地址
     *
     * @param listener
     * @param action
     * @param value
     */
    public static Callback.Cancelable post(final XAPIServiceListener listener, String action, Map<String, String> value) {
        Callback.Cancelable cancelable = XBaseAPIUtils.post(BaseXConst.MOBILE_ADDRESS, action, value, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                // 请求成功，发送结果
                listener.onSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                String error;
                ex.printStackTrace();
                if (ex instanceof HttpException) { // 网络错误
                    HttpException httpEx = (HttpException) ex;
                    int responseCode = httpEx.getCode();
                    String responseMsg = httpEx.getMessage();
                    LogUtils.d("responseCode：" + responseCode + ";errorResult:" + responseMsg);
                    error = responseMsg;
                } else { // 其他错误
                    error = "未知错误";
                }
                listener.onError(error);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                cex.printStackTrace();
                listener.onError("请求被取消");
            }

            @Override
            public void onFinished() {
                listener.onFinished();
            }
        });
        return cancelable;
    }
}
