package com.maf.views;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;

import com.maf.R;


/**
 * 项目名称：Ytb_Android
 * 类描述：自定义对话框，显示全局
 * 创建人：mzg
 * 创建时间：2016/8/1 16:46
 * 修改人：mzg
 * 修改时间：2016/8/1 16:46
 * 修改备注：
 */
public class CustomProgressDialog extends Dialog {
    private static CustomProgressDialog customProgressDialog = null;

    public CustomProgressDialog(Context context) {
        super(context);
    }

    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    public static CustomProgressDialog createDialog(Context context) {
        customProgressDialog = new CustomProgressDialog(context,
                R.style.CustomProgressDialog);
        customProgressDialog.setContentView(R.layout.custom_progress_dialog);
        customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;

        return customProgressDialog;
    }

}
