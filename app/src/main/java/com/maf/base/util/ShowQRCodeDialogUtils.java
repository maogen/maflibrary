package com.maf.base.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.maf.dialog.FilletDialog;
import com.maf.utils.BaseToast;
import com.tgram.qrcode.QRCodeUtil;

import java.io.File;

import maf.com.mafproject.R;

/**
 * 项目名称：Ytb_Android
 * 类描述：弹出包含一个二维码的dialog
 * 创建人：gk
 * 创建时间：2016/8/27 10:21
 * 修改人：gk
 * 修改时间：2016/8/27 10:21
 * 修改备注：
 */
public class ShowQRCodeDialogUtils
{
    public static final int WIDTH = 260;//二维码高度和宽度
    public static FilletDialog filletDialog;//对话框


    /**
     * 弹出包含二维码的dialog
     *
     * @param activity
     * @param content    二维码内容
     * @param isWithLogo 是否显示logo图片
     * @param iconId     logo图片
     */
    public static void showQRCodeDialog(Activity activity, String content, boolean isWithLogo, int iconId)
    {
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_qr_code, null, false);
        ImageView img_qr_code = (ImageView) view.findViewById(R.id.img_qr_code);
        TextView txt_sure = (TextView) view.findViewById(R.id.txt_sure);
        File qrCodeFile = new File(activity.getCacheDir(), getQRCodeFileName());

        Bitmap logo = null;
        if (isWithLogo) {
            logo = BitmapFactory.decodeResource(activity.getResources(), iconId);
        }
        if (QRCodeUtil.createQRImage(content, WIDTH, WIDTH, logo, qrCodeFile.getAbsolutePath())) {
            filletDialog = new FilletDialog(activity);
            img_qr_code.setImageURI(Uri.fromFile(qrCodeFile));
            filletDialog.setContentView(view);
            txt_sure.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    filletDialog.dismiss();
                }
            });
            filletDialog.show();
        } else {
            BaseToast.makeTextShort("二维码生成失败");
        }
    }

    /**
     * 得到二维码的图片的名称
     *
     * @return
     */
    private static String getQRCodeFileName()
    {
        return "maf_" + System.currentTimeMillis() + "_qr.png";
    }
}
