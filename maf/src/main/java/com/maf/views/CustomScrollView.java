package com.maf.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ScrollView;
import android.widget.Scroller;

import com.maf.interfaces.ScrollCallBack;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 自定义ScrollView，解决：ScrollView嵌套ViewPager，导致ViewPager不能滑动的问题
 */
public class CustomScrollView extends ScrollView {
    private GestureDetector mGestureDetector;
    private int Scroll_height = 0;
    private int view_height = 0;
    protected Field scrollView_mScroller;
    private static final String TAG = "CustomScrollView";

    private ScrollCallBack scrollCallBack;// 当滚动到做底部、到顶部，回调该监听器

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mGestureDetector = new GestureDetector(context, new YScrollDetector());
        setFadingEdgeLength(0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            stopAnim();
        }
        boolean ret = super.onInterceptTouchEvent(ev);
        boolean ret2 = mGestureDetector.onTouchEvent(ev);
        return ret && ret2;
    }

    // Return false if we're scrolling in the x direction
    class YScrollDetector extends SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (Math.abs(distanceY) > Math.abs(distanceX)) {
                return true;
            }
            return false;
        }
    }

    /**
     * 设置滚动到最底部监听器
     *
     * @param callBack 监听器
     */
    public void setCallBack(ScrollCallBack callBack) {
        this.scrollCallBack = callBack;
    }

    // ScrollView是否已经在顶部
    private boolean isTop = true;
    // ScrollView是否已经在底部
    private boolean isBottom = false;
    // 是否在加载数据中，如果正在加载，滑动到底部不触发回调
    private boolean isLoading = false;

    /**
     * 设置是否在加载数据中，如果在加载，则滑动到底部，不触发回调
     *
     * @param isLoading
     */
    public void setIsLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    @Override
    protected void onScrollChanged(int left, int top, int oldLeft, int oldTop) {
        // 判断是否滑动到底部
        View view = getChildAt(getChildCount() - 1);
        int d = view.getBottom();
        d -= (getHeight() + getScrollY());
        if (d == 0 && scrollCallBack != null && !isBottom) {
            if (!isLoading) {
                // 不再加载数据，所以滑动到底部，回调
                scrollCallBack.scrollBottomState();
            }
            isBottom = true;
        } else if (d != 0 && scrollCallBack != null && isBottom) {
            isBottom = false;
        }
        // 判断是否滑到顶部
        if (scrollCallBack != null) {
            if (top <= 50 && !isTop) {
                scrollCallBack.OnScrollTop(true);
                isTop = true;
            } else if (top > 50 && isTop) {
                scrollCallBack.OnScrollTop(false);
                isTop = false;
            }
        }
        boolean stop = false;
        if (Scroll_height - view_height == top) {
            stop = true;
        }

        if (top == 0 || stop == true) {
            try {
                if (scrollView_mScroller == null) {
                    scrollView_mScroller = getDeclaredField(this, "mScroller");
                }

                Object ob = scrollView_mScroller.get(this);
                if (ob == null || !(ob instanceof Scroller)) {
                    return;
                }
                Scroller sc = (Scroller) ob;
                sc.abortAnimation();

            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
        super.onScrollChanged(left, top, oldLeft, oldTop);
    }

    private void stopAnim() {
        try {
            if (scrollView_mScroller == null) {
                scrollView_mScroller = getDeclaredField(this, "mScroller");
            }

            Object ob = scrollView_mScroller.get(this);
            if (ob == null) {
                return;
            }
            Method method = ob.getClass().getMethod("abortAnimation");
            method.invoke(ob);
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    @Override
    protected int computeVerticalScrollRange() {
        Scroll_height = super.computeVerticalScrollRange();
        return Scroll_height;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed == true) {
            view_height = b - t;
        }
    }

    @Override
    public void requestChildFocus(View child, View focused) {
        if (focused != null && focused instanceof WebView) {
            return;
        }
        super.requestChildFocus(child, focused);
    }

    /**
     * 获取一个对象隐藏的属性，并设置属性为public属性允许直接访问
     *
     * @return {@link Field} 如果无法读取，返回null；返回的Field需要使用者自己缓存，本方法不做缓存�?
     */
    public static Field getDeclaredField(Object object, String field_name) {
        Class<?> cla = object.getClass();
        Field field = null;
        for (; cla != Object.class; cla = cla.getSuperclass()) {
            try {
                field = cla.getDeclaredField(field_name);
                field.setAccessible(true);
                return field;
            } catch (Exception e) {

            }
        }
        return null;
    }

}