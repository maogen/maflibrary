package com.maf.base.activity;

import android.view.View;

import com.maf.activity.BaseTitleActivity;

import maf.com.mafproject.R;

/**
 * 项目名称：maflibrary
 * 类描述：xposed测试
 * 创建人：zgmao
 * 创建时间：2017/5/19
 * 修改人：Administrator
 * 修改时间：2017/5/19
 * 修改备注：
 * Created by Administrator on 2017/5/19.
 */

public class XPosedActivity extends BaseTitleActivity {
    @Override
    protected void initTitleView() {
        titleBarView.setTitle("xposed测试");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_xposed;
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
}
