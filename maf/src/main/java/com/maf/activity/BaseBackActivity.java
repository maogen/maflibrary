package com.maf.activity;

/**
 * Created by mzg on 2016/5/23.
 * 继承BaseActivity，实现按返回键finish界面
 */
public abstract class BaseBackActivity extends BaseActivity {
    @Override
    public void onBackPressed() {
        finish();
    }
}
