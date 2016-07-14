package com.maf.base.activity;

import android.view.View;
import android.widget.ImageView;

import com.maf.activity.BaseBackActivity;
import com.maf.git.ImageLoaderUtils;
import com.maf.utils.FileUtils;
import com.maf.utils.LogUtils;
import com.maf.utils.SdcardUtils;

import java.util.List;

import maf.com.mafproject.R;

/**
 * Created by mzg on 2016/5/23.
 * 测试Image相关操作界面
 */
public class ImageActivity extends BaseBackActivity {
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
        imageLocal = (ImageView) findViewById(R.id.image_local);
        imageAssets = (ImageView) findViewById(R.id.image_assets);
        imageDrawable = (ImageView) findViewById(R.id.image_drawable);
        imageUrl = (ImageView) findViewById(R.id.image_url);
    }

    @Override
    protected void initEvent() {

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

    }
}
