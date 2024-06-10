package com.capstone.petropolis.utils;

import java.util.regex.Pattern;

public class CheckUtils {

    /**
     * 粗粒度 验证是否是 Email
     * @param email email 地址，格式：a@b.c
     * @return 验证成功返回 true，验证失败返回 false
     */
    public static boolean checkEmail(String email) {
        // a@b.c 默认最短 5 个字符
        // @ 前 a 默认 最多 64 个字符
        // @ 后 b.c 部分 最多 255 个字符
        // 详情 see https://zh.wikipedia.org/wiki/%E9%9B%BB%E5%AD%90%E9%83%B5%E4%BB%B6%E5%9C%B0%E5%9D%80#%E8%A7%84%E5%88%99
        if (email == null || email.length() < 5 || email.length() > 320) {
            return false;
        }

        String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
        return Pattern.matches(regex, email);
    }
}
