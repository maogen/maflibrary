package com.artifex.mupdf;

import android.content.Context;
import android.widget.ImageView;

/**
 * 项目名称：maflibrary
 * 类描述：
 * 创建人：mzg
 * 创建时间：2017/2/10 12:00
 * 修改人：mzg
 * 修改时间：2017/2/10 12:00
 * 修改备注：
 */

public class OpaqueImageView extends ImageView {

    public OpaqueImageView(Context context) {
        super(context);
    }

    @Override
    public boolean isOpaque() {
        return true;
    }
}