package com.maf.base.xposed;

import android.location.Location;

import java.util.Random;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
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
        HookGps(loadPackageParam);
    }

    private XC_MethodHook xc_methodHook;

    private void HookGps(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        xc_methodHook = new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Location location = (Location) param.args[0];
                XposedBridge.log("实际 系统 经度" + location.getLatitude() + " 系统 纬度" + location.getLongitude() + "系统 加速度 " + location.getAccuracy());
                XSharedPreferences xsp = new XSharedPreferences("com.markypq.gpshook", "markypq");
                if (xsp.getBoolean("enableHook", true)) {
                    double latitude = Double.valueOf(xsp.getString("lan", "117.536246")) + (double) new Random().nextInt(1000) / 1000000;
                    double longtitude = Double.valueOf(xsp.getString("lon", "36.681752")) + (double) new Random().nextInt(1000) / 1000000;
                    location.setLongitude(longtitude);
                    location.setLatitude(latitude);
                    XposedBridge.log("hook 系统 经度" + location.getLatitude() + " 系统 纬度" + location.getLongitude() + "系统 加速度 " + location.getAccuracy());

                }
            }
        };
        XposedHelpers.findAndHookMethod("com.android.server.LocationManagerService",
                loadPackageParam.classLoader,
                "reportLocation",
                Location.class,
                boolean.class,
                xc_methodHook);
    }
}
