package com.capstone.petropolis.api.user;

import com.capstone.petropolis.api.Response;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserVerifiedResponse extends Response {

    @Serial
    private static final long serialVersionUID = 158876020916971040L;

    // [可选] 创建成功后我们生成 token，前端所有操作都需要携带 token 操作，用于唯一标识自身身份
    private String token;
}
