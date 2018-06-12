package com.maf.db;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;

public class BeanUtil
{

    /**
     * 对象copy只拷贝public的属性
     *
     * @param from
     * @param to
     */
    public static void copyBeanWithOutNull(Object from, Object to)
    {
        Class<?> beanClass = from.getClass();
        Field[] fields = beanClass.getFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            try {
                Object value = field.get(from);
                if (value != null) {
                    field.set(to, value);
                }
            } catch (Exception e) {
            }
        }

    }

    @SuppressWarnings("rawtypes")
    public static Field getDeclaredField(Class clazz, String name)
    {
        try {
            return clazz.getDeclaredField(name);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取属性
     *
     * @param o
     * @param field
     * @return
     */
    public static Object getProperty(Object o, String field)
    {
        try {
            Field f = o.getClass().getDeclaredField(field);
            f.setAccessible(true);
            String name = f.getName();
            String method;
            if (name.startsWith("is")) {
                // 如果成员变量是is开头，方法名和成员变量同名
                method = name;
                // 尝试获取
                try {
                    Method m = o.getClass().getMethod(method);
                    return (Object) m.invoke(o);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                    // 获取失败
                }
            }
            // 一般成员变量
            name = name.replaceFirst(name.substring(0, 1), name.substring(0, 1)
                    .toUpperCase(Locale.US));
            method = "get" + name;
            Method m = o.getClass().getMethod(method);
            // return f.get(o);
            return (Object) m.invoke(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 添加屬性
     *
     * @param o
     * @param field
     * @param value
     */
    public static void setProperty(Object o, String field, Object value)
    {
        try {
            Field f = o.getClass().getDeclaredField(field);
            f.setAccessible(true);
            f.set(o, value);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
