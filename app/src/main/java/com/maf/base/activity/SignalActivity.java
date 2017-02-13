package com.maf.base.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.maf.activity.BaseTitleActivity;
import com.maf.utils.BitmapUtils;
import com.maf.utils.Lg;
import com.maf.views.CustomLinePathView;

import maf.com.mafproject.R;

/**
 * 项目名称：maflibrary
 * 类描述：签名界面（横屏）
 * 创建人：mzg
 * 创建时间：2017/2/9 14:49
 * 修改人：mzg
 * 修改时间：2017/2/9 14:49
 * 修改备注：
 */

public class SignalActivity extends BaseTitleActivity {
    private CustomLinePathView pathView;

    private ImageView imageView;

    @Override
    protected void initTitleView() {
        titleBarView.setTitle("签名");
        titleBarView.setOnMenuLeftClick(0, R.drawable.icon_check, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 清楚按钮
                pathView.clear();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 确定按钮
                Bitmap bitmap = pathView.getCachebBitmap();
//                Lg.d("签字图片大小Height：" + bitmap.getHeight() + ";Width：" + bitmap.getWidth());
                Bitmap newBitMap = BitmapUtils.scaleBitmap(bitmap, 200, 100);
                imageView.setImageBitmap(newBitMap);
                Intent intent = new Intent();
                intent.putExtra("bitmap", newBitMap);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_signal;
    }

    @Override
    protected void initView() {
        pathView = (CustomLinePathView) findViewById(R.id.pathView);
        imageView = (ImageView) findViewById(R.id.imageView);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initValue() {
    }

    @Override
    public void onClick(View v) {

    }
}
