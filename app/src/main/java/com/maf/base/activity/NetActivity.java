package com.maf.base.activity;

import android.view.View;

import com.google.gson.reflect.TypeToken;
import com.maf.activity.BaseCustomSwipeRefreshActivity;
import com.maf.base.bean.BaseRequestBean;
import com.maf.base.bean.Stores;
import com.maf.git.GsonUtils;
import com.maf.net.XAPIServiceListener;
import com.maf.net.XAPIServiceUtils;
import com.maf.utils.BaseToast;
import com.maf.utils.LogUtils;

import java.util.List;

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

    /**
     * 测试开停服务的获取状态接口
     */
    private void testXAPI() {
        if (!swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.autoRefresh();
        }
        XAPIServiceUtils.post(new XAPIServiceListener() {
            @Override
            public void onSuccess(String result) {

                BaseRequestBean<Stores> catchStores = GsonUtils.stringToGson(result, new TypeToken<BaseRequestBean<Stores>>() {
                });
                if (null != catchStores) {
                    List<Stores.Store> jsonStores = catchStores.getContent().getStores();
                    if (null != jsonStores) {
                        for (int i = 0; i < jsonStores.size(); i++) {
                            Stores.Store item = jsonStores.get(i);
                            LogUtils.d("店铺名：" + item.getName());
                            BaseToast.makeTextShort(item.getName());
                        }
                    }
                }
            }

            @Override
            public void onError(String result) {

            }

            @Override
            public void onFinished() {
                finishRefresh();
            }
        }, "api/Store/GetStores", null, null);
    }

}
