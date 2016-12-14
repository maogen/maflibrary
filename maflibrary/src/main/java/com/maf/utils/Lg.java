package com.maf.utils;

import android.util.Log;

/**
 * Created by mzg on 2016/5/23.
 * log工具，可以设置是否显示log
 */
public class Lg {
    private static String TAG = "MAF";
    private static boolean _isShowLog = true;

    /**
     * 设置是否显示log
     *
     * @param isShowLog
     */
    public static void setIsShowLog(boolean isShowLog) {
        _isShowLog = isShowLog;
    }

    /**
     * 显示Debug log
     *
     * @param log
     */
    public static void d(String log) {
        if (_isShowLog) {
            Log.d(TAG, log);
        }
    }

    /**
     * 显示Errorlog
     *
     * @param log
     */
    public static void e(String log) {
        if (_isShowLog) {
            Log.e(TAG, log);
        }
    }

    /**
     * 显示Verbose log
     *
     * @param log
     */
    public static void v(String log) {
        if (_isShowLog) {
            Log.v(TAG, log);
        }
    }

}
