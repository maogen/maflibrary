package com.maf.request.request_base;

/**
 * 项目名称：RentalRoom
 * 类描述：网络请求结果回调
 * 创建人：zgmao
 * 创建时间：2017/6/1
 * 修改人：zgmao
 * 修改时间：2017/6/1
 * 修改备注：
 * Created by zgmao on 2017/6/1.
 */

public interface RequestCallBack {
    void onSuccess(String data);

    void onError(int errorCode, String errorMsg);

    void onFinish();
}
