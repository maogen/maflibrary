package com.maf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.maf.application.ActivityManager;
import com.maf.utils.LogUtils;

/**
 * Created by mzg on 2016/5/23.
 * MAF包的基础Activity类，用于使用堆栈管理Activity
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * 是否显示周期Activity回调函数的log
     */
    private boolean isShowCycleLog = false;

    /**
     * 初始化方法，在此方法里面，主要调用了initView、initEvent、initData等方法。
     * 界面创建的顺序是先初始化控件；再初始化控件的相关事件；再初始化数据。
     * 建议调用者重写initView、initEvent、initData方法。
     * 如果需要初始化的数据太大，可以自定义线程
     *
     * @param savedInstanceState bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        ActivityManager.getInstance().addActivity(this);
        initView();
        initEvent();
        initValue();
    }

    /**
     * 启动一个Activity
     */
    public void startActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    /**
     * 返回当前Activity布局文件的id
     *
     * @return
     */
    abstract protected int getLayoutResId();

    /**
     * 初始化界面控件方法
     */
    protected abstract void initView();

    /**
     * 初始化控件的事件
     */
    protected abstract void initEvent();

    /**
     * 初始化数据
     */
    protected abstract void initValue();

    @Override
    public abstract void onClick(View v);

    @Override
    protected void onStart() {
        super.onStart();
        if (isShowCycleLog)
            LogUtils.d(getLocalClassName() + " onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isShowCycleLog)
            LogUtils.d(getLocalClassName() + " onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isShowCycleLog)
            LogUtils.d(getLocalClassName() + " onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isShowCycleLog)
            LogUtils.d(getLocalClassName() + " onDestroy");
    }
}
