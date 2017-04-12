package com.maf.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * 项目名称：maflibrary
 * 类描述：基础的PopupWindow，
 * 1：是点击外面自动隐藏Window
 * 2：包含设置背景透明度的方法
 * 创建人：mzg
 * 创建时间：2016/8/12 13:29
 * 修改人：mzg
 * 修改时间：2016/8/12 13:29
 * 修改备注：
 */
public abstract class BasePopup extends PopupWindow {
    protected Context context;
    /**
     * 布局
     */
    protected View view;

    public BasePopup(Context context) {
        this.context = context;
        view = LayoutInflater.from(context).inflate(getViewId(), null);
        setContentView(view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setFocusable(true);
        setOutsideTouchable(true);
//        setBackgroundDrawable(context.getResources().getDrawable(
//                R.color.transparent));
        setBackgroundDrawable(new BitmapDrawable());
        initView();
        initValue();
        initEvent();

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                // 对话框消失是，背景还原
                backgroundAlpha(1);
            }
        });
    }

    /**
     * 返回popup的布局id
     *
     * @return
     */
    protected abstract int getViewId();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 初始化控件内容
     */
    protected abstract void initValue();

    /**
     * 初始化事件
     */
    protected abstract void initEvent();

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        ((Activity) context).getWindow().setAttributes(lp);
    }

    /**
     * 在界面的最右侧显示对话框，同时界面背景半透明
     *
     * @param view 如果是BaseFragment，view = parentView
     *             如果是BaseActivity，view = getWindow().getDecorView();
     */
    public void showPopupRight(View view) {
        showAtLocation(view, Gravity.RIGHT, 0, 0);
        backgroundAlpha(0.5f);
    }

    /**
     * 在按钮的下方显示对话框
     *
     * @param view 点击的View
     */
    public void showPopupDownView(View view) {
        showAsDropDown(view, 0, 0);
    }

}