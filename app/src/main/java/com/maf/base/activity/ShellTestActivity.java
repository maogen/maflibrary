package com.maf.base.activity;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.maf.activity.BaseActivity;
import com.maf.activity.BaseTitleActivity;
import com.maf.base.util.ShellUtil;
import com.maf.utils.BaseToast;
import com.maf.utils.Lg;
import com.maf.utils.ScreenUtils;

import maf.com.mafproject.R;

/**
 * 项目名称：maflibrary
 * 类描述：
 * 创建人：zgmao
 * 创建时间：2017/8/16
 * 修改人：zgmao
 * 修改时间：2017/8/16
 * 修改备注：
 * Created by zgmao on 2017/8/16.
 */
public class ShellTestActivity extends BaseActivity {
    private LinearLayout view;
    private Button btn_click;
    private Button btn_shell;


    @Override
    protected int getLayoutResId() {
        return R.layout.layout_shell_test;
    }

    @Override
    protected void initView() {
        view = (LinearLayout) findViewById(R.id.view);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Lg.d("X:" + event.getX() + ";Y:" + event.getY());
                return false;
            }
        });
        btn_shell = (Button) findViewById(R.id.btn_shell);
        btn_click = (Button) findViewById(R.id.btn_click);
    }

    @Override
    protected void initEvent() {

        btn_shell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = ScreenUtils.getScreenWidth(mContext) / 2;
                int y = ScreenUtils.getScreenHeight(mContext) / 2;
                ShellUtil.execShellCmd("input tap " + x + " " + y);
            }
        });
        btn_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseToast.makeTextShort("点击了按钮");
            }
        });
    }

    @Override
    protected void initValue() {

    }

    @Override
    public void onClick(View v) {

    }
}
