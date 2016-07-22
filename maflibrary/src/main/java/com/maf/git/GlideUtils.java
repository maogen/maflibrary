package com.maf.git;

import android.os.Looper;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.maf.application.BaseApplication;

import org.xutils.common.util.LogUtil;

/**
 * 项目名称：maflibrary
 * 类描述： 显示图片的Glide工具类
 * 创建人：mzg
 * 创建时间：2016/7/22 14:36
 * 修改人：mzg
 * 修改时间：2016/7/22 14:36
 * 修改备注：
 */
public class GlideUtils {

    public void showImage(String url, ImageView imageView) {
        Glide.with(BaseApplication._application).load(url).into(imageView);
    }

    /**
     * 清楚图片缓存
     */
    public void clearImageDiskCache() {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                //只能在非主线程执行
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(BaseApplication._application).clearDiskCache();
                    }
                }).start();
            } else {
                Glide.get(BaseApplication._application).clearDiskCache();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清空内存缓存
     */
    public void clearImageMemoryCache() {
        try {
            Glide.get(BaseApplication._application).clearMemory();
        } catch (Exception e) {
            LogUtil.d("Glide清除内存缓存失败");
            e.printStackTrace();
        }
    }

}
