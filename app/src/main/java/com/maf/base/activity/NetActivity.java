package com.maf.base.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.maf.activity.BaseBackActivity;
import com.maf.base.bean.LoginBean;
import com.maf.net.MBaseConst;
import com.maf.net.MServiceUtils;
import com.maf.utils.BaseToast;
import com.maf.utils.LogUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mzg on 2016/5/30.
 * 网络测试界面
 */
public class NetActivity extends BaseBackActivity {
    private LoginBean loginBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        testLogin();
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 测试开停服务的获取状态接口
     */
    private void testGetStatus() {
        //后台地址
        String url = "https://dispatch.ytbapp.com/dispatch/GetDispatchStatus";
        MServiceUtils.setToken(loginBean.getToken_type() + " " + loginBean.getAccess_token());
        MServiceUtils.get(new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case MBaseConst.HANDLER_SERVICE_FINISH:
                        // TODO 请求结束，此时取消请求动画
                        break;
                    case MBaseConst.HANDLER_SERVICE_RESULT:
                        // 请求结束，处理请求结果
                        String result = msg.obj.toString();
                        BaseToast.makeTextShort(result);
                        break;
                    case MBaseConst.HANDLER_SERVICE_ERROR:
                        // 请求发生错误
                        String result2 = msg.obj.toString();
                        BaseToast.makeTextShort(result2);
                        break;
                }
                return false;
            }
        }), url, null);
    }

    /**
     * 开停服务登录
     */
    private void testLogin() {
        //后台地址
        String url = "https://account.ytbapp.com";
        // 登录接口
        String loginFunc = "/oauth2/authorize";
        /**
         * 参数设置
         */
        Map<String, String> params = new HashMap<>();
        params.put("username", "admin@simple1");
        params.put("password", "123456");
        params.put("client_id", "YetoonApp@iPad");
        params.put("client_secret", "abc123");
        params.put("grant_type", "password");

        MServiceUtils.post(new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case MBaseConst.HANDLER_SERVICE_FINISH:
                        // TODO 请求结束，此时取消请求动画
                        break;
                    case MBaseConst.HANDLER_SERVICE_RESULT:
                        // 请求结束，处理请求结果
                        String result = msg.obj.toString();
                        LogUtils.d(result);
                        loginBean = new Gson().fromJson(result, new TypeToken<LoginBean>() {
                        }.getType());
                        BaseToast.makeTextShort(loginBean.getToken_type() + " " + loginBean.getAccess_token());
                        // 登录结束
                        testGetStatus();
                        break;
                    case MBaseConst.HANDLER_SERVICE_ERROR:
                        // 请求发生错误
                        String result2 = msg.obj.toString();
                        BaseToast.makeTextShort(result2);
                        break;
                }
                return false;
            }
        }), url + loginFunc, params);
    }
}
