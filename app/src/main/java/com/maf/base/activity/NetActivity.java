package com.maf.base.activity;

import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.maf.activity.BaseCustomSwipeRefreshActivity;
import com.maf.base.bean.PatchBean;
import com.maf.git.GsonUtils;
import com.maf.net.XAPIServiceListener;
import com.maf.net.XBaseAPIUtils;
import com.maf.utils.BaseToast;
import com.maf.utils.LogUtils;

import java.util.HashMap;
import java.util.Map;

import maf.com.mafproject.BuildConfig;
import maf.com.mafproject.R;

/**
 * Created by mzg on 2016/5/30.
 * 网络测试界面
 */
public class NetActivity extends BaseCustomSwipeRefreshActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_net;
    }

    @Override
    public int getSwipeRefreshId() {
        return R.id.swipe_refresh;
    }

    @Override
    public void refreshing() {
        testXAPI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        testXAPI();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initValue() {

    }

    @Override
    public void onClick(View v) {

    }

    // 热修复后台地址
    private String patchBaseUrl = "http://192.168.1.176:8089/HotFixWebservice/";
    // 热修复后台地址
    private String patchAction = "getPatchPath.do";

    /**
     * 测试开停服务的获取状态接口
     */
    private void testXAPI() {
        if (!swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.autoRefresh();
        }
        Map<String, Object> map = new HashMap<>();
        map.put("appversion", BuildConfig.VERSION_NAME);
        XBaseAPIUtils.post(patchBaseUrl, patchAction, null,
                map, new XAPIServiceListener() {
                    @Override
                    public void onSuccess(String result) {
                        LogUtils.d("获取成功：" + result);
                        PatchBean patchBean = GsonUtils.stringToGson(result, new TypeToken<PatchBean>() {
                        });
                        if (patchBean != null && patchBean.isHasNewPatch()) {
                            // 有新插件，下载插件地址
                            BaseToast.makeTextShort("有新插件：" + patchBean.getPatchPath());

                        } else {
                            BaseToast.makeTextShort("无需修复");
                        }
                    }

                    @Override
                    public void onError(String result) {
                        // 请求错误
                        LogUtils.d("获取失败：" + result);
                        BaseToast.makeTextShort("获取热修复信息失败");
                    }

                    @Override
                    public void onFinished() {
                        finishRefresh();
                    }
                });
    }

}
