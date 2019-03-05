package com.maf.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import java.io.File;

/**
 * 项目名称：tgrambaseline1.0
 * 类描述：本地打开各种文件的工具类，调用系统自带的应用
 * 为了保证Android7.0以上，能直接调用系统打开文件，需要在Application的onCreate方法中添加代码
 * if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
 * StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
 * StrictMode.setVmPolicy(builder.build());
 * }
 * 创建人：zgmao
 * 创建时间：2018/9/18
 */
public class OpenFiles
{

    public static void openFile(Context context, String filePath)
    {
        if (TextUtils.isEmpty(filePath)) {
            return;
        }
        // 先把路径变成小写，然后判断后缀名
        String tempFilePath = filePath.toLowerCase();
        Intent intent;
        if (tempFilePath.endsWith("doc") || tempFilePath.endsWith("docx")) {
            intent = getWordFileIntent(filePath);
        } else if (tempFilePath.endsWith("ppt") || tempFilePath.endsWith("pptx")) {
            intent = getPPTFileIntent(filePath);
        } else if (tempFilePath.endsWith("xls") || tempFilePath.endsWith("xlsx")) {
            intent = getExcelFileIntent(filePath);
        } else if (tempFilePath.endsWith("jpg") || tempFilePath.endsWith("png") ||
                tempFilePath.endsWith("gif") || tempFilePath.endsWith("tif") ||
                tempFilePath.endsWith("jpeg") || tempFilePath.endsWith("bmp")) {
            intent = getImageFileIntent(filePath);
        } else if (tempFilePath.endsWith("pdf")) {
            intent = getPdfFileIntent(filePath);
        } else if (tempFilePath.endsWith("html")) {
            intent = getHtmlFileIntent(filePath);
        } else if (tempFilePath.endsWith("apk")) {
            intent = getApkFileIntent(filePath);
        } else if (tempFilePath.endsWith("txt")) {
            intent = getWordFileIntent(filePath);
        } else {
            intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(new File(filePath)), "*/*");
        }
        try {
//                        context.startActivity(intent);
            context.startActivity(Intent.createChooser(intent, "选择浏览工具"));
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    //android获取一个用于打开HTML文件的intent
    public static Intent getHtmlFileIntent(String Path)
    {
        File file = new File(Path);
        Uri uri = Uri.parse(file.toString()).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content").encodedPath(file.toString()).build();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(uri, "text/html");
        return intent;
    }

    //android获取一个用于打开图片文件的intent
    public static Intent getImageFileIntent(String Path)
    {
        File file = new File(Path);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "image/*");
        return intent;
    }

    //android获取一个用于打开PDF文件的intent
    public static Intent getPdfFileIntent(String Path)
    {
        File file = new File(Path);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }

    //android获取一个用于打开文本文件的intent
    public static Intent getTextFileIntent(String Path)
    {
        File file = new File(Path);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "text/plain");
        return intent;
    }

    //android获取一个用于打开音频文件的intent
    public static Intent getAudioFileIntent(String Path)
    {
        File file = new File(Path);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "audio/*");
        return intent;
    }

    //android获取一个用于打开视频文件的intent
    public static Intent getVideoFileIntent(String Path)
    {
        File file = new File(Path);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "video/*");
        return intent;
    }


    //android获取一个用于打开CHM文件的intent
    public static Intent getChmFileIntent(String Path)
    {
        File file = new File(Path);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/x-chm");
        return intent;
    }


    //android获取一个用于打开Word文件的intent
    public static Intent getWordFileIntent(String Path)
    {
        File file = new File(Path);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }

    //android获取一个用于打开Excel文件的intent
    public static Intent getExcelFileIntent(String Path)
    {
        File file = new File(Path);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        return intent;
    }

    //android获取一个用于打开PPT文件的intent
    public static Intent getPPTFileIntent(String Path)
    {
        File file = new File(Path);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        return intent;
    }

    //android获取一个用于打开apk文件的intent
    public static Intent getApkFileIntent(String Path)
    {
        File file = new File(Path);
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        return intent;
    }
}
