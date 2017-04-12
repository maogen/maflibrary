package com.maf.application;

import android.app.Activity;
import android.content.Context;

import java.util.Stack;

/**
 * Created by mzg on 2016/6/14.
 * 管理应用中所有继承BaseActivity的Activity
 */
public class ActivityManager {
    private static Stack<Activity> activityStack;
    private static ActivityManager instance;

    private ActivityManager() {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
    }

    /**
     * 单例初始化Activity管理堆栈
     *
     * @return
     */
    public static ActivityManager getInstance() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        removeActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            // activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                removeActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
        System.exit(0);
    }

    /**
     * 退出应用程序
     */
    public void exitApp(Context context) {
        try {
            finishAllActivity();
            android.app.ActivityManager activityMgr = (android.app.ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            System.exit(0);
        }
    }
}
