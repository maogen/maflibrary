package com.maf.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.ImageView;

import com.maf.R;


/**
 * 项目名称：Ytb_Android
 * 类描述：动画对号框
 * 创建人：mzg
 * 创建时间：2016/8/19 11:48
 * 修改人：mzg
 * 修改时间：2016/8/19 11:48
 * 修改备注：
 */
public class CheckMarkDialog extends Dialog {
    private ImageView imageView;

    public CheckMarkDialog(Context context) {
        this(context, R.style.customDialogNoBG);
    }

    public CheckMarkDialog(Context context, int themeResId) {
        super(context, themeResId);
        setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        Window dialogWindow = getWindow();
        dialogWindow.setWindowAnimations(R.style.DialogShowStyle);// 设置显示隐藏动画
        setContentView(R.layout.layout_check_mark);

        imageView = (ImageView) findViewById(R.id.image_check);
    }


    /**
     * 显示对话框
     */
    public void showCheck(boolean isSuccess) {
        show();
        if (isSuccess) {
            imageView.setImageResource(R.drawable.check_mark_success);
        } else {
            imageView.setImageResource(R.drawable.check_mark_error);
        }
        Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                dismiss();
                return false;
            }
        });
        handler.sendEmptyMessageDelayed(0, 800);
    }
}
