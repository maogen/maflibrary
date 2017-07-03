package com.maf.request.request_base;

import android.os.Handler;
import android.os.Message;

import com.maf.utils.Lg;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
 * 项目名称：RentalRoom
 * 类描述：上传图片线程
 * 创建人：zgmao
 * 创建时间：2017/6/2
 * 修改人：zgmao
 * 修改时间：2017/6/2
 * 修改备注：
 * Created by zgmao on 2017/6/2.
 */

public class RequestFileThread extends Thread {
    private Map<String, String> map;
    private String action;
    private Handler mHandler;

    private HttpURLConnection urlConnection = null;
    private String method = "POST";
    private static final String CHARSET = "utf-8"; // 设置编码

    private static final String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
    //    private static final String BOUNDARY = "*****"; // 边界标识 随机生成
    private static final String PREFIX = "--";
    private static final String LINE_END = "\r\n";
    private static final String CONTENT_TYPE = "multipart/form-data"; // 内容类型

    private File file;

    public RequestFileThread(String action, Map<String, String> map, Handler mHandler) {
        this.map = map;
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

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public void run() {
        // 消息
        Message message = Message.obtain();
        message.what = ProtocolConfigure.REQUEST_ERROR_MSG;
        message.arg1 = ProtocolConfigure.ERROR;
        message.obj = "请求失败";
        // 拼接参数
        Lg.e("url:" + action);
        try {
            // 构造UrlCon
            URL url = new URL(action);
            urlConnection = (HttpURLConnection) url.openConnection();
            initPostParams();// 请求相关设置
//            String token = SPUtils.get(RentalApplication._application, ApplicationConfigure.SP_KEY_TOKEN, "").toString();
//            Lg.e("token:" + token);
//            urlConnection.setRequestProperty("token", token);
            // 发送请求参数
            DataOutputStream dos = new DataOutputStream(urlConnection.getOutputStream());
            StringBuilder sb = new StringBuilder();
            sb.append(PREFIX);
            sb.append(BOUNDARY);
            sb.append(LINE_END);

            //以下是用于上传参数
            if (map != null && map.size() > 0) {
                Iterator<String> it = map.keySet().iterator();
                while (it.hasNext()) {
                    String key = it.next();
                    String value = map.get(key);
                    sb.append("Content-Disposition: form-data; name=\"")
                            .append(key)
                            .append("\"")
                            .append(LINE_END);
                    sb.append(LINE_END);
                    sb.append(value);
                    sb.append(LINE_END);
                    sb.append(PREFIX);
                    sb.append(BOUNDARY);
                    sb.append(LINE_END);
                }
            }
            /**
             * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
             * filename是文件的名字，包含后缀名的 比如:abc.png
             */
            sb.append("Content-Disposition: form-data; name=\"file\"; filename=\"" +
                    file.getName() + "\"" + LINE_END);
            sb.append("Content-Type: image/jpg; charset=" + CHARSET + LINE_END); // 这里配置的Content-type很重要的 ，用于服务器端辨别文件的类型的
            sb.append(LINE_END);
//            Lg.e(file.getName());
            Lg.e(sb.toString());
            dos.write(sb.toString().getBytes());
            /**上传文件*/
            InputStream is = new FileInputStream(file);
            byte[] bytes = new byte[1024];
            int len;
            while ((len = is.read(bytes)) != -1) {
                dos.write(bytes, 0, len);
            }
            is.close();

            dos.write(LINE_END.getBytes());
            byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
            dos.write(end_data);
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
        urlConnection.setRequestProperty("Connection", "keep-alive");
        urlConnection.setRequestProperty("Charset", CHARSET);
        urlConnection.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
                + BOUNDARY);
        urlConnection.setRequestProperty("Accept", "*/*");
    }

    /**
     * 将输入流转换成字符串
     *
     * @param is 从网络获取的输入流
     * @return
     */
    private static String streamToString(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
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
