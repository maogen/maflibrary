package com.maf.task;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.maf.utils.ImageUtils;
import com.maf.utils.Lg;


/**
 * 项目名称：RentalRoom
 * 类描述：旋转图片、压缩图片
 * 创建人：zgmao
 * 创建时间：2017/6/27
 * 修改人：zgmao
 * 修改时间：2017/6/27
 * 修改备注：
 * Created by zgmao on 2017/6/27.
 */

public class CompressTask extends AsyncTask<String, String, String> {
    // 原始的图片
    private String filePath;
    // 旋转的图片
    private String rotateImagePath;
    // 压缩的图片
    private String compressPath;

    private Handler handler;
    /**
     * 压缩图片结束的消息
     */
    public static final int IMAGE_COMPRESS = 3001;

    public CompressTask(String filePath, Handler handler) {
        this.filePath = filePath;
        this.handler = handler;
    }

    @Override
    protected String doInBackground(String... params) {
        // 上传图片之前，先处理图片的旋转角度
        rotateImagePath = ImageUtils.rotateImage(filePath);
        Lg.d("原来图片地址：" + rotateImagePath);
        // 压缩图片
        compressPath = ImageUtils.compressImage(rotateImagePath);
        Lg.d("压缩后的图片地址：" + compressPath);
        // 压缩成功，通过主线程
        Message message = Message.obtain();
        message.what = IMAGE_COMPRESS;
        message.obj = compressPath;
        handler.sendMessage(message);
        return compressPath;
    }
}
