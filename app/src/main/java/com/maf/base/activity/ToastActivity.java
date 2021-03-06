package com.maf.base.activity;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.maf.activity.BaseBackActivity;
import com.maf.utils.BaseToast;

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
