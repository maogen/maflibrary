package com.maf.net;

import android.os.Handler;
import android.os.Message;

import com.maf.utils.LogUtils;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;

import java.util.Map;

import javax.net.ssl.SSLSocketFactory;

/**
 * Created by mzg on 2016/5/30.
 * 网络请求工具
 */
public class MServiceUtils {
    private static Handler handler;

    /**
     * 设置token值，一般在第一次登录后设置
     *
     * @param token
     */
    public static void setToken(String token) {
        MBaseService.setToken(token);
    }

    /**
     * 设置ssl证书
     *
     * @param sslSocketFactory ssl证书
     */
    public static void setSSL(SSLSocketFactory sslSocketFactory) {
        MBaseService.setSslSocketFactory(sslSocketFactory);
    }

    /**
     * 结果回调处理
     */
    private static Callback.CommonCallback<String> callback = new Callback.CommonCallback<String>() {

        @Override
        public void onSuccess(String result) {
            // 请求成功，发送结果
            Message message = Message.obtain();
            message.what = MBaseConst.HANDLER_SERVICE_RESULT;
            message.obj = result;
            handler.sendMessage(message);
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
            ex.printStackTrace();
            Message message = Message.obtain();
            message.what = MBaseConst.HANDLER_SERVICE_ERROR;
            if (ex instanceof HttpException) { // 网络错误
                HttpException httpEx = (HttpException) ex;
                int responseCode = httpEx.getCode();
                String responseMsg = httpEx.getMessage();
                String result = httpEx.getResult();
                LogUtils.d("responseCode：" + responseCode + ";errorResult:" + responseMsg);
                LogUtils.d("result：" + result);
                message.arg1 = responseCode;
                message.obj = result;
            } else { // 其他错误
                message.obj = "未知错误";
            }
            handler.sendMessage(message);
        }

        @Override
        public void onCancelled(CancelledException cex) {
            LogUtils.d("请求取消");
            cex.printStackTrace();
            Message message = Message.obtain();
            message.what = MBaseConst.HANDLER_SERVICE_ERROR;
            message.obj = "请求取消";
            handler.sendMessage(message);
        }

        @Override
        public void onFinished() {
            LogUtils.d("请求结束");
            Message message = Message.obtain();
            message.what = MBaseConst.HANDLER_SERVICE_FINISH;
            handler.sendMessage(message);
            destroy();
        }

    };

    /**
     * 开始请求，默认地址是手机后台服务器地址
     *
     * @param _handler
     * @param url
     * @param params
     */
    public static Callback.Cancelable post(Handler _handler, String url, Map<String, String> params) {
        handler = _handler;
        Callback.Cancelable cancelable = MBaseService.post(url, params, callback);
        return cancelable;
    }

    /**
     * 开始请求，默认地址是手机后台服务器地址
     *
     * @param _handler
     * @param url
     * @param params
     */
    public static Callback.Cancelable get(Handler _handler, String url, Map<String, String> params) {
        handler = _handler;
        Callback.Cancelable cancelable = MBaseService.get(url, params, callback);
        return cancelable;
    }

    public static void destroy() {
        handler = null;
    }
}
