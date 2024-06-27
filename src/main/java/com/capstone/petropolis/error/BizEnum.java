package com.capstone.petropolis.error;

public class BizEnum {
    /*
     * biz code b is { 000 }  -> { 999 }
     */

    public static BizException comment(int c, String message) {
        return BizException.create(300, 1, c, message);
    }

    public static BizException post(int c, String message) {
        return BizException.create(300, 2, c, message);
    }

    public static BizException user(int c, String message) {
        return BizException.create(300, 3, c, message);
    }
}
