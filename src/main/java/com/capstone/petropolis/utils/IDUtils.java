package com.capstone.petropolis.utils;

import org.springframework.util.DigestUtils;

import java.util.UUID;

public class IDUtils {

    public static String getUpper32UUID() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    public static String getUpper32MD5(String source) {
        if (source == null) {
            source = "";
        }
        return DigestUtils.md5DigestAsHex(source.getBytes()).toUpperCase();
    }
}
