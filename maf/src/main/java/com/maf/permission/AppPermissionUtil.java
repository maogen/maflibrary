package com.maf.permission;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;

import com.maf.utils.Lg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名称：Ytb_Android
 * 类描述：判断App的权限工具类
 * 创建人：mzg
 * 创建时间：2016/8/29 14:40
 * 修改人：mzg
 * 修改时间：2016/8/29 14:40
 * 修改备注：
 */
public class AppPermissionUtil {


    // 所需的主要权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.USE_FINGERPRINT,
            Manifest.permission.INTERNET,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    /**
     * 保存权限是否成功的map
     */
    static Map<String, Boolean> permissionOkMap;

    /**
     * 检查版本号
     */
    public static void checkPermission(Activity activity, int requestCode) {
        if (permissionOkMap == null) {
            permissionOkMap = new HashMap<>();
        }
        for (int i = 0; i < PERMISSIONS.length; i++) {
            int permissionResult = PermissionChecker.checkSelfPermission(activity,
                    PERMISSIONS[i]);
            Lg.d("获取的权限值是：" + permissionResult);
            if (permissionResult != PackageManager.PERMISSION_GRANTED) {
                // 没有获取权限
                permissionOkMap.put(PERMISSIONS[i], false);
                // 缺少权限，请求还未获取的权限
                ActivityCompat.requestPermissions(activity,
                        getNoPermission(), requestCode);
                Lg.d("缺少权限：" + PERMISSIONS[i]);
                break;
            } else {
                // 权限获取成功，保存
                permissionOkMap.put(PERMISSIONS[i], true);
            }
        }
    }

    /**
     * 根据map值，得到没有获取成功的权限数组
     *
     * @return 没有获取成功的权限数组
     */
    private static String[] getNoPermission() {
        if (permissionOkMap == null) {
            return getNoPermission();
        }
        List<String> list = new ArrayList<>();
        for (int i = 0; i < PERMISSIONS.length; i++) {
            String permission = PERMISSIONS[i];
            if (!permissionOkMap.containsKey(permission)) {
                // 还没有判断该权限，插入list中
                list.add(permission);
            } else if (!permissionOkMap.get(permission)) {
                // 权限为false，插入到list中
                list.add(permission);
            }
        }
        return list.toArray(new String[list.size()]);
    }

}
