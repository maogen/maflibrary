package com.maf.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

/**
 * 项目名称：maflibrary
 * 类描述：
 * 创建人：mzg
 * 创建时间：2016/12/14 15:59
 * 修改人：mzg
 * 修改时间：2016/12/14 15:59
 * 修改备注：
 */

public class BitmapUtils {
    /**
     * 将原始的bitmap进行缩放
     *
     * @param originBitmap 原始的bitmap
     * @param width        缩放后的宽
     * @param height       缩放后的高
     * @return
     */
    public static Bitmap scaleBitmap(Bitmap originBitmap, int width, int height) {
        int originWidth = originBitmap.getWidth();
        int originHeight = originBitmap.getHeight();
        float scaleWidth = (float) width / originWidth;
        float scaleHeight = (float) height / originHeight;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
//        Lg.d("原始尺寸：" + originWidth + ";" + originHeight);
//        Lg.d("自定义尺寸：" + width + ";" + height);
//        Lg.d("缩放比例：" + scaleWidth + ";" + scaleHeight);
        return Bitmap.createBitmap(originBitmap, 0, 0, originWidth, originHeight, matrix, true);
    }

    /**
     * 将两张bitmap合并成一个
     *
     * @param background
     * @param foreground
     * @return
     */
    public static Bitmap toConformBitmap(Bitmap background, Bitmap foreground) {
        if (background == null) {
            return null;
        }

        int bgWidth = background.getWidth();
        int bgHeight = background.getHeight();
        //int fgWidth = foreground.getWidth();
        //int fgHeight = foreground.getHeight();
        //create the new blank bitmap 创建一个新的和SRC长度宽度一样的位图
        Bitmap newBmp = Bitmap.createBitmap(bgWidth, bgHeight, Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(newBmp);
        //draw bg into
        cv.drawBitmap(background, 0, 0, null);//在 0，0坐标开始画入bg
        //draw fg into
        cv.drawBitmap(foreground, 0, 0, null);//在 0，0坐标开始画入fg ，可以从任意位置画入
        //save all clip
        cv.save(Canvas.ALL_SAVE_FLAG);//保存
        //store
        cv.restore();//存储
        return newBmp;
    }
}
