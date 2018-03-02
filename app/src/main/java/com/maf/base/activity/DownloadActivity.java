package com.maf.base.activity;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.maf.activity.BaseTitleActivity;
import com.maf.net.XBaseAPIUtils;
import com.maf.utils.FileUtils;
import com.maf.utils.Lg;

import org.xutils.common.Callback;

import java.io.File;

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

    /**
     * 文件下载地址
     */
    private String[] filePath = {"http://34.44.1.227/apk/1.zip", "http://34.44.1.227/apk/2.apk", "http://34.44.1.227/apk/3.exe"};
    /**
     * 文件名
     */
    TextView textFileName;
    /**
     * 文件大小
     */
    TextView textFileSize;
    /**
     * 下载进度条
     */
    ProgressBar progressBar;
    /**
     * 下载状态
     */
    Button btnStatus;

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
        textFileName = (TextView) findViewById(R.id.text_file_name);
        textFileSize = (TextView) findViewById(R.id.text_file_size);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(100);
        btnStatus = (Button) findViewById(R.id.btn_download_status);
    }

    @Override
    protected void initEvent()
    {
        btnStatus.setOnClickListener(this);
    }

    @Override
    protected void initValue()
    {
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.btn_download_status:
                String url = filePath[1];
                File file = new File(url);
                String saveFile = FileUtils.getFilePath(file.getName());
                Callback.Cancelable cancelable = XBaseAPIUtils.loadFile(url, saveFile, new Callback.ProgressCallback<File>()
                {
                    @Override
                    public void onWaiting()
                    {
                        Lg.d("onStarted");
                    }

                    @Override
                    public void onStarted()
                    {
                        Lg.d("onStarted");
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isDownloading)
                    {
                        Lg.d("onLoading，current:+" + current + ";total:" + total + ";isDownloading:" + isDownloading);
                        textFileSize.setText(FileUtils.getFileSize(current) + "/" + FileUtils.getFileSize(total));
                    }

                    @Override
                    public void onSuccess(File result)
                    {
                        Lg.d("onSuccess，AbsolutePath:" + result.getAbsolutePath());
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback)
                    {
                        ex.printStackTrace();
                        Lg.d("onError，isOnCallback" + isOnCallback);
                    }

                    @Override
                    public void onCancelled(CancelledException cex)
                    {
                        cex.printStackTrace();
                        Lg.d("onCancelled");
                    }

                    @Override
                    public void onFinished()
                    {
                        Lg.d("onFinished");
                    }
                });
            default:
                break;
        }
    }
}
