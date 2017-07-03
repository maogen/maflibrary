package com.maf.base.activity;

import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.maf.activity.BaseTitleActivity;
import com.maf.base.adapter.BaseListTestAdapter;
import com.maf.utils.BaseToast;
import com.maf.utils.DateUtils;
import com.maf.utils.DialogUtil;
import com.maf.views.XListView;

import java.util.ArrayList;
import java.util.List;

import maf.com.mafproject.R;

/**
 * 项目名称：maflibrary
 * 类描述：XListView测试
 * 创建人：zgmao
 * 创建时间：2017/7/3
 * 修改人：zgmao
 * 修改时间：2017/7/3
 * 修改备注：
 * Created by zgmao on 2017/7/3.
 */

public class XListViewActivity extends BaseTitleActivity {  // 测试XListView加载更多、刷新功能
    private XListView list_test;
    private BaseListTestAdapter adapterTest;
    private List<String> dataList;

    private int maxCount = 33;
    private Handler mHandler;

    @Override
    protected void initTitleView() {
        titleBarView.setTitle("测试界面");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_x_listview;
    }

    @Override
    protected void initView() {
        list_test = (XListView) findViewById(R.id.list_test);
        dataList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            dataList.add("test" + i);
        }
        adapterTest = new BaseListTestAdapter(this, dataList);
        list_test.setAdapter(adapterTest);
        // 设置可上拉刷新和下拉加载更多
        list_test.setPullLoadEnable(true);
        list_test.setPullRefreshEnable(true);
    }

    @Override
    protected void initEvent() {
        list_test.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                // 刷新操作
                DialogUtil.showProgressDialog(mContext);
                mHandler.sendEmptyMessageDelayed(0, 1000);
            }

            @Override
            public void onLoadMore() {
                // 加载更多
                DialogUtil.showProgressDialog(mContext);
                mHandler.sendEmptyMessageDelayed(1, 1000);
            }
        });
    }

    @Override
    protected void initValue() {
        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        // 刷新
                        dataList.clear();
                        for (int i = 0; i < 15; i++) {
                            dataList.add("刷新test" + i);
                        }
                        stopLoad();
                        break;
                    case 1:
                        // 加载更多
                        for (int i = 0; i < 10; i++) {
                            dataList.add("加载更多test" + i);
                        }
                        stopLoad();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    /**
     * 停止加载
     */
    private void stopLoad() {
        adapterTest.notifyDataSetChanged();
        DialogUtil.dismissDialog();

        list_test.stopRefresh();
        list_test.stopLoadMore();
        list_test.setRefreshTime(DateUtils.getDateByFormat(DateUtils.defaultDateFormat));
        if (dataList.size() >= maxCount) {
            // 数据加载完毕，无法继续加载更多
            list_test.setPullLoadEnable(false);
            BaseToast.makeTextShort(dataList.size() + "数据全部加载完成");
        } else {
            list_test.setPullLoadEnable(true);
        }
    }

    @Override
    public void onClick(View v) {

    }
}
