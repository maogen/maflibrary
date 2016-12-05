package com.maf.activity;

import android.os.Bundle;

import com.maf.R;
import com.maf.views.TitleBarView;


/**
 * 项目名称：Ytb_Android
 * 类描述：包含标题的公用界面，需要在布局中包含 <include layout="@layout/layout_titlebar_base_view" />
 * 创建人：mzg
 * 创建时间：2016/8/18 9:27
 * 修改人：mzg
 * 修改时间：2016/8/18 9:27
 * 修改备注：
 */
public abstract class BaseTitleActivity extends BaseActivity {
    protected TitleBarView titleBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitle();
        initTitleView();

    }

    /**
     * 初始化标题
     */
    private void initTitle() {
        titleBarView = (TitleBarView) findViewById(R.id.view_title_background);
    }

    /**
     * 初始化抬头相关信息
     */
    protected abstract void initTitleView();
}
