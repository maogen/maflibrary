package com.maf.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mzg on 2016/5/23.
 * File工具类
 */
public class FileUtils {
    private static List<String> imageList;// 图片文件列表
    /**
     * sdCard根目录
     */
    private static String rootPath = SdcardUtils.getRootPath();
    /**
     * MAF框架所有文件存放文件夹
     */
    private static String DIR_NAME = "MAF_DIR";
    /**
     * 本地图片保存地址
     */
    public static final String DIR_IMAGE_NAME = "Image";
    /**
     * 保存的文件后缀名
     */
    public static final String IMAGE_FORMAT = ".jpg";
    /**
     * 以同伴存放log文件的文件夹
     */
    private static final String DIR_LOG_NAME = "Log";
    /**
     * 保存文件的文件夹
     */
    private static final String DIR_FILE_NAME = "File";

    /**
     * 判断文件是否存在，不存在则新建
     *
     * @param dirName
     */
    private static void mkDirs(String dirName) {
        File dir = new File(dirName);
        if (!dir.exists()) {
            dir.mkdirs();
            LogUtils.d("新建文件夹：" + dirName);
        }
    }

    /**
     * 初始化文件夹，如果没有，新建
     */
    public static void initDir() {
        mkDirs(rootPath + DIR_NAME);
    }

    /**
     * 如果文件不存在，新建
     *
     * @param fileName
     * @return 创建文件成功，返回true
     */
    private static boolean createNewFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
                return true;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 向文件里面写文本
     *
     * @param text     文本内容
     * @param fileName 文件绝对路径
     */
    public static void writeTextToFile(String text, String fileName) {
        if (!createNewFile(fileName)) {
            return;
        }
        File file = new File(fileName);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file, true);
            fileWriter.write(text);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            // 关闭流
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * 记录崩溃的log日志
     *
     * @param text
     */
    public static void writeCrashLog(String text) {
        // crash log文件名
        String logDir = rootPath + DIR_NAME + File.separator + DIR_LOG_NAME;
        mkDirs(logDir);
        String logFileName = logDir + File.separator + "crash_" + DateUtils.getDateByFormat("yyyyMMddHHmmss") + ".log";
        writeTextToFile(text, logFileName);
    }

    /**
     * 得到文件夹下面的所有图片文件
     *
     * @param path
     * @return
     */
    public static List<String> getImagesByPath(String path) {
        File file = new File(path);
        imageList = new ArrayList<>();
        getPath(file);
        return imageList;
    }

    /**
     * 得到图片保存目录
     *
     * @return 图片保存目录
     */
    public static String getImageDir() {
        String imageDir = rootPath + DIR_NAME + File.separator + DIR_IMAGE_NAME;
        mkDirs(imageDir);
        return imageDir;
    }

    /**
     * 递归遍历所有图片文件
     *
     * @param file 文件目录
     */
    private static void getPath(File file) {
        if (!file.exists()) {
            return;
        }
        if (!file.isDirectory()) {
            String path = file.getAbsolutePath();
            if (path.toLowerCase().endsWith(".jpg")
                    || path.toLowerCase().endsWith(".png")
                    || path.toLowerCase().endsWith(".jpeg")
                    || path.toLowerCase().endsWith(".bmp")) {
                imageList.add(path);
            }
        } else {
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for (int i = 0; i < files.length; i++) {
                    getPath(files[i]);
                }
            }
        }
    }

    /**
     * 根据文件名，拼接MAF框架文件路径
     *
     * @param fileName
     * @return
     */
    public static String getFilePath(String fileName) {
        String fileDir = rootPath + DIR_NAME + File.separator + DIR_FILE_NAME;
        mkDirs(fileDir);
        return fileDir + File.separator + fileName;
    }

}
