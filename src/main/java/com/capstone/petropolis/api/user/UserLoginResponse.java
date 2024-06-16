package com.capstone.petropolis.api.user;

import com.capstone.petropolis.api.Response;
import lombok.Data;

import java.io.Serial;

@Data
public class UserLoginResponse extends Response {

    @Serial
    private static final long serialVersionUID = 4079358352780500675L;

    // [可选] 创建成功后我们生成 token，前端可以用 token 操作
    private String token;
}
