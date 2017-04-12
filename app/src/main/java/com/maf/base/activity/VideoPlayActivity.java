package com.maf.base.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.maf.activity.BaseTitleActivity;
import com.maf.utils.BaseToast;
import com.maf.utils.Lg;

import java.io.File;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
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

    /**
     * 播放控件
     */
    private VideoView videoView;
    private MediaController controller;

    /**
     * 视频名
     */
    private String movieName;
    /**
     * 视频地址
     */
    private String movieUrl;

    /**
     * 打开视频播放界面
     *
     * @param name 视频名称
     * @param url  视频地址，可以是本地地址
     */
    public static void actionStart(Context context, String name, String url) {
        Intent intent = new Intent(context, VideoPlayActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    protected void initTitleView() {
        titleBarView.setTitle(movieName);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_video_play;
    }

    @Override
    protected void initView() {
        videoView = (VideoView) findViewById(R.id.surface_view);
    }

    @Override
    protected void initEvent() {
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setOnVideoSizeChangedListener(new MediaPlayer
                        .OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int
                            height) {
                        controller = new MediaController(VideoPlayActivity.this);
                        videoView.setMediaController(controller);
                        controller.setAnchorView(videoView);
                    }
                });
            }
        });
    }

    @Override
    protected void initValue() {
        movieName = getIntent().getStringExtra("name");
        movieUrl = getIntent().getStringExtra("url");
        Lg.d("movieName:" + movieName);
        Lg.d("movieUrl:" + movieUrl);

        if (movieUrl.startsWith("http")) {
            videoView.setVideoURI(Uri.parse(movieUrl));
        } else if (new File(movieUrl).exists()) {
            videoView.setVideoPath(movieUrl);
        } else {
            BaseToast.makeTextShort("视频文件不存在");
            return;
        }
        controller = new MediaController(this);
        // 控制器显示5s后隐藏
        controller.show(5000);
        videoView.setMediaController(controller);
        //设置播放画质 高画质
        videoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
        // 取得焦点
        videoView.requestFocus();

    }

    @Override
    public void onClick(View v) {

    }
}
