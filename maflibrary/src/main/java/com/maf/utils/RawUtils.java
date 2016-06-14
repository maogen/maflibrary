package com.maf.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by mzg on 2016/5/23.
 */
public class RawUtils {
    /**
     * 读取资源文件
     *
     * @param context
     * @param id      资源id
     * @return
     */
    public static String getRawStr(Context context, int id) {
        StringBuffer sb = new StringBuffer();
        InputStream is = null;
        BufferedReader reader = null;
        try {
            is = context.getResources().openRawResource(id);
            reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
            String temp;
            while ((temp = reader.readLine()) != null) {
                sb.append(temp);
            }
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
            LogUtils.d("文件编码错误");
        } catch (IOException ex) {
            ex.printStackTrace();
            LogUtils.d("文件流异常");
        } catch (Exception ex) {
            ex.printStackTrace();
            LogUtils.d("未知异常");
        } finally {
            // 关闭流
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    LogUtils.d("关闭流发生异常");
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    LogUtils.d("关闭流发生异常");
                }
            }
        }
        return sb.toString();
    }
}
