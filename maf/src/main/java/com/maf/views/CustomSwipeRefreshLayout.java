package com.maf.views;


import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * 项目名称：Ytb_Android
 * 类描述：自定义下拉刷新组件,可以直接显示下拉刷新进度条
 * 创建人：mzg
 * 创建时间：2016/7/11 15:15
 * 修改人：mzg
 * 修改时间：2016/7/11 15:15
 * 修改备注：
 */
public class CustomSwipeRefreshLayout extends SwipeRefreshLayout {
    public CustomSwipeRefreshLayout(Context context) {
        super(context);
    }

    public CustomSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 自动刷新，如果每次进入该页面的时候，要求自动请求加载数据，可调用该方法自动显示刷新动画
     */
    public void autoRefresh() {
        try {
            Field mCircleView = SwipeRefreshLayout.class.getDeclaredField("mCircleView");
            mCircleView.setAccessible(true);
            View progress = (View) mCircleView.get(this);
            progress.setVisibility(VISIBLE);
            Method setRefreshing = SwipeRefreshLayout.class.getDeclaredMethod("setRefreshing", boolean.class, boolean.class);
            setRefreshing.setAccessible(true);
            setRefreshing.invoke(this, true, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}