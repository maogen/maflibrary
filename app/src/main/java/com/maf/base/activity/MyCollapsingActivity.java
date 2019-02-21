package com.maf.base.activity;

import android.os.SystemClock;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.maf.activity.BaseActivity;
import com.maf.base.adapter.MyCollapsingAdapter;
import com.maf.interfaces.OnItemClickListener;
import com.maf.interfaces.OnItemDoubleClickListener;
import com.maf.interfaces.OnItemLongClickListener;
import com.maf.utils.BaseToast;
import com.maf.utils.Lg;

import java.util.ArrayList;
import java.util.List;

import maf.com.mafproject.R;

/**
 * 项目名称：maflibrary
 * 类描述：收缩控件案例
 * 创建人：mzg
 * 创建时间：2016/7/14 20:55
 * 修改人：mzg
 * 修改时间：2016/7/14 20:55
 * 修改备注：
 */
public class MyCollapsingActivity extends BaseActivity
{
    private RecyclerView mRecyclerView;
    private MyCollapsingAdapter adapter;
    private List<String> dataList;

    private CoordinatorLayout coordinatorLayout;
    private NestedScrollView scrollView;
    private ImageView imageView;

    @Override
    protected int getLayoutResId()
    {
        return R.layout.activity_my_collapsing;
    }

    @Override
    protected void initView()
    {
        mRecyclerView = (RecyclerView) this.findViewById(R.id.recyclerView);
        // 瀑布流效果
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        // 普通ListView效果
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // 普通GridView效果
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        scrollView = (NestedScrollView) findViewById(R.id.scrollView);
        imageView = (ImageView) findViewById(R.id.imageView);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
    }

    @Override
    protected void initEvent()
    {
        imageView.setOnClickListener(this);
    }

    @Override
    protected void initValue()
    {
        dataList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            dataList.add("aaa" + i);
        }
        adapter = new MyCollapsingAdapter(this, dataList);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                BaseToast.makeTextShort("点击了：" + dataList.get(position));
            }
        });
        adapter.setOnItemLongClickListener(new OnItemLongClickListener()
        {
            @Override
            public void onItemLongClick(View view, int position)
            {
                BaseToast.makeTextShort("长按了：" + dataList.get(position));
            }
        });
        adapter.setOnItemDoubleClickListener(new OnItemDoubleClickListener()
        {
            @Override
            public void onItemDoubleClick(View view, int position)
            {
                BaseToast.makeTextShort("双击了：" + dataList.get(position));
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.imageView:
                // 点击图片，RecycleView聚焦到底部
                Lg.d("定位到最后");
//                moveToPosition(dataList.size() - 1);
//                scrollView.setSmoothScrollingEnabled(true);
//                View view = mRecyclerView.getChildAt(0);
//                scrollView.scrollTo(0, view.getHeight() * dataList.size());
//                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
//                MotionEvent.obtain(SystemClock.uptimeMillis()，)
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        Lg.d("事件类型" + event.getAction());
        return super.onTouchEvent(event);
    }

    boolean move = false;

    private void moveToPosition(int n)
    {
        //先从RecyclerView的LayoutManager中获取第一项和最后一项的Position
        LinearLayoutManager mLinearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        int firstItem = mLinearLayoutManager.findFirstVisibleItemPosition();
        int lastItem = mLinearLayoutManager.findLastVisibleItemPosition();
        //然后区分情况
        if (n <= firstItem) {
            //当要置顶的项在当前显示的第一个项的前面时
            mRecyclerView.scrollToPosition(n);
        } else if (n <= lastItem) {
            //当要置顶的项已经在屏幕上显示时
            int top = mRecyclerView.getChildAt(n - firstItem).getTop();
            mRecyclerView.scrollBy(0, top);
        } else {
            //当要置顶的项在当前显示的最后一项的后面时
            mRecyclerView.scrollToPosition(n);
            //这里这个变量是用在RecyclerView滚动监听里面的
            move = true;
        }
    }
}
