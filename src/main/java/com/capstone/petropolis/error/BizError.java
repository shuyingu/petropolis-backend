package com.capstone.petropolis.error;

public class BizError {
    // 400 万能参数异常
    public final static BizException Param = BizException.create(400, 0, 1, "argument exception");
    public final static BizException Empty = BizException.create(400, 0, 2, "argument empty");

    public static BizException param(String format, Object... args) {
        return  BizException.create(400, 0,3, String.format(format, args));
    }

    // token 异常
    public final static BizException Token = BizException.create(400, 0, 4, "token exception");
    // 正常 token 但过期了
    public final static BizException TokenExpire = BizException.create(400, 0, 5, "token expire");

    // 500 万能服务器内部异常
    public final static BizException Internal = BizException.create(500, 0, 1, "internal error");

    // begin : comment 业务

    // end   : comment 业务

    // begin : post 业务

    // end   : post 业务

    // begin : user 业务
    public final static BizException UserNotFound = BizEnum.user(1, "user not found");
    public final static BizException UserExist = BizEnum.user(2, "user already exist");

    public static BizException user(String format, Object... args) {
        return  BizEnum.user(3, String.format(format, args));
    }

    public final static BizException UserPassword = BizEnum.user(4, "user and password mismatch");
    // end   : user 业务
}
