package com.maf.utils;

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
     * 自定义toast显示时间(ms)
     *
     * @param text
     * @param duration toast的显示时间(ms)
     */
    public static void makeText(String text, int duration) {
        if (_toast != null) {
            // 如果_toast不是空，则先取消之前的toast
            _toast.cancel();
        }
        _toast = Toast.makeText(BaseApplication._application, text, duration);
        _toast.show();
    }
}
