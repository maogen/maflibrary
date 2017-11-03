package com.maf.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 项目名称：maflibrary
 * 类描述：
 * 创建人：zgmao
 * 创建时间：2017/11/3
 * 修改人：zgmao
 * 修改时间：2017/11/3
 * 修改备注：
 * Created by zgmao on 2017/11/3.
 */
public class ZipUtil
{
    /**
     * 解压到指定目录
     *
     * @param zipPath
     */
    public static void unZipFiles(String zipPath)
            throws IOException
    {
        File file = new File(zipPath);
        String name = file.getName().split("\\.")[0];
        unZipFiles(new File(zipPath), file.getParent() + File.separator + name
                + File.separator);
    }

    /**
     * 解压文件到指定目录
     *
     * @param zipFile
     * @param descDir
     * @author isea533
     */
    @SuppressWarnings("rawtypes")
    public static void unZipFiles(File zipFile, String descDir)
            throws IOException
    {
        File pathFile = new File(descDir);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        ZipFile zip = new ZipFile(zipFile);
        for (Enumeration entries = zip.entries(); entries.hasMoreElements(); ) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            String zipEntryName = entry.getName();
            InputStream in = zip.getInputStream(entry);
            String outPath = (descDir + zipEntryName).replaceAll("\\*", "/");
            // 判断路径是否存在,不存在则创建文件路径
            File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
            if (!file.exists()) {
                file.mkdirs();
            }
            // 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
            if (new File(outPath).isDirectory()) {
                continue;
            }
            // 输出文件路径信息
            System.out.println(outPath);

            OutputStream out = new FileOutputStream(outPath);
            byte[] buf1 = new byte[1024];
            int len;
            while ((len = in.read(buf1)) > 0) {
                out.write(buf1, 0, len);
            }
            in.close();
            out.close();
        }
        zip.close();
        System.out.println("******************解压完毕********************");
    }
}
