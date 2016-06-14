package com.maf.git;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.maf.utils.LogUtils;

import java.lang.reflect.Type;

/**
 * Created by mzg on 2016/5/23.
 * 1、Gson的git地址是：https://github.com/google/gson
 * 2、当前版本是2.6.2
 */
public class GsonUtils {
    /**
     * 将json文本转成实体类
     * 泛型无效，在未找到原因
     *
     * @param json json文本
     * @return
     */
    public static <T> T getByJsonString(T t, String json) {
        LogUtils.d("T的类型" + t.getClass().getName());
        Gson gson = new Gson();
        Type type = new TypeToken<T>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    /**
     * 将实体类转成json
     *
     * @param obj
     * @return
     */
    public static <T> String getJson(T obj) {
        return new Gson().toJson(obj);
    }
}
