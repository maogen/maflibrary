package com.maf.base.activity;

import android.app.Notification;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Telephony;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

import com.maf.activity.BaseTitleActivity;
import com.maf.utils.BaseToast;
import com.maf.utils.Lg;
import com.maf.utils.NotificationUtils;

import maf.com.mafproject.R;

/**
 * 项目名称：maflibrary
 * 类描述：系统测试界面
 * 创建人：mzg
 * 创建时间：2017/1/6 9:49
 * 修改人：mzg
 * 修改时间：2017/1/6 9:49
 * 修改备注：
 */

public class SystemTestActivity extends BaseTitleActivity implements Handler.Callback {
    private Button btnInsertSms;
    private Button btnCreateNotify;
    private Button btnCustomNotify;

    private Handler handler;

    @Override
    protected void initTitleView() {
        titleBarView.setTitle("测试系统相关内容");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_system_test;
    }

    @Override
    protected void initView() {
        btnInsertSms = (Button) findViewById(R.id.btn_insert_sms);
        btnCreateNotify = (Button) findViewById(R.id.btn_create_notify);
        btnCustomNotify = (Button) findViewById(R.id.btn_custom_notify);
        initNotifyView();
    }

    @Override
    protected void initEvent() {
        btnInsertSms.setOnClickListener(this);
        btnCreateNotify.setOnClickListener(this);
        btnCustomNotify.setOnClickListener(this);
    }

    @Override
    protected void initValue() {
        handler = new Handler(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_insert_sms:
                // 往短信数据库插入一条短信
                insertSms();
                break;
            case R.id.btn_create_notify:
                // 创建一条通知栏
                NotificationUtils.createNotification(this, R.drawable.maf_icon,
                        "新消息", "您有一条新消息", null, true, 0);
                break;
            case R.id.btn_custom_notify:
                // 创建自定义通知栏
                notifyCustom = NotificationUtils.createNotifyByView(this, remoteViews, null, true, notifyId);
                // 开始发送更新消息
                progress = 0;
                setProgressValue(progress);
                handler.sendEmptyMessage(0);
                break;
            default:
                break;
        }
    }

    private Notification notifyCustom;
    private int notifyId = 1;
    private RemoteViews remoteViews;
    private int progress;

    /**
     * 初始化通知栏
     */
    private void initNotifyView() {
        remoteViews = new RemoteViews(getPackageName(), R.layout.layout_notification_bar);
        remoteViews.setInt(R.id.progressBar, "setMax", 100);// 设置ProgressBar最大值
    }

    /**
     * 设置当前进度
     *
     * @param progress progress
     */
    private void setProgressValue(int progress) {
        if (progress >= 100) {
            // 下载已经结束
            remoteViews.setInt(R.id.progressBar, "setProgress", 100);
            remoteViews.setCharSequence(R.id.text_progress, "setText", "下载完成");
            NotificationUtils.updateNotify(this, notifyId, notifyCustom);
        } else {
            remoteViews.setInt(R.id.progressBar, "setProgress", progress);
            remoteViews.setInt(R.id.progressBar, "setSecondaryProgress", progress + 10);
            remoteViews.setCharSequence(R.id.text_progress, "setText", "正在下载..." + progress + "%");
            handler.sendEmptyMessageDelayed(0, 500);
            NotificationUtils.updateNotify(this, notifyId, notifyCustom);
        }
    }

    @Override
    public boolean handleMessage(Message message) {
        switch (message.what) {
            case 0:
                progress = progress + 3;
                setProgressValue(progress);
                break;
            default:
                break;
        }
        return false;
    }

    /**
     * 插入短信
     */
    private void insertSms() {
        if (Build.VERSION.SDK_INT >= 19) {
            String defaultSmsPkg = Telephony.Sms.getDefaultSmsPackage(this);
            String mySmsPkg = this.getPackageName();
            if (!defaultSmsPkg.equals(mySmsPkg)) {
                //            如果这个App不是默认的Sms App，则修改成默认的SMS APP
                //            因为从Android 4.4开始，只有默认的SMS APP才能对SMS数据库进行处理
                Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
                intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, mySmsPkg);
                startActivity(intent);
            }
        }

        ContentValues values = new ContentValues();
                /* 手机号 */
        values.put("address", "15855121592");
                /* 时间 */
        values.put("date", System.currentTimeMillis());
        values.put("read", 0);
//                values.put("status", -1);
                /* 类型1为收件箱，2为发件箱 */
        values.put("type", 2);
                /* 短信体内容 */
        values.put("body", "测试插入一条短信");
                /* 插入数据库操作 */
        Uri inserted = getContentResolver().insert(Uri.parse("content://sms/sent"),
                values);
        if (inserted != null) {
            Lg.d(inserted.toString());
            BaseToast.makeTextShort("插入一条短信成功");
        } else {
            BaseToast.makeTextShort("插入一条短信失败");
        }
    }
}
