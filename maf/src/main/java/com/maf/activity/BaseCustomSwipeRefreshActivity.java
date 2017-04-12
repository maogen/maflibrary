package com.maf.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;

import com.maf.views.CustomSwipeRefreshLayout;

/**
 * 项目名称：maflibrary
 * 类描述：
 * 集成下拉刷新SwipeRefresh组件的界面，
 * 在布局中需要包含以下布局：
 *
 *  <com.maf.views.CustomSwipeRefreshLayout
        android:id="@+id/swipe_container"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:orientation="vertical">

 * 如果要检测下面布局是否滑动到顶部，或者底部，
 * 可是添加CustomScrollView控件，然后设置监听器。
 * 此时内部布局的RecycleView和ListView等控件的滑动事件将被截断
 * 此时布局如下：
    <com.maf.views.CustomSwipeRefreshLayout
        android:id="@+id/swipe_container"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <com.maf.views.CustomScrollView
            android:id="@+id/scrollView"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:fillViewport="true"
            android:scrollbars="none">

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:orientation="vertical">
 * 创建人：mzg
 * 创建时间：2016/7/14 9:13
 * 修改人：mzg
 * 修改时间：2016/7/14 9:13
 * 修改备注：
 */
public abstract class BaseCustomSwipeRefreshActivity extends BaseBackActivity {
    protected CustomSwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRefresh();
    }

    /**
     * 初始化下拉刷新控件
     */
    private void initRefresh() {
        swipeRefreshLayout = (CustomSwipeRefreshLayout) findViewById(getSwipeRefreshId());
        if (null != swipeRefreshLayout) {
            //设置刷新时动画的颜色，可以设置4种颜色
            swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

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
    public void finishRefresh() {
        if (null != swipeRefreshLayout) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
