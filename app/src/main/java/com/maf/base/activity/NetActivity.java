package com.maf.base.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.maf.activity.BaseCustomSwipeRefreshActivity;
import com.maf.base.adapter.MovieAdapter;
import com.maf.base.bean.Trailer;
import com.maf.base.bean.Trailers;
import com.maf.base.logic.MovieLogic;
import com.maf.interfaces.IViewListener;
import com.maf.utils.Lg;
import com.maf.views.TitleBarView;

import java.util.ArrayList;
import java.util.List;

import maf.com.mafproject.R;

/**
 * Created by mzg on 2016/5/30.
 * 网络测试界面
 */
public class NetActivity extends BaseCustomSwipeRefreshActivity implements IViewListener<Trailers> {
    protected TitleBarView titleBarView;

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

    private MovieLogic movieLogic;

    @Override
    protected void initValue() {
        movieLogic = new MovieLogic(this, this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void updateView(Trailers trailers) {
        if (null != trailers) {
            List<Trailer> movieList = trailers.getTrailers();
            if (movieList == null) {
                Lg.e("trailerList is null");
                return;
            }
            trailerList.clear();
            trailerList.addAll(movieList);
            recyclerViewMovie.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void onSuccess(String result) {

    }

    @Override
    public void onError(String result) {
        Lg.d("获取失败：" + result);
    }

    @Override
    public void onFinished() {
        finishRefresh();
    }

    /**
     * 测试开停服务的获取状态接口
     */
    private void testXAPI() {
        if (!swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.autoRefresh();
        }
        movieLogic.requestMovieList();
    }
}
