package com.maf.utils;

/**
 * Created by mzg on 2016/5/23.
 * 处理字符串的工具类
 */
public class StringUtils {
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
}
