package com.maf.git;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by mzg on 2016/5/23.
 * 1、Gson的git地址是：https://github.com/google/gson
 * 2、当前版本是2.6.2
 */
public class GsonUtils {

    /**
     * 将对象转成json字符串
     *
     * @param obj
     * @return
     */
    public static String gsonToString(Object obj) {
        return new Gson().toJson(obj);
    }

    /**
     * json字符串转成实体类
     *
     * @param json
     * @param token new TypeToken<T>() {}
     * @return
     */
    public static <T> T stringToGson(String json, TypeToken<T> token) {
        return new Gson().fromJson(json, token.getType());
    }

}
