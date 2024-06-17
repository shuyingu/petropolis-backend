package com.capstone.petropolis.api.user;

import com.capstone.petropolis.api.Response;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserLoginResponse extends Response {

    @Serial
    private static final long serialVersionUID = 4079358352780500675L;

    // [可选] 创建成功后我们生成 token，前端可以用 token 操作
    private String token;

    // [可选] 验证码相关信息，当 captcha code is not empty 前端需要渲染验证码模块
    // 暂时没有开发，当然没有开发功能 too many
    private String captcha;
}
