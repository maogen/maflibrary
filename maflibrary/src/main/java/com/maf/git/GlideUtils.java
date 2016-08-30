package com.maf.git;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Looper;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.maf.application.BaseApplication;
import com.maf.utils.StringUtils;

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

    /**
     * 加载网络图片
     *
     * @param context   由于存在生命周期问题，一般是Activity
     * @param url       网络图片地址
     * @param imageView 图片控件
     */
    public static void loadImage(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

    /**
     * 加载本地图片
     *
     * @param context    由于存在生命周期问题，一般是Activity
     * @param resourceId 图片id
     * @param imageView  图片控件
     */
    public static void loadImage(Context context, int resourceId, ImageView imageView) {
        Glide.with(context).load(resourceId).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

    /**
     * 圆角加载网络图片
     *
     * @param context   由于存在生命周期问题，一般是Activity
     * @param url       图片地址
     * @param imageView 图片控件
     */
    public static void loadImageRound(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).transform(new GlideRoundTransform(context, 10)).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

    /**
     * 圆角加载本地图片
     *
     * @param context    由于存在生命周期问题，一般是Activity
     * @param resourceId 图片id
     * @param imageView  图片控件
     */
    public static void loadImageRound(Context context, int resourceId, ImageView imageView) {
        Glide.with(context).load(resourceId).transform(new GlideRoundTransform(context, 10)).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

    /**
     * 加载圆形图片，图片来源网络
     *
     * @param context   由于存在生命周期问题，一般是Activity
     * @param url       图片地址
     * @param imageView 图片控件
     */
    public static void loadImageCircle(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).transform(new CircleTransform(context)).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

    /**
     * 加载圆形图片，图片来源本地
     *
     * @param context    由于存在生命周期问题，一般是Activity
     * @param resourceId 图片地址id
     * @param imageView  图片控件
     */
    public static void loadImageCircle(Context context, int resourceId, ImageView imageView) {
        Glide.with(context).load(resourceId).transform(new CircleTransform(context)).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

    /**
     * 加载常规图片，图片长宽自适应
     *
     * @param context   由于存在生命周期问题，一般是Activity
     * @param url       图片地址
     * @param imageView 图片控件
     */
    public static void loadImageGetSize(Context context, String url, final ImageView imageView) {
        if (StringUtils.isEmpty(url)) {
            return;
        }
        if (imageView == null) {
            return;
        }
        Glide.with(context).load(url).asBitmap().
                into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        imageView.setImageBitmap(resource);
                    }
                });
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
