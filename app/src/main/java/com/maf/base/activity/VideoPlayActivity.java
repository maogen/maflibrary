package com.maf.base.activity;

import android.view.View;

import com.maf.activity.BaseTitleActivity;

import maf.com.mafproject.R;

/**
 * 项目名称：maflibrary
 * 类描述：视频播放界面
 * 创建人：zgmao
 * 创建时间：2017/4/12
 * 修改人：zgmao
 * 修改时间：2017/4/12
 * 修改备注：
 * Created by zgmao on 2017/4/12.
 */
public class VideoPlayActivity extends BaseTitleActivity {


    @Override
    protected void initTitleView() {
        titleBarView.setTitle("播放视频");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_video_play;
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
