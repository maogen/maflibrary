package com.maf.base.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.maf.activity.BaseBackActivity;
import com.maf.dialog.CheckMarDialogUtils;
import com.maf.dialog.TakePhotoDialogUtils;
import com.maf.utils.BaseToast;
import com.maf.utils.ImageUtils;
import com.maf.utils.LogUtils;

import java.io.File;

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
                BaseToast.makeTextShort("Toast Short Test");
                // 选择照相还是相册
                TakePhotoDialogUtils.showTakePictureDialog(this);
                break;
            case R.id.btn_show_long:
                BaseToast.makeTextLong("Toast Long Test");
                CheckMarDialogUtils.showCheck(this, true);
                break;
            case R.id.btn_show_duration:
                BaseToast.makeText("Toast Duration", 3000);
                CheckMarDialogUtils.showCheck(this, false);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case ImageUtils.TAKE_PHOTO_FROM_CAMERA:
                // 从相机界面返回
                if (resultCode == RESULT_OK) {
                    LogUtils.d("拍照的照片：" + TakePhotoDialogUtils.imagePath);
                }
                break;
            case ImageUtils.TAKE_PHOTO_FROM_ALBUM:
                // 从相册界面返回
                if (resultCode == RESULT_OK) {
                    String imgPath = ImageUtils.getImageAbsolutePath(this, data.getData());
                    LogUtils.d("选择的照片：" + imgPath);
                }
                break;
        }
    }
}
