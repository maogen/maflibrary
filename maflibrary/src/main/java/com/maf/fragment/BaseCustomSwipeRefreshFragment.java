package com.maf.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maf.views.CustomSwipeRefreshLayout;

/**
 * 项目名称：maflibrary
 * 类描述：自定义下拉刷新fragment
 * 创建人：mzg
 * 创建时间：2016/7/14 9:42
 * 修改人：mzg
 * 修改时间：2016/7/14 9:42
 * 修改备注：
 */
public abstract class BaseCustomSwipeRefreshFragment extends BaseFragment {
    protected CustomSwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        initRefresh();
        return parentView;
    }

    /**
     * 初始化下拉刷新控件
     */
    private void initRefresh() {
        swipeRefreshLayout = (CustomSwipeRefreshLayout) parentView.findViewById(getSwipeRefreshId());
        if (null != swipeRefreshLayout) {
            //设置刷新时动画的颜色，可以设置4种颜色
            swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
            swipeRefreshLayout.setOnRefreshListener(new CustomSwipeRefreshLayout.OnRefreshListener() {

                public void onRefresh() {
                    refreshing();
                }
            });
        }
    }

    /**
     * 返回CustomSwipeRefreshLayout控件的id
     *
     * @return
     */
    public abstract int getSwipeRefreshId();

    /**
     * 正在刷新的回调方法
     */
    public abstract void refreshing();

    /**
     * 调用此方法可以停止刷新
     */
    public void refreshFinish() {
        if (null != swipeRefreshLayout) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
