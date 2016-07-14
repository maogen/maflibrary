package com.maf.utils;

import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * Created by mzg on 2016/5/23.
 * 处理字符串的工具类
 */
public class StringUtils {
    /**
     * 验证手机号码的正则表达式
     */
    public static String phoneZZ = "^(13[0-9]|15[0-9]|18[7|8|9|6|5|2|1|0]|170|147)\\d{4,8}$";

    /**
     * 判断字符串是空
     *
     * @param string
     * @return
     */
    public static boolean isEmpty(String string) {
        if (null == string || "".equals(string)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断字符串不为空
     *
     * @param string
     * @return
     */
    public static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }

    /**
     * 根据字符串得到首字母
     *
     * @param string
     * @return
     */
    public static String getFirstCharByString(String string) {
        if (isEmpty(string)) {
            return "#";
        } else {
            CharacterParser characterParser = CharacterParser.getInstance();
            String pinyin = characterParser.getSelling(string);
            String sortString = pinyin.substring(0, 1).toUpperCase();
            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                return sortString.toUpperCase();
            } else {
                return "#";
            }
        }
    }

    /**
     * 随机产生字符串
     *
     * @param length
     * @return
     */
    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * String转成utf-8
     *
     * @param str
     * @return
     */
    public static String str2UTF8(String str) {
        return str2Code(str, "UTF-8");
    }

    /**
     * String转成gbk
     *
     * @param str
     * @return
     */
    public static String str2GBK(String str) {
        return str2Code(str, "GBK");
    }

    /**
     * 将字符串换编码
     *
     * @param str
     * @param code
     * @return
     */
    public static String str2Code(String str, String code) {
        try {
            str = new String(str.getBytes(), code);
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return str;
    }
}
