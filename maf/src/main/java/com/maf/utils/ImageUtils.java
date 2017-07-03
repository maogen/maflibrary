package com.maf.utils;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import com.maf.application.BaseApplication;
import com.maf.crop.Crop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 项目名称：maflibrary
 * 类描述：图片相关操作，比如通过Glide加载图片，拍照或者从相册获取图片
 * 创建人：mzg
 * 创建时间：2016/8/12 11:16
 * 修改人：mzg
 * 修改时间：2016/8/12 11:16
 * 修改备注：
 */
public class ImageUtils {
    public static final int TAKE_PHOTO_FROM_CAMERA = 0x1000;
    public static final int TAKE_PHOTO_FROM_ALBUM = 0x1001;
    public static final int TAKE_PHOTO_FROM_CROP = 0x1002;

    /**
     * 裁剪图片的临时文件路径
     */
    public static String cropTempImagePath;

    /**
     * 根据图片id得到图片的bitmap
     *
     * @param imageId 图片地址
     * @return bitmap
     */
    public static Bitmap getBitmapById(int imageId) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        return BitmapFactory.decodeResource(BaseApplication._application.getResources(), imageId);
    }

    /**
     * 根据图片id，得到图片高
     *
     * @param imageId 图片id
     * @return 图片高
     */
    public static int getImageHeight(int imageId) {
        return getBitmapById(imageId).getHeight();
    }

    /**
     * 根据图片id，得到图片宽
     *
     * @param imageId 图片id
     * @return 图片宽
     */
    public static int getImageWidth(int imageId) {
        return getBitmapById(imageId).getWidth();
    }

    /**
     * 拍照获得图片
     * 传入的路径就是图片的路径
     * 注：部分手机拍摄完照片，照片会旋转
     *
     * @param activity Activity对象
     * @param imgPath  文件的路径
     */
    public static void takePhotoFromCamera(Activity activity, String imgPath) {
        Intent intent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        Uri imgUri = Uri.fromFile(new File(imgPath));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
        activity.startActivityForResult(intent, TAKE_PHOTO_FROM_CAMERA);
    }

    /**
     * 从相册获得图片
     * 首先在回调中拿到uri,根据uri通过方法.拿到路径
     * String imgPath = ImageUtils.getImageAbsolutePath(mContext, data.getData());
     *
     * @param activity
     */
    public static void takePhotoFromAbnum(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        activity.startActivityForResult(intent, TAKE_PHOTO_FROM_ALBUM);
    }

    /**
     * 裁剪图片
     *
     * @param activity  activity
     * @param imagePath imagePath
     *                  获取裁剪后的方法：
     * @Override protected void onActivityResult(int requestCode, int resultCode, Intent result) {
     * if (requestCode == Crop.REQUEST_CROP && resultCode == RESULT_OK) {
     * ImageUtils.cropTempImagePath;
     * }
     * }
     */
    public static void takePhotoFromCrop(Activity activity, String imagePath) {
        if (StringUtils.isEmpty(imagePath)) {
            return;
        }
        Uri uri = Uri.fromFile(new File(imagePath));
        cropTempImagePath = FileUtils.getTempImagePath().getAbsolutePath();
        Uri outUri = Uri.fromFile(new File(cropTempImagePath));
        new Crop(uri).output(outUri).asSquare().start(activity);
    }

    /**
     * 根据Uri获取图片绝对路径，解决Android4.4以上版本Uri转换
     *
     * @param context
     * @param imageUri
     * @author yaoxing
     * @date 2016年4月6日
     */
    public static String getImageAbsolutePath(Activity context, Uri imageUri) {
        if (context.getPackageManager().checkPermission("android.permission.WRITE_EXTERNAL_STORAGE", context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
            if (context == null || imageUri == null)
                return null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, imageUri)) {
                if (isExternalStorageDocument(imageUri)) {
                    String docId = DocumentsContract.getDocumentId(imageUri);
                    String[] split = docId.split(":");
                    String type = split[0];
                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }
                } else if (isDownloadsDocument(imageUri)) {
                    String id = DocumentsContract.getDocumentId(imageUri);
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                    return getDataColumn(context, contentUri, null, null);
                } else if (isMediaDocument(imageUri)) {
                    String docId = DocumentsContract.getDocumentId(imageUri);
                    String[] split = docId.split(":");
                    String type = split[0];
                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }
                    String selection = MediaStore.Images.Media._ID + "=?";
                    String[] selectionArgs = new String[]{split[1]};
                    return getDataColumn(context, contentUri, selection, selectionArgs);
                }
            } // MediaStore (and general)
            else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
                // Return the remote address
                if (isGooglePhotosUri(imageUri))
                    return imageUri.getLastPathSegment();
                return getDataColumn(context, imageUri, null, null);
            }
            // File
            else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
                return imageUri.getPath();
            }
        } else {
            Lg.e("没有读取文件系统的权限,图片路径获取失败");
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = MediaStore.Images.Media.DATA;
        String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


    /**
     * 将图片压缩到1M以内
     *
     * @param path
     * @return
     */
    public static String compressImage(String path) {
        File file = new File(path);
        long fileSize = file.length() / 1024;
        Lg.d("图片大小" + fileSize);
        if (fileSize <= 1024) {
            // 图片大小小于最大值，不压缩，直接返回原来的路劲
            return path;
        }
        String desPath = FileUtils.getTempImagePath().getAbsolutePath();
        desPath = compressPicture(path, desPath);
        return desPath;
    }

    /**
     * 得到图片bitmap
     *
     * @param path
     * @return
     */
    public static Bitmap getImageBitmap(String path) {
        BitmapFactory.Options opts = new BitmapFactory.Options();//获取缩略图显示到屏幕上
        opts.inSampleSize = 2;
        Bitmap newBitmap = BitmapFactory.decodeFile(path, opts);
        return newBitmap;
    }

    /**
     * 压缩图片
     *
     * @param srcPath 图片原来的路径
     * @param desPath 图片压缩之后的路径
     * @return
     */
    public static String compressPicture(String srcPath, String desPath) {
        BitmapFactory.Options op = new BitmapFactory.Options();

        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        op.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, op);
        op.inJustDecodeBounds = false;

        // 缩放图片的尺寸
        float w = op.outWidth;
        float h = op.outHeight;
        float hh = 1024f;//
        float ww = 1024f;//
        // 最长宽度或高度1024
        float be = 1.0f;
        if (w > h && w > ww) {
            be = (w / ww);
        } else if (w < h && h > hh) {
            be = (h / hh);
        }
        if (be <= 0) {
            be = 1.0f;
        }
        op.inSampleSize = (int) be;// 设置缩放比例,这个数字越大,图片大小越小.
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, op);
        int desWidth = (int) (w / be);
        int desHeight = (int) (h / be);
        bitmap = Bitmap.createScaledBitmap(bitmap, desWidth, desHeight, true);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(desPath);
            if (bitmap != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return desPath;
    }

    /**
     * 将图片进行旋转
     *
     * @param path 图片原来的路径
     * @return 旋转之后的路径
     */
    public static String rotateImage(String path) {
        int degree = getBitmapDegree(path);
        Lg.d("图片角度:" + degree);
        if (degree != 0) {
            // 如果图片旋转角度不是0，需要旋转处理
            Bitmap newBitmap = rotateBitmapByDegree(getImageBitmap(path), degree);
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(path);
                if (newBitmap != null) {
                    newBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (null != fos) {
                    try {
                        fos.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        return path;
    }

    /**
     * 读取图片的旋转的角度
     *
     * @param path 图片绝对路径
     * @return 图片的旋转角度
     */
    public static int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bitmap 需要旋转的图片
     * @param angle  旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bitmap, int angle) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }
}
