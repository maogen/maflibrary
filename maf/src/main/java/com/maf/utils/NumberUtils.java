package com.maf.utils;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 项目名称：maflibrary
 * 类描述：数字工具类
 * 创建人：mzg
 * 创建时间：2016/11/24 15:21
 * 修改人：mzg
 * 修改时间：2016/11/24 15:21
 * 修改备注：
 */

public class NumberUtils {
    /**
     * 判断字符串是否是数字
     *
     * @param string
     * @return
     */
    public static boolean isNumber(String string) {
        if (StringUtils.isEmpty(string)) {
            return false;
        }
        Pattern pattern = Pattern.compile("[-]*[0-9]*");
        Matcher isNum = pattern.matcher(string);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 转成两位小数
     *
     * @param number
     * @return
     */
    public static String string2Dec(double number) {
        return new DecimalFormat("#.##").format(number);
    }

    /**
     * 整数两位显示
     *
     * @param number
     * @return
     */
    public static String intTo2Dec(int number) {
        return String.format("%02d", number);
    }
}
