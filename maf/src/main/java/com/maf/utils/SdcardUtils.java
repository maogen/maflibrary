package com.maf.utils;

import android.content.Context;
import android.os.storage.StorageManager;

import com.maf.application.BaseApplication;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mzg on 2016/5/23.
 * sdcard工具类
 */
public class SdcardUtils {
    /**
     * 得到sdcard的路径列表，当手机sdcard不止一个时
     *
     * @return 路径列表
     */
    public static List<String> getSdcardPath() {
        List<String> pathList = new ArrayList<>();
        StorageManager manager = (StorageManager) BaseApplication._application.getSystemService(Context.STORAGE_SERVICE);
        try {
            String[] paths = (String[]) manager.getClass().getMethod("getVolumePaths", new Class[0]).invoke(manager, new Object[]{});
            for (int i = 0; i < paths.length; i++) {
                pathList.add(paths[i]);
            }
        } catch (NoSuchMethodException exception) {
            exception.printStackTrace();
        } catch (InvocationTargetException exception) {
            exception.printStackTrace();
        } catch (IllegalAccessException exception) {
            exception.printStackTrace();
        }
        return pathList;
    }

    /**
     * 获取默认的sdcard根路径
     *
     * @return sdcard根路径
     */
    public static String getRootPath() {
        List<String> paths = getSdcardPath();
        if (paths != null && paths.size() > 0) {
            return paths.get(0) + File.separator;
        }
        return "";
    }
}
