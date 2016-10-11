package com.maf.base.application;

import com.maf.application.BaseApplication;
import com.maf.application.CrashHandler;
import com.maf.utils.FileUtils;

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
//        CrashHandler.getInstance().init(this);

    }
}
