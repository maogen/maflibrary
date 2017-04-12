package com.maf.base.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.reflect.TypeToken;
import com.maf.activity.BaseCustomSwipeRefreshActivity;
import com.maf.base.adapter.MovieAdapter;
import com.maf.base.bean.Trailer;
import com.maf.base.bean.Trailers;
import com.maf.git.GsonUtils;
import com.maf.net.XAPIServiceListener;
import com.maf.net.XBaseAPIUtils;
import com.maf.utils.Lg;
import com.maf.views.TitleBarView;

import java.util.ArrayList;
import java.util.List;

import maf.com.mafproject.R;

/**
 * Created by mzg on 2016/5/30.
 * 网络测试界面
 */
public class NetActivity extends BaseCustomSwipeRefreshActivity {
    protected TitleBarView titleBarView;
    // 热修复后台地址
    private String patchBaseUrl = "http://api.m.mtime.cn/PageSubArea/";
    // 热修复后台地址
    private String patchAction = "/TrailerList.api";

    private ProgressBar progressBar;
    // 展示电影列表
    private List<Trailer> trailerList;
    private RecyclerView recyclerViewMovie;
    private MovieAdapter adapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_net;
    }

    @Override
    public int getSwipeRefreshId() {
        return R.id.swipe_refresh;
    }

    @Override
    public void refreshing() {
        testXAPI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        testXAPI();
    }

    @Override
    protected void initView() {
        titleBarView = (TitleBarView) findViewById(R.id.view_title_background);
        titleBarView.setTitle("网络测试");

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        recyclerViewMovie = (RecyclerView) findViewById(R.id.recycler_movie);
        recyclerViewMovie.setLayoutManager(new GridLayoutManager(this, 1));
        trailerList = new ArrayList<>();
        adapter = new MovieAdapter(this, trailerList);
        adapter.setProgressBar(progressBar);
        recyclerViewMovie.setAdapter(adapter);
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

    /**
     * 测试开停服务的获取状态接口
     */
    private void testXAPI() {
        if (!swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.autoRefresh();
        }
//        Map<String, Object> map = new HashMap<>();
//        map.put("appversion", BuildConfig.VERSION_NAME);
        XBaseAPIUtils.get(patchBaseUrl, patchAction, null,
                null, new XAPIServiceListener() {
                    @Override
                    public void onSuccess(String result) {
                        Lg.d("获取成功：" + result);
                        Trailers trailers = GsonUtils.stringToGson(result, new
                                TypeToken<Trailers>() {
                                });
                        List<Trailer> movieList = trailers.getTrailers();
                        if (movieList == null) {
                            Lg.e("trailerList is null");
                            return;
                        }
                        trailerList.clear();
                        trailerList.addAll(movieList);
                        recyclerViewMovie.getAdapter().notifyDataSetChanged();
//                        PatchBean patchBean = GsonUtils.stringToGson(result, new
//                                TypeToken<PatchBean>() {
//                        });
//                        if (patchBean != null && patchBean.isHasNewPatch()) {
//                            // 有新插件，下载插件地址
//                            BaseToast.makeTextShort("有新插件：" + patchBean.getPatchPath());
//
//                        } else {
//                            BaseToast.makeTextShort("无需修复");
//                        }
                    }

                    @Override
                    public void onError(String result) {
                        // 请求错误
                        Lg.d("获取失败：" + result);
//                        BaseToast.makeTextShort("获取热修复信息失败");
                    }

                    @Override
                    public void onFinished() {
                        finishRefresh();
                    }
                });
    }

}
