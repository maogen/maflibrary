package com.maf.base.test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.Button;

import com.maf.utils.Lg;

/**
 * 项目名称：maflibrary
 * 类描述：
 * 创建人：mzg
 * 创建时间：2016/10/12 16:30
 * 修改人：mzg
 * 修改时间：2016/10/12 16:30
 * 修改备注：
 */
public class MyButton extends Button {
    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        Lg.d("onKeyDown");
        return false;
    }
}
