package com.maf.base.activity;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.maf.activity.BaseTitleActivity;
import com.maf.base.thread.DownloadThread;

import maf.com.mafproject.R;

/**
 * 项目名称：maflibrary
 * 类描述：
 * 创建人：zgmao
 * 创建时间：2017/10/31
 * 修改人：zgmao
 * 修改时间：2017/10/31
 * 修改备注：
 * Created by zgmao on 2017/10/31.
 */
public class DownloadActivity extends BaseTitleActivity
{
    // 进度条
    private ProgressBar progressBar;
    // 开始下载按钮
    private Button btnStart;
    // 暂停下载按钮
    private Button btnPause;

    // 进度回调
    private Handler handler;

    // 下载链接
    private String url = "http://sw.bos.baidu.com/sw-search-sp/software/16d5a98d3e034/QQ_8.9.5.22062_setup.exe";

    @Override
    protected void initTitleView()
    {
        titleBarView.setTitle("文件下载");
    }

    @Override
    protected int getLayoutResId()
    {
        return R.layout.activity_download;
    }

    @Override
    protected void initView()
    {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnStart = (Button) findViewById(R.id.btn_start);
        btnPause = (Button) findViewById(R.id.btn_pause);
        handler = new Handler(new Handler.Callback()
        {
            @Override
            public boolean handleMessage(Message msg)
            {
                switch (msg.what) {
                    case DownloadThread.MSG_DOWNLOAD_PROGRESS:
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void initEvent()
    {
        btnStart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // 开始下载
            }
        });
        btnPause.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // 暂停下载
            }
        });
    }

    @Override
    protected void initValue()
    {

    }

    @Override
    public void onClick(View v)
    {

    }
}
