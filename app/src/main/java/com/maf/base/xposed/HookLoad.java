package com.maf.base.xposed;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * 项目名称：maflibrary
 * 类描述：xposed接口实现
 * 创建人：zgmao
 * 创建时间：2017/5/19
 * 修改人：Administrator
 * 修改时间：2017/5/19
 * 修改备注：
 * Created by Administrator on 2017/5/19.
 */

public class HookLoad implements IXposedHookLoadPackage {
    /**
     * 该类覆盖了接口中的handleLoadPackage方法，
     * 通过对该方法中的参数loadPackageParam执行某些操作可对实际运行的该apk进行劫持，
     * 这里我们只是简单地打印包名。
     *
     * @param loadPackageParam
     * @throws Throwable
     */
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        // 打印加载的apk程序包名
        XposedBridge.log("Launch app: " + loadPackageParam.packageName);
    }
}
