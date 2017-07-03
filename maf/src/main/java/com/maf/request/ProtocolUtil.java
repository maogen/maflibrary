package com.maf.request;

import android.os.Handler;
import android.os.Message;

import com.maf.git.GsonUtils;
import com.maf.request.request_base.ProtocolConfigure;
import com.maf.request.request_base.RequestCallBack;
import com.maf.utils.Lg;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * 项目名称：RentalRoom
 * 类描述：使用OkHttp框架实现的网络请求，提供post（一般post请求）、postForm（表单post请求）方法
 * 创建人：zgmao
 * 创建时间：2017/6/1
 * 修改人：zgmao
 * 修改时间：2017/6/1
 * 修改备注：
 * Created by zgmao on 2017/6/1.
 */

public class ProtocolUtil {
    //使用者传递的回调接口
    private RequestCallBack callBack;
    // 请求返回之后消息处理
    private Handler mHandler;
    private OkHttpClient okHttpClient;
    // 实现自己的请求回调
    private MyCallback myCallback;

    /**
     * 初始化httpClient和handler
     */
    private void initHttpClient() {
        if (null == okHttpClient) {
            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS).build();
            mHandler = new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    switch (msg.what) {
                        case ProtocolConfigure.REQUEST_SUCCESS_MSG:
                            if (callBack != null) {
                                callBack.onSuccess(msg.obj.toString());
                                callBack.onFinish();
                            }
                            break;
                        case ProtocolConfigure.REQUEST_ERROR_MSG:
                            if (callBack != null) {
                                callBack.onError(msg.arg1, msg.obj.toString());
                                callBack.onFinish();
                            }
                            break;
                        default:
                            break;
                    }
                    return false;
                }
            });
            myCallback = new MyCallback();
        }
    }

    /**
     * 解析接口返回信息
     */
    class MyCallback implements Callback {
        @Override
        public void onFailure(Call call, IOException e) {
            Message message = Message.obtain();
            message.what = ProtocolConfigure.REQUEST_ERROR_MSG;
            if (e instanceof SocketTimeoutException) {
                message.arg1 = ProtocolConfigure.ERROR_TIME_OUT;
            } else if (e instanceof UnknownHostException) {
                message.arg1 = ProtocolConfigure.ERROR_NET_ERROR;
            } else {
                message.arg1 = ProtocolConfigure.ERROR;
            }
            message.obj = e;
            mHandler.sendMessage(message);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            if (response.isSuccessful()) {
                Message message = Message.obtain();
                message.what = ProtocolConfigure.REQUEST_SUCCESS_MSG;
                message.obj = response.body().string();
                mHandler.sendMessage(message);
            } else {
                Message message = Message.obtain();
                message.what = ProtocolConfigure.REQUEST_ERROR_MSG;
                message.obj = response.message();
                mHandler.sendMessage(message);
            }
        }
    }

    /**
     * Post请求，按照表单提交的方式，上传参数
     *
     * @param action    action
     * @param map       参数
     * @param _callBack
     */
    public void postForm(String action, Map<String, String> map, RequestCallBack _callBack) {
        callBack = _callBack;
        // 构造请求Body
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            bodyBuilder.add(entry.getKey(), entry.getValue());
        }
        RequestBody requestBody = bodyBuilder.build();
        // 构造Request
        Request request = new Request.Builder()
                .url(action)
                .method("POST", requestBody).build();
        // 执行
        initHttpClient();
        okHttpClient.newCall(request).enqueue(myCallback);
    }

    public void post(String action, Map<String, String> map, RequestCallBack _callBack) {
        callBack = _callBack;

        MediaType MEDIA_TYPE_APPLICATION
                = MediaType.parse("application/json; charset=utf-8");
        String json;
        // 当前参数不是字符串，是其他对象，将对象转成实体类
        json = GsonUtils.gsonToString(map);
//        String token = SPUtils.get(RentalApplication._application, ApplicationConfigure.SP_KEY_TOKEN, "").toString();
        Lg.e("url:" + action);
//        Lg.e("token:" + token);
        Lg.e("params:" + json);
        RequestBody body = RequestBody.create(MEDIA_TYPE_APPLICATION, json);
        Request request = new Request.Builder()
                .addHeader("Content-Type", "application/json")
//                .addHeader("token", token)
                .url(action)
                .post(body)
                .build();
        // 执行
        initHttpClient();
        okHttpClient.newCall(request).enqueue(myCallback);
    }

    /**
     * POST提交Json数据
     *
     * @param action    action
     * @param content   参数，如果该参数不是String内容，则会被转成json字符串
     * @param _callBack 回调
     */
    public void post(String action, Object content, RequestCallBack _callBack) {
        callBack = _callBack;

        MediaType MEDIA_TYPE_APPLICATION
                = MediaType.parse("application/json; charset=utf-8");
        String json;
        if (content instanceof String) {
            json = content.toString();
        } else {
            // 当前参数不是字符串，是其他对象，将对象转成实体类
            json = GsonUtils.gsonToString(content);
        }
//        String token = SPUtils.get(BaseApplication._application, ApplicationConfigure.SP_KEY_TOKEN, "").toString();

        RequestBody body = RequestBody.create(MEDIA_TYPE_APPLICATION, json);
        Request request = new Request.Builder()
                .addHeader("Content-Type", "application/json")
//                .addHeader("token", token)
                .url(action)
                .post(body)
                .build();
        // 执行
        initHttpClient();
        okHttpClient.newCall(request).enqueue(myCallback);
    }

    /**
     * @param action
     * @param file
     * @param _callBack
     */
    public void updateFile(String action, File file, RequestCallBack _callBack) {
        callBack = _callBack;
//        String token = SPUtils.get(RentalApplication._application, ApplicationConfigure.SP_KEY_TOKEN, "").toString();

        // 表单类型
        String imageType = "multipart/form-data";
        // 图片后缀名
        String name = file.getName();
        String type = name.substring(name.lastIndexOf(".") + 1);

        RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", name, fileBody)
                .addFormDataPart("imagetype", imageType)
                .addFormDataPart("type", type)
                .addFormDataPart("originalName", name)
                .build();
        Request request = new Request.Builder()
                .url(action)
//                .addHeader("token", token)
                .post(requestBody)
                .build();
        // 执行
        initHttpClient();
        okHttpClient.newCall(request).enqueue(myCallback);
    }
}
