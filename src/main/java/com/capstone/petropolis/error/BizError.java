package com.capstone.petropolis.error;

public class BizError {
    // 400 万能参数异常
    public final static BizException Param = BizException.create(400, 0, 0,"argument exception");
    // 500 万能服务器内部异常
    public final static BizException Internal = BizException.create(500, 0, 0, "internal error");

    // begin : comment 业务

    // end   : comment 业务

    // begin : post 业务

    // end   : post 业务

    // begin : user 业务
    public final static BizException UserNotFound = BizEnum.user(1, "user not found");
    public final static BizException UserExist = BizEnum.user(2, "user already exist");
    // end   : user 业务
}
