package com.maf.application;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Looper;

import com.maf.utils.FileUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by mzg on 2016/6/14.
 * 捕获全局异常
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static CrashHandler instance;
    private Context mContext;// 应用的Application
    private Context startContext;// 应用的启动界面，如果不设置，无法重启应用
//    private Thread.UncaughtExceptionHandler unCEHandler;

    /**
     * 私有化构造函数
     */
    private CrashHandler() {
    }

    /**
     * 单例初始化方法
     *
     * @return 单例实例对象
     */
    public static CrashHandler getInstance() {
        if (instance == null) {
            instance = new CrashHandler();
        }
        return instance;
    }

    /**
     * 初始化相关变量，一般在Application里面调用
     *
     * @param context 自定义Application
     */
    public void init(Context context) {
        mContext = context;
//        unCEHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 设置应用重启的启动Activity，如果不设置，出现崩溃时，无法重新启动应用
     *
     * @param _startContext
     */
    public void setStartContext(Context _startContext) {
        startContext = _startContext;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
//        Lg.d(TAG, "uncaughtException");
//        Lg.d(TAG, ex.getMessage());
//        ex.printStackTrace();
        saveThrowable(ex);
//        finishActivity();
        restartApp();

    }

    /**
     * 保存异常消息类
     *
     * @param ex 异常对象
     */
    private void saveThrowable(Throwable ex) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        FileUtils.writeCrashLog(result);
    }

    /**
     * 出错时关闭当前页面
     * 注：关闭当前界面后，应用还是会无响应
     */
    private void finishActivity() {
//        AppManager.getAppManager().currentActivity().finish();
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                new AlertDialog.Builder(ActivityManager.getInstance().currentActivity()).setTitle("提示").setCancelable(false)
                        .setMessage("程序发生错误...").setNeutralButton("点击关闭界面", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ActivityManager.getInstance().currentActivity().finish();
                    }
                }).create().show();
                Looper.loop();
            }
        }.start();
    }

    /**
     * 出错重新启动app
     */
    private void restartApp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                new AlertDialog.Builder(ActivityManager.getInstance().currentActivity()).setTitle("提示").setCancelable(false)
                        .setMessage("应用发生错误...重新启动应用...").setNeutralButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (startContext != null) {
                            Intent intent = new Intent(mContext, startContext.getClass());
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(intent);
                        }
                        ActivityManager.getInstance().exitApp(mContext);
                    }
                }).create().show();
                Looper.loop();
            }
        }).start();
    }
}
