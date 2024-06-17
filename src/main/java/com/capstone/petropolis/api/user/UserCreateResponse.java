package com.capstone.petropolis.api.user;

import com.capstone.petropolis.api.Response;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserCreateResponse extends Response {

    @Serial
    private static final long serialVersionUID = -49289180100051655L;

    // [可选] 创建成功后我们生成 token，前端可以用 token 操作
    private String token;
}
