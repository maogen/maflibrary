package com.maf.net;

import com.maf.utils.LogUtils;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * 项目名称：maflibrary
 * 类描述：统一处理网络请求的回调
 * 创建人：mzg
 * 创建时间：2016/7/22 14:08
 * 修改人：mzg
 * 修改时间：2016/7/22 14:08
 * 修改备注：
 */
public class XAPIServiceCallBack implements Callback.CommonCallback<String> {
    private XAPIServiceListener listener;

    public XAPIServiceCallBack(XAPIServiceListener _listener) {
        this.listener = _listener;
    }

    @Override
    public void onSuccess(String result) {
        // 请求成功，发送结果
        listener.onSuccess(result);
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        ex.printStackTrace();
        String responseCode = "";
        if (ex instanceof HttpException) { // 服务器异常
            HttpException httpEx = (HttpException) ex;
            responseCode = String.valueOf(httpEx.getCode());
            String responseMsg = httpEx.getMessage();
            LogUtils.d("responseCode：" + responseCode + ";errorResult:" + responseMsg);
        } else if (ex instanceof UnknownHostException) {
            // 网络异常
            responseCode = BaseXConst.XAPI_ERROR_NET_ERROR;
        } else if (ex instanceof SocketTimeoutException) {
            // 请求超时
            responseCode = BaseXConst.XAPI_ERROR_TIME_OUT;
        }
        listener.onError(responseCode);
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
}