package com.maf.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.maf.R;


/**
 * 项目名称：tgrambaseline1.0
 * 类描述：界面控件工具类，
 * 创建人：zgmao
 * 创建时间：2017/8/30
 */
public class ViewUtils
{

    /**
     * EditText是否可编辑
     *
     * @param isEnabled
     */
    public static void setEditEnabled(EditText editText, boolean isEnabled)
    {
        editText.setEnabled(isEnabled);
        editText.setFocusable(isEnabled);
        editText.setFocusableInTouchMode(isEnabled);
    }

    /**
     * 请求EditText的焦点
     */
    public static void setEditClearFocusable(EditText editText)
    {
        editText.clearFocus();
    }

    /**
     * 显示dialog在底部，同时宽度和手机宽度一样，背景模糊
     *
     * @param context
     * @param dialog
     */
    public static void showDialog(final Context context, Dialog dialog)
    {
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        //设置dialog的宽度和手机宽度一样
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = dialogWindow.getWindowManager().getDefaultDisplay().getWidth();
        //设置宽度
        dialogWindow.setAttributes(lp);
        // 设置显示动画
        dialogWindow.setWindowAnimations(R.style.popwindow_from_bottom_style);
        // 界面半透明
        backgroundAlpha(context, 0.6f);
        //隐藏时，界面回复
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener()
        {
            @Override
            public void onDismiss(DialogInterface dialog)
            {
                backgroundAlpha(context, 1f);
            }
        });
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public static void backgroundAlpha(Context context, float bgAlpha)
    {
        WindowManager.LayoutParams windowLP = ((Activity) context).getWindow().getAttributes();
        windowLP.alpha = bgAlpha;
        if (bgAlpha == 1) {
            //不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
            ((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        } else {
            //此行代码主要是解决在华为手机上半透明效果无效的bug
            ((Activity) context).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        ((Activity) context).getWindow().setAttributes(windowLP);
    }

    /**
     * 设置控件左边图标
     */
    public static void setRightDrawable(Context context, TextView textView, Drawable drawable)
    {
        /// 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(null, null, drawable, null);

    }
}
