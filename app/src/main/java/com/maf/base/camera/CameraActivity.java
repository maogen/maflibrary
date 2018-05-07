package com.maf.base.camera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.maf.activity.BaseTitleActivity;
import com.maf.utils.BaseToast;
import com.maf.utils.FileUtils;
import com.maf.utils.Lg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import maf.com.mafproject.R;

/**
 * 项目名称：BorderCollection
 * 类描述：相机拍照界面
 * 创建人：zgmao
 * 创建时间：2018/5/4
 */
public class CameraActivity extends BaseTitleActivity
{

    /**
     * 启动界面
     *
     * @param activity
     * @param requestCode
     */
    public static void actionStart(Activity activity, int requestCode)
    {
        Intent intent = new Intent(activity, CameraActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    // 拍照按钮
    private Button btn_take;
    // 删除，重新拍照
    private Button btn_delete;
    // 确定
    private Button btn_sure;
    // 相机预览控件
    private CameraPreview mPreview;
    // 相机
    private Camera mCamera;
    // 保存的文件
    private File pictureFile;
    // 保存图片
    private Camera.PictureCallback mPicture = new Camera.PictureCallback()
    {

        @Override
        public void onPictureTaken(byte[] data, Camera camera)
        {
            FileOutputStream fos = null;
            try {
                pictureFile = FileUtils.getTempImagePath();
                fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.flush();
                // 拍照结束，返回结果
                Lg.d("拍照结果：" + pictureFile.getAbsolutePath());
                BaseToast.makeTextLong(pictureFile.getAbsolutePath());
                Intent intent = new Intent();
                intent.putExtra("picture", pictureFile.getAbsolutePath());
                setIntent(intent);
                setResult(RESULT_OK, intent);
                finish();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (null != fos) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

    @Override
    protected void initTitleView()
    {
        titleBarView.setTitle("自定义相机");
    }

    @Override
    public void onClick(View v)
    {

    }

    @Override
    protected int getLayoutResId()
    {
        return R.layout.activity_camera;
    }

    @Override
    protected void initView()
    {

        btn_take = (Button) findViewById(R.id.btn_take);
//        mPreview = new CameraPreview(this);
//        preview.addView(mPreview);
        if (checkCameraHardware(this)) {
            // Create an instance of Camera
            mCamera = getCameraInstance();
            // Create our Preview view and set it as the content of our activity.
            mPreview = new CameraPreview(this, mCamera);
            // 设置参数
            setParam();
            RelativeLayout preview = (RelativeLayout) findViewById(R.id.camera_preview);
            preview.addView(mPreview);
            // 设置控件大小
            ViewGroup.LayoutParams layoutParams = mPreview.getLayoutParams();
            layoutParams.width = mCamera.getParameters().getPreviewSize().width;
            layoutParams.height = (mCamera.getParameters().getPreviewSize()).height;
            mPreview.setLayoutParams(layoutParams);
        }
    }

    private void setParam()
    {
        if (null == mCamera) {
            return;
        }
        // 查找当前相机支持的预览尺寸和图片尺寸。将预览尺寸的最大尺寸作为图片尺寸保存
        List<Camera.Size> pictureSizes = mCamera.getParameters().getSupportedPictureSizes();
        List<Camera.Size> previewSizes = mCamera.getParameters().getSupportedPreviewSizes();
        Camera.Size psize;
        // 打印相机支持的图片尺寸
        for (int i = 0; i < pictureSizes.size(); i++) {
            psize = pictureSizes.get(i);
            Lg.d("相机支持的图片尺寸: " + psize.width + " x " + psize.height);
        }
        int pWidth = 0;
        int pHeight = 0;
        int maxSize = 0;
        // 查找相机支持的预览尺寸，并且把最大尺寸作为图片尺寸保存
        for (int i = 0; i < previewSizes.size(); i++) {
            psize = previewSizes.get(i);
            Lg.d("相机支持的预览尺寸: " + psize.width + " x " + psize.height);
            if (psize.width * psize.height >= maxSize) {
                maxSize = psize.width * psize.height;
                pWidth = psize.width;
                pHeight = psize.height;
            }
        }
        if (pWidth != 0) {
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setPreviewSize(pWidth, pHeight);
            parameters.setPictureSize(pWidth, pHeight);
            mCamera.setParameters(parameters);
        }

    }

    @Override
    protected void initEvent()
    {
        btn_take.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mCamera.takePicture(null, null, mPicture);
            }
        });
    }

    @Override
    protected void initValue()
    {

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    /**
     * Check if this device has a camera
     */
    private boolean checkCameraHardware(Context context)
    {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    /**
     * A safe way to get an instance of the Camera object.
     */
    public static Camera getCameraInstance()
    {
        // getNumberOfCameras()-1. 2个  0代表后置（默认） 1代表前置
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

}
