package com.maf.permission;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;

import com.maf.utils.LogUtils;

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
            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.WRITE_SETTINGS,
            Manifest.permission.CAMERA,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    /**
     * 检查版本号
     */
    public static void checkPermission(Activity activity, int requestCode) {
        for (int i = 0; i < PERMISSIONS.length; i++) {
            if (PermissionChecker.checkSelfPermission(activity,
                    PERMISSIONS[i]) != PackageManager.PERMISSION_GRANTED) {
                // 缺少权限
                ActivityCompat.requestPermissions(activity,
                        PERMISSIONS, requestCode);
                LogUtils.d("缺少权限：" + PERMISSIONS[i]);
                break;
            }
        }
    }
}
