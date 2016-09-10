package com.maf.dialog;

import android.app.Dialog;
import android.content.Context;

import com.maf.R;


/**
 * 项目名称：Ytb_Android
 * 类描述：圆角dialog
 * 创建人：gk
 * 创建时间：2016/7/21 9:31
 * 修改人：gk
 * 修改时间：2016/7/21 9:31
 * 修改备注：
 */
public class FilletDialog extends Dialog {
    public FilletDialog(Context context) {
        this(context, R.style.customDialog);
    }

    public FilletDialog(Context context, int themeResId) {
        super(context, themeResId);
    }
}
