package com.maf.base.application;

import com.maf.application.BaseApplication;

/**
 * Created by mzg on 2016/5/23.
 */
public class MAFProjectApplication extends BaseApplication {


    @Override
    public void onCreate() {
        startLeakCanary(true);
        super.onCreate();
    }
}
