package com.maf.base.activity;

import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.maf.activity.BaseActivity;

import maf.com.mafproject.R;

/**
 * 项目名称：maflibrary
 * 类描述：
 * 创建人：mzg
 * 创建时间：2016/7/19 11:33
 * 修改人：mzg
 * 修改时间：2016/7/19 11:33
 * 修改备注：
 */
public class FingerprintActivity extends BaseActivity {
    private FingerprintManagerCompat manager;

    private Button btnStart;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_fingerprint;
    }

    @Override
    protected void initView() {
        btnStart = (Button) findViewById(R.id.btn_start);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initValue() {
        manager = FingerprintManagerCompat.from(this);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.authenticate(null, 0, null, new MyCallBack(), null);
            }
        });

    }

    @Override
    public void onClick(View v) {

    }

    public class MyCallBack extends FingerprintManagerCompat.AuthenticationCallback {
        private static final String TAG = "MyCallBack";

        // 当出现错误的时候回调此函数，比如多次尝试都失败了的时候，errString是错误信息
        @Override
        public void onAuthenticationError(int errMsgId, CharSequence errString) {
            Log.d(TAG, "onAuthenticationError: " + errString);
            Toast.makeText(FingerprintActivity.this, "onAuthenticationError: " + errString, Toast.LENGTH_LONG).show();
        }

        // 当指纹验证失败的时候会回调此函数，失败之后允许多次尝试，失败次数过多会停止响应一段时间然后再停止sensor的工作
        @Override
        public void onAuthenticationFailed() {
            Log.d(TAG, "onAuthenticationFailed: " + "验证失败");
            Toast.makeText(FingerprintActivity.this, "onAuthenticationFailed: " + "验证失败", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
            Log.d(TAG, "onAuthenticationHelp: " + helpString);
            Toast.makeText(FingerprintActivity.this, "onAuthenticationHelp: " + helpString, Toast.LENGTH_LONG).show();
        }

        // 当验证的指纹成功时会回调此函数，然后不再监听指纹sensor
        @Override
        public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult
                                                      result) {
            Log.d(TAG, "onAuthenticationSucceeded: " + "验证成功");
            Toast.makeText(FingerprintActivity.this, "onAuthenticationSucceeded: " + "验证成功", Toast.LENGTH_LONG).show();
        }
    }
}
