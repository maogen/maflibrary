package com.maf.request;

import android.os.Handler;
import android.os.Message;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.maf.git.GsonUtils;
import com.maf.request.request_base.ProtocolConfigure;
import com.maf.request.request_base.RequestCallBack;
import com.maf.request.request_base.RequestFileThread;
import com.maf.request.request_base.RequestThread;
import com.maf.utils.Lg;

import java.io.File;
import java.util.Map;

/**
 * 项目名称：RentalRoom
 * 类描述：使用HttpUrlConnection实现的网络请求工具，提供post、get请求方法
 * 创建人：zgmao
 * 创建时间：2017/6/1
 * 修改人：zgmao
 * 修改时间：2017/6/1
 * 修改备注：
 * Created by zgmao on 2017/6/1.
 */

public class RequestUtil {
    // 请求返回之后消息处理
    private Handler mHandler;
    //使用者传递的回调接口
    private RequestCallBack mCallBack;

    /**
     * 初始化相关变量
     */
    private void init(RequestCallBack _callBack) {
        this.mCallBack = _callBack;
        if (null == mHandler) {
            mHandler = new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    switch (msg.what) {
                        case ProtocolConfigure.REQUEST_SUCCESS_MSG:
                            if (mCallBack != null) {
                                Object obj = msg.obj;
                                String data = "";
                                if (null != obj) {
                                    data = obj.toString();
                                    JsonObject jsonObject = GsonUtils.stringToGson(data, new TypeToken<JsonObject>() {
                                    });
                                    String errorCode = jsonObject.get("errorCode").getAsString();
                                    String msg2 = jsonObject.get("msg").getAsString();
                                    Lg.e("errorCode" + errorCode + ";msg:" + msg2);
                                }
                                mCallBack.onSuccess(data);
                                mCallBack.onFinish();
                            }
                            break;
                        case ProtocolConfigure.REQUEST_ERROR_MSG:
                            if (mCallBack != null) {
                                Object obj = msg.obj;
                                String data = "";
                                if (null != obj) {
                                    data = obj.toString();
                                }
                                mCallBack.onError(msg.arg1, data);
                                mCallBack.onFinish();
                            }
                            break;
                        default:
                            break;
                    }
                    return false;
                }
            });
        }
    }

    /**
     * post请求
     *
     * @param action    action
     * @param map       map
     * @param _callBack _callBack
     */
    public void post(String action, Map<String, String> map, RequestCallBack _callBack) {
        // 赋值回调接口
//        callBack = _callBack;
        // 初始化相关变量
        init(_callBack);
        RequestThread thread = new RequestThread(action, map, mHandler);
        thread.setMethod("POST");
        thread.start();
    }

    /**
     * post请求
     *
     * @param action    action
     * @param object    object
     * @param _callBack _callBack
     */
    public void post(String action, Object object, RequestCallBack _callBack) {
        // 赋值回调接口
//        callBack = _callBack;
        // 初始化相关变量
        init(_callBack);
        RequestThread thread = new RequestThread(action, object, mHandler);
        thread.setMethod("POST");
        thread.start();
    }

    /**
     * get请求
     *
     * @param action    action
     * @param map       map
     * @param _callBack _callBack
     */
    public void get(String action, Map<String, String> map, RequestCallBack _callBack) {
        // 赋值回调接口
//        callBack = _callBack;
        // 初始化相关变量
        init(_callBack);
        RequestThread thread = new RequestThread(action, map, mHandler);
        thread.setMethod("GET");
        thread.start();
    }

    /**
     * 上传图片
     *
     * @param action    action
     * @param map       map
     * @param file      file
     * @param _callBack _callBack
     */
    public void postFile(String action, Map<String, String> map, String file, RequestCallBack _callBack) {
        // 赋值回调接口
//        callBack = _callBack;
        // 初始化相关变量
        init(_callBack);
        RequestFileThread thread = new RequestFileThread(action, map, mHandler);
        thread.setFile(new File(file));
        thread.setMethod("POST");
        thread.start();
    }
}
