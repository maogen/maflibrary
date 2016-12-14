package com.maf.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.maf.utils.Lg;

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
}
