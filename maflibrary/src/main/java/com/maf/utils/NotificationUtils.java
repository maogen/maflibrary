package com.maf.utils;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.maf.R;

/**
 * 项目名称：maflibrary
 * 类描述：创建通知栏工具类
 * 创建人：mzg
 * 创建时间：2017/1/6 10:47
 * 修改人：mzg
 * 修改时间：2017/1/6 10:47
 * 修改备注：
 */

public class NotificationUtils {
    /**
     * 创建通知栏
     *
     * @param context       context
     * @param iconId        图标id
     * @param title         通知栏标题
     * @param text          通知栏内容
     * @param activity      点击启动的界面，可以传null
     * @param isClickCancel 点击通知栏是否消失
     * @param notifyId      通知栏id
     */
    public static Notification createNotification(Context context, int iconId,
                                                  String title, String text,
                                                  Activity activity, boolean isClickCancel,
                                                  int notifyId) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent;
        if (activity != null) {
            Intent intent = new Intent(context, activity.getClass());
            pendingIntent = PendingIntent.getActivity(context, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
        } else {
            Intent intent = new Intent();
            pendingIntent = PendingIntent.getActivity(context, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
        }
        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(iconId);
        if (StringUtils.isNotEmpty(title)) {
            builder.setContentTitle(title);
        }
        if (StringUtils.isNotEmpty(text)) {
            builder.setContentText(text);
        }
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.getNotification();
//                notification.flags = Notification.FLAG_NO_CLEAR;// 点击清除全部按钮才会消失
//                notification.flags = Notification.FLAG_AUTO_CANCEL;// 滑动清除
        if (isClickCancel) {
            notification.flags |= Notification.FLAG_AUTO_CANCEL;// 通知栏点击后消失
        }
        manager.notify(notifyId, notification);
        return notification;
    }

    /**
     * 创建自定义通知栏
     *
     * @param context       context
     * @param remoteViews   自定义布局
     *                      自定义视图布局文件中，仅支持FrameLayout、LinearLayout、RelativeLayout三种布局控件
     *                      和AnalogClock、Chronometer、Button、ImageButton、
     *                      ImageView、ProgressBar、TextView、ViewFlipper、
     *                      ListView、GridView、StackView和AdapterViewFlipper这些显示控件，
     *                      不支持这些类的子类或Android提供的其他控件。
     *                      否则会引起ClassNotFoundException异常
     * @param activity      点击启动的界面，可以传null
     * @param isClickCancel 点击通知栏是否消失
     * @param notifyId      通知栏id
     */
    public static Notification createNotifyByView(Context context, RemoteViews remoteViews,
                                                  Activity activity, boolean isClickCancel,
                                                  int notifyId) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent;
        if (activity != null) {
            Intent intent = new Intent(context, activity.getClass());
            pendingIntent = PendingIntent.getActivity(context, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
        } else {
            Intent intent = new Intent();
            pendingIntent = PendingIntent.getActivity(context, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
        }
        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(R.drawable.maf_icon);
        builder.setContent(remoteViews);
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.getNotification();
//                notification.flags = Notification.FLAG_NO_CLEAR;// 点击清除全部按钮才会消失
//                notification.flags = Notification.FLAG_AUTO_CANCEL;// 滑动清除
        if (isClickCancel) {
            notification.flags |= Notification.FLAG_AUTO_CANCEL;// 通知栏点击后消失
        }
        manager.notify(notifyId, notification);
        return notification;
    }

    /**
     * 刷新通知栏
     *
     * @param context
     * @param notifyId
     * @param notification
     */
    public static void updateNotify(Context context, int notifyId, Notification notification) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(notifyId, notification);
    }
}
