package com.maf.base.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.maf.activity.BaseBackActivity;

import maf.com.mafproject.R;

/**
 * Created by mzg on 2016/6/14.
 * 打印机操作界面
 */
public class PrintActivity extends BaseBackActivity {
    private TextView textStatus;// 当前蓝牙状态
    private Button btnOpen;//开启/关闭蓝牙
    private Button btnSearch;//搜索蓝牙设备

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_print);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        textStatus = (TextView) findViewById(R.id.text_bluetooth_status);
        btnOpen = (Button) findViewById(R.id.btn_open);
        btnSearch = (Button) findViewById(R.id.btn_search);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {

    }
}
