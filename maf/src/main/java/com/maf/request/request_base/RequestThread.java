package com.maf.request.request_base;

import android.os.Handler;
import android.os.Message;

import com.maf.git.GsonUtils;
import com.maf.utils.Lg;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * 项目名称：RentalRoom
 * 类描述：网络请求线程
 * 创建人：zgmao
 * 创建时间：2017/6/2
 * 修改人：zgmao
 * 修改时间：2017/6/2
 * 修改备注：
 * Created by zgmao on 2017/6/2.
 */

public class RequestThread extends Thread {
    private Map<String, String> map;
    private String action;
    private Handler mHandler;

    private Object object;
    private HttpURLConnection urlConnection = null;
    private String method = "POST";

    public RequestThread(String action, Map<String, String> map, Handler mHandler) {
        this.map = map;
        this.action = action;
        this.mHandler = mHandler;
    }

    public RequestThread(String action, Object object, Handler mHandler) {
        this.object = object;
        this.action = action;
        this.mHandler = mHandler;
    }

    /**
     * 设置请求方法
     *
     * @param method "POST"/"GET"
     */
    public void setMethod(String method) {
        if ("POST".equals(method) || "GET".equals(method)) {
            this.method = method;
        }
    }

    @Override
    public void run() {
        // 消息
        Message message = Message.obtain();
        message.what = ProtocolConfigure.REQUEST_ERROR_MSG;
        message.arg1 = ProtocolConfigure.ERROR;
        message.obj = "请求失败";
        // 拼接参数
        StringBuilder sBuilder = new StringBuilder(GsonUtils.gsonToString(map));
        if (null != object) {
            sBuilder = new StringBuilder(GsonUtils.gsonToString(object));
        }
        Lg.e("url:" + action);
        try {
            // 构造UrlCon
            URL url = new URL(action);
            urlConnection = (HttpURLConnection) url.openConnection();
            initPostParams();// 请求相关设置
//            String token = SPUtils.get(RentalApplication._application, ApplicationConfigure.SP_KEY_TOKEN, "").toString();
//            Lg.e("token:" + token);
//            urlConnection.setRequestProperty("token", token);
            urlConnection.setRequestProperty("Content-Type", "application/json");
//            urlConnection.setRequestProperty("Content-Length", String.valueOf(sBuilder.toString().getBytes().length));
            urlConnection.connect();
            // 发送请求参数
//            DataOutputStream dos = new DataOutputStream(urlConnection.getOutputStream());
            OutputStreamWriter dos = new OutputStreamWriter(urlConnection.getOutputStream());
            String content = sBuilder.toString();
//            String content = URLEncoder.encode(sBuilder.toString(), "utf-8");
            dos.write(content);
//            dos.write(sBuilder.toString().getBytes());
            Lg.e("url:" + action);
            Lg.e("params:" + content);
            dos.flush();
            dos.close();
            // 判断请求是否成功
            int responseCode = urlConnection.getResponseCode();
            String responseMessage = urlConnection.getResponseMessage();
            Lg.e("responseCode:" + responseCode);
            if (responseCode == 200) {
                // 获取返回的数据
                String result = streamToString(urlConnection.getInputStream());
                Lg.e("result:" + result);
                // 请求成功，设置消息
                message.what = ProtocolConfigure.REQUEST_SUCCESS_MSG;
                message.obj = result;
            } else {
                message.arg1 = responseCode;
                message.obj = responseMessage;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // 处理比较明显的异常
            e.printStackTrace();
            if (e instanceof SocketTimeoutException) {
                message.arg1 = ProtocolConfigure.ERROR_TIME_OUT;
                message.obj = "请求超时";
            } else if (e instanceof UnknownHostException) {
                message.arg1 = ProtocolConfigure.ERROR_NET_ERROR;
                message.obj = "网络或者服务器异常";
            } else {
                message.obj = "请求出错";
            }
        } finally {
            // 最终将结果发送消息给主线程
            mHandler.sendMessage(message);
            if (urlConnection != null) {
                // 关闭连接
                urlConnection.disconnect();
            }
        }
    }

    /**
     * 初始化请求设置
     */
    private void initPostParams() throws ProtocolException {
        if (urlConnection == null) {
            return;
        }
        // 设置超时
        urlConnection.setConnectTimeout(ProtocolConfigure.timeOut);
        urlConnection.setReadTimeout(ProtocolConfigure.timeOut);
        //设置请求允许输入 默认是true
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);
        // Post请求不能使用缓存
        urlConnection.setUseCaches(false);
        urlConnection.setRequestMethod(method);
        //设置本次连接是否自动处理重定向
        urlConnection.setInstanceFollowRedirects(true);
        // 配置请求Header
        urlConnection.setRequestProperty("connection", "keep-alive");
        urlConnection.setRequestProperty("Charset", "UTF-8");
        urlConnection.setRequestProperty("Accept", "application/json");
//            urlConnection.setRequestProperty("Content-Type", "multipart/form-data");
//        urlConnection.setRequestProperty("contentType", "application/x-www-form-urlencoded");
        urlConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
    }

    /**
     * 将输入流转换成字符串
     *
     * @param is 从网络获取的输入流
     * @return 输入流转换成字符串
     */
    private static String streamToString(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            baos.close();
            is.close();
            byte[] byteArray = baos.toByteArray();
            return new String(byteArray);
        } catch (Exception e) {
            return null;
        }
    }
}
