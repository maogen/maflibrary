package com.maf.base.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Telephony;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.maf.activity.BaseBackActivity;
import com.maf.utils.BaseToast;
import com.maf.utils.Lg;

import maf.com.mafproject.R;

/**
 * Created by mzg on 2016/5/23.
 * 测试BaseToast
 */
public class ToastActivity extends BaseBackActivity {
    // 申明Button控件
    private Button btnShort;
    private Button btnLong;
    private Button btnDuration;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_toast;
    }

    @Override
    protected void initView() {
        btnShort = (Button) findViewById(R.id.btn_show_short);
        btnLong = (Button) findViewById(R.id.btn_show_long);
        btnDuration = (Button) findViewById(R.id.btn_show_duration);
    }

    @Override
    protected void initEvent() {
        btnShort.setOnClickListener(this);
        btnLong.setOnClickListener(this);
        btnDuration.setOnClickListener(this);
    }

    @Override
    protected void initValue() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_show_short:
                BaseToast.makeText("Toast Short Test", Toast.LENGTH_SHORT);
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
                Lg.d(inserted.toString());
                break;
            case R.id.btn_show_long:
                BaseToast.makeText("Toast Long Test", Toast.LENGTH_LONG);
//                CheckMarDialogUtils.showCheck(this, true);
                break;
            case R.id.btn_show_duration:
//                BaseToast.makeText("Toast Duration", 2000);
//                CheckMarDialogUtils.showCheck(this, false);
                BaseToast.makeText("Toast Duration", 2000);
                break;
            default:
                break;
        }
    }

}
