package com.capstone.petropolis.utils;

import com.google.gson.Gson;

public class JSON {
    private final static Gson G = new Gson();;

    // 对象转 json 字符串
    public static String to(Object src) {
        return G.toJson(src);
    }

    // 从 json 串封装对象
    public static <T> T from(String json, Class<T> classOfT) {
        return G.fromJson(json, classOfT);
    }
}
