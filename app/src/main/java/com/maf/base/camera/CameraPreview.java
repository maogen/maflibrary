package com.maf.base.camera;

import android.app.Activity;
import android.hardware.Camera;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * 项目名称：BorderCollection
 * 类描述：sufaceView 的预览类，其中SurfaceHolder.CallBack用来监听Surface的变化，
 * 当Surface发生改变的时候自动调用该回调方法
 * 通过调用方SurfaceHolder.addCallBack来绑定该方法
 * 创建人：zgmao
 * 创建时间：2018/5/4
 */
public class CameraPreview extends SurfaceView implements
        SurfaceHolder.Callback
{
    private String tag = "camerademo";
    private SurfaceHolder mHolder;
    private Camera mCamera;
    private Activity context;

    public CameraPreview(Activity context, Camera camera)
    {
        super(context);
        this.context = context;
        mCamera = camera;
        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            mCamera.setPreviewDisplay(holder);
            Camera.Parameters parameters = mCamera.getParameters();
            Camera.CameraInfo info =
                    new Camera.CameraInfo();
            Camera.getCameraInfo(0, info);
            int rotation = context.getWindowManager().getDefaultDisplay()
                    .getRotation();
            Log.d(tag, "rotation =" + rotation);
            int degrees = 0;
            switch (rotation) {
                case Surface.ROTATION_0:
                    degrees = 0;
                    break;
                case Surface.ROTATION_90:
                    degrees = 90;
                    break;
                case Surface.ROTATION_180:
                    degrees = 180;
                    break;
                case Surface.ROTATION_270:
                    degrees = 270;
                    break;
            }
            int result;
            Log.d(tag, "info.orientation =" + info.orientation);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                result = (info.orientation + degrees) % 360;
                parameters.setRotation(result);//设置存储的图片显示
                result = (360 - result) % 360;  // compensate the mirror  前置默认270

            } else {  // back-facing
                parameters.setRotation((info.orientation - degrees) % 360);
                result = (info.orientation - degrees + 360) % 360;  //后置默认90

            }

            Log.d(tag, "result =" + result);

            mCamera.setDisplayOrientation(result);//设置预览显示
            mCamera.setParameters(parameters);

            mCamera.startPreview();
        } catch (IOException e) {
            Log.d(tag, "Error setting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (mHolder.getSurface() == null) {
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        } catch (Exception e) {
            Log.d(tag, "Error starting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {

    }
}
