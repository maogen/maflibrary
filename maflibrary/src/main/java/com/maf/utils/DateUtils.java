package com.maf.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mzg on 2016/5/23.
 * 时间工具类，格式化时间
 */
public class DateUtils {
    /**
     * 默认的时间格式
     */
    public static String defaultDateFormat = "yyyy-MM-dd HH:mm:ss";

    /**
     * 将Date转成自定义的时间格式
     *
     * @param date   Date对象
     * @param format 目标时间格式
     * @return 将Date转换成目标格式字符串之后
     */
    public static String getDateByFormat(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * 根据时间格式得到时间
     *
     * @param format 目标时间格式
     * @return 当前的Date转成成目标时间格式的结果
     */
    public static String getDateByFormat(String format) {
        Date date = new Date();
        return getDateByFormat(date, format);
    }

    /**
     * 根据已有时间和时间格式，得到Date
     *
     * @param dateString 需要处理的时间
     * @param format     需要处理的时间的格式
     * @return 处理之后的Date
     */
    public static Date getDateByFormatDate(String dateString, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException exception) {
            exception.printStackTrace();
        }
        return date;
    }

    /**
     * 根据默认的时间格式，得到时间
     *
     * @return 得到默认时间格式的当前时间
     */
    public static String getDateByDefault() {
        return getDateByFormat(defaultDateFormat);
    }


    /**
     * 转换时间格式
     *
     * @param dateString     原始的时间字符串
     * @param originalFormat 原始时间字符串的时间格式
     * @param purposeFormat  目标的时间格式
     * @return 转换后的时间
     */
    public static String changeDateFormat(String dateString, String originalFormat, String purposeFormat) {
        Date date = getDateByFormatDate(dateString, originalFormat);
        return getDateByFormat(date, purposeFormat);
    }
}
