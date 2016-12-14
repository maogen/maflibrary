package com.maf.base.activity;

import android.view.View;

import com.maf.activity.BaseTitleActivity;
import com.maf.utils.BaseToast;
import com.maf.views.SlideSwitch;

import maf.com.mafproject.R;

/**
 * 项目名称：maflibrary
 * 类描述：测试切换控件
 * 创建人：mzg
 * 创建时间：2016/12/14 15:33
 * 修改人：mzg
 * 修改时间：2016/12/14 15:33
 * 修改备注：
 */

public class SlideSwitchActivity extends BaseTitleActivity {
    private SlideSwitch slideOne;
    private SlideSwitch slideTwo;
    private SlideSwitch slideThree;
    private SlideSwitch slideFour;

    @Override
    protected void initTitleView() {
        titleBarView.setTitle("测试切换控件");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_slide_switch;
    }

    @Override
    protected void initView() {
        slideOne = (SlideSwitch) findViewById(R.id.switch_one);

        slideTwo = (SlideSwitch) findViewById(R.id.switch_two);
        slideTwo.setStatus(false);
        slideTwo.setNotSwitch();

        slideThree = (SlideSwitch) findViewById(R.id.switch_three);
        slideThree.setText("关", "开");
        slideFour = (SlideSwitch) findViewById(R.id.switch_four);
        slideFour.setText("关", "开");
    }

    @Override
    protected void initEvent() {
        slideOne.setOnSwitchChangedListener(new SlideSwitch.OnSwitchChangedListener() {
            @Override
            public void onSwitchChanged(SlideSwitch obj, int status) {
                if (status == SlideSwitch.SWITCH_OFF) {
                    BaseToast.makeTextShort("打开");
                } else {
                    BaseToast.makeTextShort("关闭");
                }
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
