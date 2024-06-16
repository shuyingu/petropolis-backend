package com.capstone.petropolis.error;

public class BizEnum {
    // code b is { 0000 }  -> { 9999 } ，分配给每个业务模块，唯一
    public static BizException comment(int c, String message) {
        // 1 : comment 业务 biz code b 标识
        return BizException.create(300, 1, c, message);
    }

    public static BizException post(int c, String message) {
        // 2 : post 业务 biz code b 标识
        return BizException.create(300, 2, c, message);
    }

    public static BizException user(int c, String message) {
        // 3 : user 业务 biz code b 标识
        return BizException.create(300, 3, c, message);
    }
}
