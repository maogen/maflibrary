package com.maf.camera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.maf.R;
import com.maf.activity.BaseTitleActivity;
import com.maf.utils.FileUtils;
import com.maf.utils.ImageUtils;
import com.maf.utils.Lg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * 项目名称：maf
 * 类描述：相机拍照界面
 * 创建人：zgmao
 * 创建时间：2018/5/4
 * 1：Manifest文件配置Activity
 * <activity
 * android:name="com.maf.camera.CameraActivity"/>
 * 2：在onActivityResult中通过
 * String pictureFile = data.getStringExtra("picture");
 * 得到拍照结果
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
    private RelativeLayout camera_preview;
    // 相机预览控件
    private CameraPreview mPreview;
    // 相机
    private Camera mCamera;
    // 保存的文件
    private File pictureFile;
    // 0-前置摄像头，1-后置摄像头
    private static int cameraIndex = 0;
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
                String path = pictureFile.getAbsolutePath();
                if (cameraIndex == 1) {
                    // 前置摄像头拍照，照片需要水平翻转
                    path = ImageUtils.flipHorizontal(path);
                }
                Intent intent = new Intent();
                intent.putExtra("picture", path);
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
    protected void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initTitleView()
    {
        titleBarView.setTitle("拍照");
        titleBarView.setTitleRight("切换摄像头", new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                cameraIndex = cameraIndex == 0 ? 1 : 0;

                if (mCamera != null) {
                    mCamera.release();
                }
                recreate();
            }
        });
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

        camera_preview = (RelativeLayout) findViewById(R.id.camera_preview);
        btn_take = (Button) findViewById(R.id.btn_take);
        initCamera();
    }

    private void initCamera()
    {
        if (checkCameraHardware(this)) {
            // Create an instance of Camera
            mCamera = getCameraInstance();
            // Create our Preview view and set it as the content of our activity.
            mPreview = new CameraPreview(this, mCamera);
            mPreview.setCameraIndex(cameraIndex);
            // 设置参数
            setParam();
            //
            // 添加相机预览控件
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.width = mCamera.getParameters().getPreviewSize().width;
            layoutParams.height = mCamera.getParameters().getPreviewSize().height;
            mPreview.setLayoutParams(layoutParams);
            camera_preview.addView(mPreview);
            // 添加头像背景虚线框
            ImageView imageView = new ImageView(mContext);
            ViewGroup.LayoutParams imageParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageParams.width = mCamera.getParameters().getPreviewSize().width;
            imageParams.height = mCamera.getParameters().getPreviewSize().height - 30;
            imageView.setLayoutParams(imageParams);
            imageView.setImageResource(R.drawable.scan_face_bg);
            camera_preview.addView(imageView);
        }
    }

    private void setParam()
    {
        if (null == mCamera) {
            return;
        }
        // 查找当前相机支持的预览尺寸和图片尺寸。
        // 将预览尺寸的最大尺寸作为图片尺寸保存
        // 将第一个预览尺寸宽高比最小的最为预览尺寸
        List<Camera.Size> pictureSizes = mCamera.getParameters().getSupportedPictureSizes();
        List<Camera.Size> previewSizes = mCamera.getParameters().getSupportedPreviewSizes();

        Camera.Size psize;
        // 打印相机支持的图片尺寸
        for (int i = 0; i < pictureSizes.size(); i++) {
            psize = pictureSizes.get(i);
            Lg.d("相机支持的图片尺寸: " + psize.width + " x " + psize.height);
        }
        int preWidth = 0;
        int preHeight = 0;

        int picWidth = 0;
        int picHeight = 0;
        int maxSize = 0;
        float sizeF = 1;// 由于预览控件在界面是正方形，所以选择宽高比例最接近1的尺寸作为预览
        for (int i = previewSizes.size() - 1; i >= 0; i--) {
            psize = previewSizes.get(i);
            float f = (float) psize.height / psize.width;
            Lg.d("相机支持的预览尺寸比重:" + f + "；" + +psize.width + " x " + psize.height);
            if (psize.width * psize.height >= maxSize) {
                // 获取预览尺寸中，最清晰的作为保存图片尺寸
                maxSize = psize.width * psize.height;
                picWidth = psize.width;
                picHeight = psize.height;
            }
            if (Math.abs(f - 1) <= sizeF) {
                // 当前尺寸的宽高比小
                sizeF = Math.abs(f - 1);
                preWidth = psize.width;
                preHeight = psize.height;
            }
        }
        Lg.d("最终相机预览尺寸：" + preWidth + "X" + preHeight);
        Lg.d("最终相机图片尺寸：" + picWidth + "X" + picHeight);
        try {
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setPreviewSize(preWidth, preHeight);
            parameters.setPictureSize(picWidth, picHeight);
            mCamera.setParameters(parameters);
        } catch (Exception e) {
            e.printStackTrace();
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
        if (mCamera != null) {
            mCamera.release();
        }
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
            c = Camera.open(cameraIndex);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

}
