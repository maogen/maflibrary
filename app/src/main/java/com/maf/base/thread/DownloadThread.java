package com.maf.base.thread;

import android.os.Handler;
import android.os.Message;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import io.vov.vitamio.utils.Log;

/**
 * 项目名称：maflibrary
 * 类描述：下载文件线程，支持断点下载
 * 创建人：zgmao
 * 创建时间：2017/10/31
 * 修改人：zgmao
 * 修改时间：2017/10/31
 * 修改备注：
 * Created by zgmao on 2017/10/31.
 */
public class DownloadThread extends Thread
{
    /**
     * 下载进度消息
     */
    public static final int MSG_DOWNLOAD_PROGRESS = 1001;
    /**
     * 下载结束
     */
    public static final int MSG_DOWNLOAD_FINISH = 1002;
    /**
     * 缓存数组大小
     */
    private static final int BUFFER_SIZE = 1024;
    private Handler handler;
    // 下载链接
    private String urlStr;
    // 文件名
    private String fileName;
    // 文件
    private File file;

    // 已经下载的大小
    private long downloadSize;

    /**
     * @param urlStr   下载链接
     * @param fileName 保存的文件地址
     */
    public DownloadThread(String urlStr, String fileName)
    {
        this.urlStr = urlStr;
        this.fileName = fileName;
    }

    public void setHandler(Handler handler)
    {
        this.handler = handler;
    }

    /**
     * 计算本地已经下载的文件大小
     */
    private long countFile()
    {
        file = new File(fileName);
        if (!file.exists()) {
            return 0;
        }
        return file.length();
    }

    @Override
    public void run()
    {
        BufferedInputStream bis = null;
        RandomAccessFile fos = null;
        byte[] buf = new byte[BUFFER_SIZE];
        URLConnection con = null;
        try {
            URL url = new URL(urlStr);
            URLConnection connection = url.openConnection();
            connection.setAllowUserInteraction(true);
            long fileSize = connection.getContentLength();
            Log.d("TAG", "文件大小：" + fileSize);
            // 设置线程下载的起点和终点
            long startPosition = countFile();// 开始位置
            long curPosition = startPosition;// 当前位置，循环下载时，会跟着变动
            long endPosition = fileSize - 1;// 结束位置
            connection.setRequestProperty("Range", "bytes=" + startPosition + "-" + endPosition);
            //使用java中的RandomAccessFile 对文件进行随机读写操作
            fos = new RandomAccessFile(file, "rw");
            //设置开始写文件的位置
            fos.seek(startPosition);
            bis = new BufferedInputStream(con.getInputStream());
            //开始循环以流的形式读写文件
            while (curPosition < endPosition) {
                int len = bis.read(buf, 0, BUFFER_SIZE);
                if (len == -1) {
                    break;
                }
                fos.write(buf, 0, len);
                curPosition = curPosition + len;
                if (curPosition > endPosition) {
                    downloadSize += len - (curPosition - endPosition) + 1;
                } else {
                    downloadSize += len;
                }
                // 下载进度通知UI
                if (null != handler) {
                    Message message = Message.obtain();
                    message.what = MSG_DOWNLOAD_PROGRESS;
                    handler.sendMessage(message);
                }
            }
            bis.close();
            fos.close();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
        // 下载结束通知UI
        if (null != handler) {
            Message message = Message.obtain();
            message.what = MSG_DOWNLOAD_FINISH;
            handler.sendMessage(message);
        }
    }

    public long getDownloadSize()
    {
        return downloadSize;
    }
}
