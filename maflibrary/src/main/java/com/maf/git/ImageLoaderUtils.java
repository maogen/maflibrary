package com.maf.git;

import android.widget.ImageView;

import com.maf.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by mzg on 2016/5/23.
 * 1、ImageLoader git地址是：https://github.com/nostra13/Android-Universal-Image-Loader
 * 2、当前版本是1.9.5
 * 3、使用之前在Application中进行全局初始化
 */
public class ImageLoaderUtils {
    /**
     * 加载图片的loader
     */
    private static ImageLoader imageLoader;
    private static DisplayImageOptions options;

    /**
     * 初始化
     */
    private static void init() {
        if (imageLoader == null) {
            imageLoader = ImageLoader.getInstance();
            options = new DisplayImageOptions.Builder()
                    .showImageForEmptyUri(R.drawable.ic_launcher)
                    .showImageOnFail(R.drawable.ic_launcher).build();
        }
    }

    /**
     * 显示本地图片
     *
     * @param imageView 需要显示图片的控件
     * @param filePath  图片的sdcard路径
     */
    public static void showLocalImage(ImageView imageView, String filePath) {
        init();
        imageLoader.displayImage("file:///" + filePath, imageView, options);
    }

    /**
     * 显示Drawable目录的图片
     *
     * @param imageView  需要显示图片的控件
     * @param drawableId drawable图片的id
     */
    public static void showDrawableImage(ImageView imageView, int drawableId) {
        init();
        imageLoader.displayImage("drawable://" + drawableId, imageView, options);
    }

    /**
     * 显示Assets目录下的图片
     *
     * @param imageView  需要显示图片的控件
     * @param assetsPath assets文件路径
     */
    public static void showAssetsImage(ImageView imageView, String assetsPath) {
        init();
        imageLoader.displayImage("assets://" + assetsPath, imageView, options);
    }

    /**
     * 显示网络图片
     *
     * @param imageView 需要显示图片的控件
     * @param url       网路图片url
     */
    public static void showUrlImage(ImageView imageView, String url) {
        init();
        imageLoader.displayImage(url, imageView, options);
    }
}
