package com.maf.dialog;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.maf.R;
import com.maf.utils.FileUtils;
import com.maf.utils.ImageUtils;
import com.maf.utils.LogUtils;
import com.rey.material.app.BottomSheetDialog;

import java.io.File;

/**
 * 项目名称：maflibrary
 * 类描述：拍照或者相册获取照片工具
 * 创建人：mzg
 * 创建时间：2016/8/12 11:15
 * 修改人：mzg
 * 修改时间：2016/8/12 11:15
 * 修改备注：
 */
public class TakePhotoDialogUtils {
    public static String imagePath;

    /**
     * 选择图片的dialog
     * 可以选择从相机拍照还是从图册选择
     * 解析图片地址的方式是重写onActivityResult方法
     *
     @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode != RESULT_OK) {
                return;
            }
            switch (requestCode) {
                case ImageUtils.TAKE_PHOTO_FROM_CAMERA:
                // 从相机界面返回
                if (resultCode == RESULT_OK) {
                    LogUtils.d("拍照的照片：" + TakePhotoDialogUtils.imagePath);
                }
                break;
                case ImageUtils.TAKE_PHOTO_FROM_ALBUM:
                // 从相册界面返回
                if (resultCode == RESULT_OK) {
                    String imgPath = ImageUtils.getImageAbsolutePath(this, data.getData());
                    LogUtils.d("选择的照片：" + imgPath);
                }
                break;
            }
        }
     */
    public static void showTakePictureDialog(final Activity activity) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(activity);
        View pictureDialog = LayoutInflater.from(activity).inflate(R.layout.dialog_take_picture, null);
        bottomSheetDialog.setContentView(pictureDialog);
        bottomSheetDialog.show();

        TextView txt_take_photo = (TextView) pictureDialog.findViewById(R.id.txt_take_photo);
        TextView txt_album = (TextView) pictureDialog.findViewById(R.id.txt_album);
        TextView txt_cancel = (TextView) pictureDialog.findViewById(R.id.txt_cancel);


        txt_take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                File tempCameraFile = new File(FileUtils.getImageDir(), System.currentTimeMillis() + FileUtils.IMAGE_FORMAT);
                imagePath = tempCameraFile.getAbsolutePath();
                LogUtils.d("拍照临时路径：" + imagePath);
                ImageUtils.takePhotoFromCamera(activity, imagePath);
            }
        });
        txt_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                ImageUtils.takePhotoFromAbnum(activity);
            }
        });
        txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
    }
}
