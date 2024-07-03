package com.capstone.petropolis.error;

public class BizError {
    public final static BizException Param = BizException.create(400, 0, 1, "argument exception");
    public final static BizException Empty = BizException.create(400, 0, 2, "argument empty");

    public static BizException param(String format, Object... args) {
        return  BizException.create(400, 0,3, String.format(format, args));
    }

    public final static BizException Token = BizException.create(400, 0, 4, "token exception");

    public final static BizException TokenExpire = BizException.create(400, 0, 5, "token expire");

    public final static BizException Internal = BizException.create(500, 0, 1, "internal error");

    public final static BizException UserNotFound = BizEnum.user(1, "user not found");
    public final static BizException UserExist = BizEnum.user(2, "user already exist");

    public static BizException user(String format, Object... args) {
        return  BizEnum.user(3, String.format(format, args));
    }

    public final static BizException UserPassword = BizEnum.user(4, "user and password mismatch");

    public final static BizException UserNameExists = BizEnum.user(5, "user name already exists");

    public final static BizException UserEmailExists = BizEnum.user(6, "user email already exists");
}
