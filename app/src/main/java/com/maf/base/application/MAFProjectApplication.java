package com.maf.base.application;

import android.content.Intent;

import com.maf.application.BaseApplication;
import com.maf.application.CrashHandler;
import com.maf.base.activity.MainActivity;
import com.maf.utils.FileUtils;

import io.vov.vitamio.Vitamio;

/**
 * Created by mzg on 2016/5/23.
 */
public class MAFProjectApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        startLeakCanary(true);
        // 工具类调用
        FileUtils.initDir();
        // 应用崩溃初始化
        CrashHandler.getInstance().init(this);
        // 初始化视频
        Vitamio.isInitialized(this);
    }
}
