package com.maf.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * 项目名称：maflibrary
 * 类描述：基础的popup
 * 创建人：mzg
 * 创建时间：2016/7/26 13:28
 * 修改人：mzg
 * 修改时间：2016/7/26 13:28
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

}
