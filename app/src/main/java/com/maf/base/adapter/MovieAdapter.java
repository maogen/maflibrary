package com.maf.base.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.ProgressBar;

import com.maf.adapter.BaseRecycleAdapter;
import com.maf.base.activity.VideoPlayActivity;
import com.maf.base.bean.Trailer;
import com.maf.git.GlideUtils;
import com.maf.utils.BaseToast;
import com.maf.utils.FileUtils;
import com.maf.utils.Lg;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.List;

import maf.com.mafproject.R;

/**
 * 项目名称：maflibrary
 * 类描述：
 * 创建人：zgmao
 * 创建时间：2017/4/11
 * 修改人：zgmao
 * 修改时间：2017/4/11
 * 修改备注：
 * Created by zgmao on 2017/4/11.
 */
public class MovieAdapter extends BaseRecycleAdapter<Trailer, MovieViewHolder> {
    private ProgressBar progressBar;

    public MovieAdapter(Context context, List<Trailer> list) {
        super(context, list);
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    @Override
    protected int getResourceId() {
        return R.layout.item_movie;
    }

    @Override
    protected MovieViewHolder getViewHolder(View view) {
        MovieViewHolder viewHolder = new MovieViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        final Trailer trailer = list.get(position);
        // 设置电影图片
        GlideUtils.loadImage(context, trailer.getCoverImg(), holder.imageCover);
        // 设置电影名
        holder.textMovieName.setText(trailer.getMovieName());
        holder.textMovieName.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        // 设置电影描述
        holder.textMovieSummary.setText(trailer.getSummary());
        // 设置电影类型
        StringBuffer sbType = new StringBuffer();
        List<String> typeList = trailer.getType();
        if (typeList != null) {
            for (String item : typeList) {
                sbType.append("[").append(item).append("]");
            }
        }
        holder.textMovieType.setText(sbType.toString());
        // 设置点击事件
        holder.textMovieName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadFile(trailer);
            }
        });
        holder.imageCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMovie(trailer);
            }
        });
    }

    /**
     * 是否正在下载
     */
    private boolean isLoading = false;

    /**
     * 下载文件
     *
     * @param trailer 电影
     */
    private void downloadFile(final Trailer trailer) {
        if (isLoading) {
            BaseToast.makeTextShort("正在下载中，请稍后再试");
            return;
        }
        String movieUrl = trailer.getHightUrl();
        String fileName = FileUtils.getFileNameByPath(movieUrl);
        // 根据文件名，得到本地文件地址
        String sdcardFilePath = FileUtils.getFilePath(fileName);
        Lg.d("文件：" + sdcardFilePath);
        File sdcardFile = new File(sdcardFilePath);
        if (sdcardFile.exists()) {
            BaseToast.makeTextShort("本地文件已经存在");
            return;
        }
//        BaseToast.makeTextShort("下载预告片" + fileName);
        isLoading = true;
        RequestParams params = new RequestParams(movieUrl);
        // 设置保存路径
        params.setSaveFilePath(sdcardFilePath);
        // 设置是否可以取消下载
        params.setCancelFast(true);
        // 自动是否命名
        params.setAutoRename(true);
        // 设置断点续传
        params.setAutoResume(true);
        x.http().get(params, new Callback.ProgressCallback<File>() {
            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {

            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                Lg.d("total:" + total + ";current:" + current + ";isDownloading: " +
                        isDownloading);
                if (progressBar != null) {
                    progressBar.setMax((int) total);
                    progressBar.setProgress((int) current);
                }
            }

            @Override
            public void onSuccess(File file) {
                // 下载成功
                Lg.d("下载成功，文件路径：" + file.getAbsolutePath());
                BaseToast.makeTextShort("下载文件成功");
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Lg.d("onError");
                // 失败
                ex.printStackTrace();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Lg.d("onCancelled");
                // 取消
                cex.printStackTrace();
            }

            @Override
            public void onFinished() {
                Lg.d("onFinished");
                isLoading = false;
            }
        });
    }

    /**
     * 播放视频
     *
     * @param trailer
     */
    private void playMovie(Trailer trailer) {
        String movieUrl = trailer.getHightUrl();
        String fileName = FileUtils.getFileNameByPath(movieUrl);
        // 根据文件名，得到本地文件地址
        String sdcardFilePath = FileUtils.getFilePath(fileName);
        File sdcardFile = new File(sdcardFilePath);
        if (sdcardFile.exists()) {
            // 本地文件存在，播放本地视频
            BaseToast.makeTextShort("播放本地视频");
            VideoPlayActivity.actionStart(context, trailer.getMovieName(),
                    sdcardFilePath);
        } else {
            BaseToast.makeTextShort("播放在线视频");
            VideoPlayActivity.actionStart(context, trailer.getMovieName(), movieUrl);
        }
    }
}
