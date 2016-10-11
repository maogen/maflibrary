package com.maf.base.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.maf.activity.BaseBackActivity;
import com.maf.crop.Crop;
import com.maf.dialog.TakePhotoDialogUtils;
import com.maf.git.ImageLoaderUtils;
import com.maf.utils.FileUtils;
import com.maf.utils.ImageUtils;
import com.maf.utils.LogUtils;
import com.maf.utils.SdcardUtils;

import java.util.List;

import maf.com.mafproject.R;

/**
 * Created by mzg on 2016/5/23.
 * 测试Image相关操作界面
 */
public class ImageActivity extends BaseBackActivity {
    private Button btnGetPicture;// 拍照或者选择图片，选择完之后
    private ImageView imageLocal;
    private ImageView imageAssets;
    private ImageView imageDrawable;
    private ImageView imageUrl;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_image;
    }

    @Override
    protected void initView() {
        btnGetPicture = (Button) findViewById(R.id.btn_get_picture);

        imageLocal = (ImageView) findViewById(R.id.image_local);
        imageAssets = (ImageView) findViewById(R.id.image_assets);
        imageDrawable = (ImageView) findViewById(R.id.image_drawable);
        imageUrl = (ImageView) findViewById(R.id.image_url);
    }

    @Override
    protected void initEvent() {
        btnGetPicture.setOnClickListener(this);
    }

    @Override
    protected void initValue() {
        // 得到所有sdcard路径
        List<String> paths = SdcardUtils.getSdcardPath();
        for (int i = 0; i < paths.size(); i++) {
            LogUtils.d(paths.get(i));
        }
        //  取得所有制定目录下的图片文件
        List<String> imageFiles = FileUtils.getImagesByPath("/storage/emulated/0/tencent/QQfile_recv");
        for (int i = 0; i < imageFiles.size(); i++) {
            LogUtils.d(imageFiles.get(i));
        }
        ImageLoaderUtils.showLocalImage(imageLocal, "storage/emulated/0/tencent/QQfile_recv/111111.png");
        ImageLoaderUtils.showAssetsImage(imageAssets, "image/image_one.jpg");
        ImageLoaderUtils.showDrawableImage(imageDrawable, R.drawable.image_two);
        ImageLoaderUtils.showUrlImage(imageUrl, "http://pic39.nipic.com/20140321/9448607_213919680000_2.jpg");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get_picture:
                // 选择照相还是相册
                TakePhotoDialogUtils.showTakePictureDialog(this);
                break;
        }
    }

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
                    // 裁剪照片
                    ImageUtils.takePhotoFromCrop(this, TakePhotoDialogUtils.imagePath);
                }
                break;
            case ImageUtils.TAKE_PHOTO_FROM_ALBUM:
                // 从相册界面返回
                if (resultCode == RESULT_OK) {
                    String imgPath = ImageUtils.getImageAbsolutePath(this, data.getData());
                    LogUtils.d("选择的照片：" + imgPath);
                    // 裁剪照片
                    ImageUtils.takePhotoFromCrop(this, imgPath);
                }
                break;
            case Crop.REQUEST_CROP:
                // 从裁剪照片返回
                LogUtils.d("裁剪后的图片：" + ImageUtils.cropTempImagePath);
                ImageLoaderUtils.showLocalImage(imageLocal, ImageUtils.cropTempImagePath);
                break;
            default:
                break;
        }
    }
}
