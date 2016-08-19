package com.maf.dialog;

import android.content.Context;

/**
 * 项目名称：Ytb_Android
 * 类描述：动画对号工具
 * 创建人：mzg
 * 创建时间：2016/8/19 11:55
 * 修改人：mzg
 * 修改时间：2016/8/19 11:55
 * 修改备注：
 */
public class CheckMarDialogUtils {
    private static CheckMarkDialog checkDialog;

    /**
     * 显示操作成功与否对话框
     *
     * @param context
     * @param isSuccess 是否成功
     */
    public static void showCheck(Context context, boolean isSuccess) {
        if (checkDialog == null) {
            checkDialog = new CheckMarkDialog(context);
        }
        checkDialog.showCheck(isSuccess);
    }
}
