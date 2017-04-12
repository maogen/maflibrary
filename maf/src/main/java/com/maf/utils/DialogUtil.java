package com.maf.utils;

import android.app.Dialog;
import android.content.Context;

import com.maf.views.CustomProgressDialog;


/**
 * 项目名称：Ytb_Android
 * 类描述：对话框工具，部分网络请求显示全屏不可取消对话框
 * 创建人：mzg
 * 创建时间：2016/8/1 16:44
 * 修改人：mzg
 * 修改时间：2016/8/1 16:44
 * 修改备注：
 */
public class DialogUtil {
    private static Dialog mAlertDialog = null;
    private static Dialog fetchDialog = null;//读取对话框，可以取消

    /**
     * 取消所有弹出的对话框
     */
    public static void dismissDialog() {
        if (mAlertDialog != null) {
            mAlertDialog.dismiss();
        }
        if (fetchDialog != null) {
            fetchDialog.dismiss();
        }
        fetchDialog = null;
        mAlertDialog = null;
    }

    /**
     * 显示进度对话框（不可取消）
     */

    public static void showProgressDialog(Context context) {
        dismissDialog();
        mAlertDialog = CustomProgressDialog.createDialog(context);
        mAlertDialog.setCancelable(false);
        mAlertDialog.show();
    }


    /**
     * 显示进度对话框（可以取消）
     *
     * @param context
     */
    public static void showFetchDiglog(Context context) {
        dismissDialog();
        fetchDialog = CustomProgressDialog.createDialog(context);
        fetchDialog.show();
    }

}
