package com.maf.base.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.maf.activity.BaseBackActivity;

import maf.com.mafproject.R;

/**
 * Created by mzg on 2016/6/23.
 * 加载html
 */
public class HtmlActivity extends BaseBackActivity {
    private String[] url = {
            "file:///android_asset/ssss/donut2d_03.html",
            "file:///android_asset/ssss/donut2d_02.html",
            "file:///android_asset/ssss/donut2d_03.html",
            "file:///android_asset/ssss/area2d_01.html",
            "file:///android_asset/ssss/area2d_02.html",
            "file:///android_asset/ssss/area2d_03.html"
    };
    private int btnId[] = {R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5, R.id.btn_6};

    private Button[] btn = new Button[url.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_html);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        for (int i = 0; i < url.length; i++) {
            btn[i] = (Button) findViewById(btnId[i]);
            btn[i].setOnClickListener(this);
        }
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btn_1:
                intent = new Intent(HtmlActivity.this, WebViewActivity.class);
                intent.putExtra("url", url[0]);
                startActivity(intent);
                break;
            case R.id.btn_2:
                intent = new Intent(HtmlActivity.this, WebViewActivity.class);
                intent.putExtra("url", url[1]);
                startActivity(intent);
                break;
            case R.id.btn_3:
                intent = new Intent(HtmlActivity.this, WebViewActivity.class);
                intent.putExtra("url", url[2]);
                startActivity(intent);
                break;
            case R.id.btn_4:
                intent = new Intent(HtmlActivity.this, WebViewActivity.class);
                intent.putExtra("url", url[3]);
                startActivity(intent);
                break;
            case R.id.btn_5:
                intent = new Intent(HtmlActivity.this, WebViewActivity.class);
                intent.putExtra("url", url[4]);
                startActivity(intent);
                break;
            case R.id.btn_6:
                intent = new Intent(HtmlActivity.this, WebViewActivity.class);
                intent.putExtra("url", url[5]);
                startActivity(intent);
                break;

        }

    }
}
