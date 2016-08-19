package com.maf.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.maf.R;

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
        View view = LayoutInflater.from(context).inflate(R.layout.layout_check_mark, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_check);
        if (isSuccess) {
            imageView.setImageResource(R.drawable.check_mark_success);
        } else {
            imageView.setImageResource(R.drawable.check_mark_error);
        }
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
//        if (checkDialog == null) {
//            checkDialog = new CheckMarkDialog(context);
//        }
//        checkDialog.showCheck(isSuccess);
    }
}
