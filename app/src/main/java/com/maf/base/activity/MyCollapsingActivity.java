package com.maf.base.activity;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.maf.activity.BaseActivity;
import com.maf.base.adapter.MyCollapsingAdapter;

import java.util.ArrayList;
import java.util.List;

import maf.com.mafproject.R;

/**
 * 项目名称：maflibrary
 * 类描述：收缩控件案例
 * 创建人：mzg
 * 创建时间：2016/7/14 20:55
 * 修改人：mzg
 * 修改时间：2016/7/14 20:55
 * 修改备注：
 */
public class MyCollapsingActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private MyCollapsingAdapter adapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_my_collapsing;
    }

    @Override
    protected void initView() {
        mRecyclerView = (RecyclerView) this.findViewById(R.id.recyclerView);
        // 瀑布流效果
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        // 普通ListView效果
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // 普通GridView效果
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initValue() {
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            dataList.add("aaa" + i);
        }
        adapter = new MyCollapsingAdapter(this, dataList);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

    }
}
