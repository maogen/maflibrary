package com.maf.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.maf.application.BaseApplication;

/**
 * Created by mzg on 2016/5/23.
 * 显示Toast，可以避免快速重复弹出
 */
public class BaseToast {
    private static Toast _toast;

    /**
     * 短暂的显示Toast
     *
     * @param text
     */
    public static void makeTextShort(String text) {
        if (_toast != null) {
            // 如果_toast不是空，则先取消之前的toast
            _toast.cancel();
        }
        _toast = Toast.makeText(BaseApplication._application, text, Toast.LENGTH_SHORT);
        _toast.show();
    }

    /**
     * 短暂的显示Toast，文本内容居中
     *
     * @param text
     */
    public static void makeTextShortCenter(String text) {
        if (_toast != null) {
            // 如果_toast不是空，则先取消之前的toast
            _toast.cancel();
        }
        _toast = Toast.makeText(BaseApplication._application, text, Toast.LENGTH_SHORT);
        _toast.setGravity(Gravity.CENTER, 0, 0);
        _toast.show();
    }

    /**
     * 较长时间显示toast
     *
     * @param text
     */
    public static void makeTextLong(String text) {
        if (_toast != null) {
            // 如果_toast不是空，则先取消之前的toast
            _toast.cancel();
        }
        _toast = Toast.makeText(BaseApplication._application, text, Toast.LENGTH_LONG);
        _toast.show();
    }

    /**
     * 较长时间显示toast，文本内容居中
     *
     * @param text
     */
    public static void makeTextLongCenter(String text) {
        if (_toast != null) {
            // 如果_toast不是空，则先取消之前的toast
            _toast.cancel();
        }
        _toast = Toast.makeText(BaseApplication._application, text, Toast.LENGTH_LONG);
        _toast.setGravity(Gravity.CENTER, 0, 0);
        _toast.show();
    }

    /**
     * 自定义toast显示时间(ms)
     *
     * @param text
     * @param duration toast的显示时间(ms，最大不会超过3500ms)
     */
    public static void makeText(String text, int duration) {
        if (_toast != null) {
            // 如果_toast不是空，则先取消之前的toast
            _toast.cancel();
        }
        if(duration == Toast.LENGTH_SHORT){
            _toast = Toast.makeText(BaseApplication._application, text, Toast.LENGTH_SHORT);
        }else if(duration == Toast.LENGTH_LONG){
            _toast = Toast.makeText(BaseApplication._application, text, Toast.LENGTH_LONG);
        }else {
            _toast = Toast.makeText(BaseApplication._application, text, Toast.LENGTH_LONG);
            cancelToast(duration);
        }
        _toast.show();

    }

    private static Handler handler;

    /**
     * 取消之前的toast
     */
    private static void cancelToast(int duration) {
        if (handler == null) {
            handler = new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message message) {
                    if (_toast != null) {
                        // 如果_toast不是空，则先取消之前的toast
                        _toast.cancel();
                    }
                    return false;
                }
            });
            handler.sendEmptyMessageDelayed(0, duration);
        }
    }

    /**
     * 显示自定义View的Toast
     *
     * @param context
     * @param view
     */
    public static void showView(Context context, View view) {
        if (_toast == null) {
            _toast = new Toast(context);
            _toast.setGravity(Gravity.CENTER, 0, 0);
            _toast.setDuration(Toast.LENGTH_SHORT);
            _toast.setView(view);
            _toast.show();
        } else {
            _toast.cancel();
            _toast = new Toast(context);
            _toast.setGravity(Gravity.CENTER, 0, 0);
            _toast.setDuration(Toast.LENGTH_SHORT);
            _toast.setView(view);
            _toast.show();
        }
    }
}
