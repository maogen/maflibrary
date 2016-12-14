package com.maf.base.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.maf.activity.BaseCustomSwipeRefreshActivity;
import com.maf.base.adapter.MyLoadMoreAdapter;
import com.maf.interfaces.ScrollCallBack;
import com.maf.utils.BaseToast;
import com.maf.utils.Lg;
import com.maf.views.CustomScrollView;

import java.util.ArrayList;
import java.util.List;

import maf.com.mafproject.R;

/**
 * 项目名称：maflibrary
 * 类描述：上拉刷新，下拉加载更多。
 * 分页请求加载更多注意事项
 * 1：通过回调函数refreshing()，执行上拉刷新方法，请求第一页数据
 * 2：布局中添加CustomScrollView控件，通过给该控件设置监听器，执行上拉到底部回调方法
 * 3：在请求过程中调用customScrollView.setIsLoading(true)方法，可以防止在加载数据过程中，滑动再次引起回调
 * 4：在加载数据过程中，不能调用swipeRefreshLayout.autoRefresh()方法展示动画，因为此时会引起refreshing()方法的回调，导致执行了刷新逻辑
 * 5：数据加载结束，调用customScrollView.setIsLoading(false)，则下次滑动，上拉加载更多方法才会再次被回调执行
 * 创建人：mzg
 * 创建时间：2016/10/25 10:30
 * 修改人：mzg
 * 修改时间：2016/10/25 10:30
 * 修改备注：
 */
public class LoadMoreActivity extends BaseCustomSwipeRefreshActivity implements Handler.Callback {
    // 加载数据
    private Handler handler;

    // 显示数据控件
    private RecyclerView recyclerView;
    private List<String> datas;
    private MyLoadMoreAdapter adapter;

    // 监听滑到顶部，滑到底部加载更多事件
    private CustomScrollView customScrollView;

    /**
     * 分页请求数据，总共几页数据
     */
    private int maxPage = 3;
    /**
     * 当前第几页数据
     */
    private int indexPage = 0;

    @Override
    public int getSwipeRefreshId() {
        return R.id.swipe_container;
    }

    @Override
    public void refreshing() {
        indexPage = 0;
        request();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_load_more;
    }

    @Override
    protected void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        customScrollView = (CustomScrollView) findViewById(R.id.scrollView);
    }

    @Override
    protected void initEvent() {
        customScrollView.setCallBack(new ScrollCallBack() {
            @Override
            public void OnScrollTop(boolean isTop) {
                Lg.d("是否滑动到顶部：" + isTop);
            }

            @Override
            public void scrollBottomState() {
                Lg.d("滑动到底部");
                request();
            }
        });
    }

    @Override
    protected void initValue() {
        handler = new Handler(this);
        datas = new ArrayList<>();
        adapter = new MyLoadMoreAdapter(this, datas);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean handleMessage(Message msg) {
        Lg.d("收到消息：" + msg.what);
        switch (msg.what) {
            case 1:
                addData(true);
                break;
            case 2:
                addData(false);
                break;
            default:
                break;
        }
        return false;
    }

    /**
     * 请求数据
     */
    private void request() {
        if (maxPage == 0 || indexPage >= maxPage) {
            // 最大页码为0，不加载数据
            finishRefresh();// 隐藏加载动画
            return;
        }
        // 正在请求
        BaseToast.makeTextShort("请求第" + indexPage + "页数据");
        customScrollView.setIsLoading(true);
        if (indexPage == 0) {
            // 第一页数据
            handler.sendEmptyMessageDelayed(1, 2000);
        } else if (indexPage > 0) {
            // 下一页数据
            handler.sendEmptyMessageDelayed(2, 2000);
        }
    }

    /**
     * 添加测试数据
     *
     * @param isFirstPage
     */
    private void addData(boolean isFirstPage) {
        if (isFirstPage) {
            datas.clear();
        }
        for (int i = 0; i < 15; i++) {
            datas.add("第" + indexPage + "页数据：" + "Data" + i);
        }
        indexPage++;// 页码加1
        adapter.notifyDataSetChanged();// 刷新数据显示
        customScrollView.setIsLoading(false);// 设置成非加载状态
        finishRefresh();// 隐藏加载动画
    }
}
