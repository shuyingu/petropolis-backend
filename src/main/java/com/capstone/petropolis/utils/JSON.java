package com.capstone.petropolis.utils;

import com.google.gson.Gson;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JSON {
    private static final Logger log = LogManager.getLogger();

    private final static Gson G = new Gson();

    // 对象转 json 字符串
    public static String to(Object src) {
        try {
            return G.toJson(src);
        } catch (Throwable r) {
            log.error("JSON_to_error | r:{}, src:{}, stack:{}", r, src, ExceptionUtils.getStackTrace(r));
        }

        // 默认 empty 兜底
        return "";
    }

    // 从 json 串封装对象
    public static <T> T from(String json, Class<T> classOfT) {
        return G.fromJson(json, classOfT);
    }
}
