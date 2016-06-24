package com.maf.base.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.maf.activity.BaseBackActivity;
import com.maf.utils.LogUtils;

import maf.com.mafproject.R;

import android.webkit.WebChromeClient;

/**
 * Created by mzg on 2016/6/23.
 */
public class WebViewActivity extends BaseBackActivity {
    private String url;
    private WebView webView;
    private ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_web);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
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
    protected void initData() {
        url = getIntent().getStringExtra("url");
        LogUtils.d(url);
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
        webView.getSettings().setUseWideViewPort(true);//适应分辨率
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.loadUrl(url);
    }

    @Override
    public void onClick(View v) {

    }
}
