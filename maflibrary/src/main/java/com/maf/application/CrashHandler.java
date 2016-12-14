package com.maf.application;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.maf.utils.FileUtils;
import com.maf.utils.Lg;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by mzg on 2016/6/14.
 * 捕获全局异常
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private Context startContext;// 应用的启动界面，如果不设置，无法重启应用
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private static CrashHandler instance;

    private BaseApplication mContext;

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
    public void init(BaseApplication context) {
        mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
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
        if (!handleException(ex) && mDefaultHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            restartApp();
        }
    }

    /**
     * 重启app
     */
    public void restartApp() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        PendingIntent restartIntent = null;
        if (startContext != null) {
            Intent intent = new Intent(mContext, startContext.getClass());
            restartIntent = PendingIntent.getActivity(
                    mContext, 0, intent,
                    Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        //退出程序
        AlarmManager mgr = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        if (restartIntent != null) {
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 500,
                    restartIntent); // 1秒钟后重启应用
        } else {
            Lg.d("没有设置启动界面");
        }
        System.exit(1);
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
        printErrorLog(result);
        FileUtils.writeCrashLog(result);
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        saveThrowable(ex);
        //使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "很抱歉,程序出现异常,即将重启.",
                        Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();
        return true;
    }

    /**
     * 打印出错日志
     *
     * @param result
     */
    private void printErrorLog(String result) {
        Log.e("crashhandler", result);
    }
}
