package com.maf.base.activity;

import android.content.Intent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;

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

    private WebView webView;
    private ProgressBar bar;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_html;
    }

    @Override
    protected void initView() {
        for (int i = 0; i < url.length; i++) {
            btn[i] = (Button) findViewById(btnId[i]);
            btn[i].setOnClickListener(this);
        }

        webView = (WebView) findViewById(R.id.web);
        bar = (ProgressBar) findViewById(R.id.myProgressBar);
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    bar.setVisibility(View.INVISIBLE);
                } else {
                    if (View.INVISIBLE == bar.getVisibility()) {
                        bar.setVisibility(View.VISIBLE);
                    }
                    bar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

        });
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initValue() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//        webView.getSettings().setSupportZoom(true);
//        webView.getSettings().setBuiltInZoomControls(true);
//        webView.getSettings().setUseWideViewPort(true);
//        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        webView.getSettings().setLoadWithOverviewMode(true);
//        webView.getSettings().setAppCacheEnabled(true);
//        webView.getSettings().setDomStorageEnabled(true);
//        webView.getSettings().setUseWideViewPort(true);//适应分辨率
//        webView.getSettings().setLoadWithOverviewMode(true);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btn_1:
                webView.loadUrl(url[0]);
                break;
            case R.id.btn_2:
                webView.loadUrl(url[1]);
                break;
            case R.id.btn_3:
                webView.loadUrl(url[2]);
                break;
            case R.id.btn_4:
                webView.loadUrl(url[3]);
                break;
            case R.id.btn_5:
                webView.loadUrl(url[4]);
                break;
            case R.id.btn_6:
                webView.loadUrl(url[5]);
                break;

        }

    }
}
