package com.artifex.mupdf;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * 项目名称：maflibrary
 * 类描述：
 * 创建人：mzg
 * 创建时间：2017/2/10 12:01
 * 修改人：mzg
 * 修改时间：2017/2/10 12:01
 * 修改备注：
 */

public class PatchInfo {
    public Bitmap bm;
    public Point patchViewSize;
    public Rect patchArea;

    public PatchInfo(Bitmap aBm, Point aPatchViewSize, Rect aPatchArea) {
        bm = aBm;
        patchViewSize = aPatchViewSize;
        patchArea = aPatchArea;
    }
}