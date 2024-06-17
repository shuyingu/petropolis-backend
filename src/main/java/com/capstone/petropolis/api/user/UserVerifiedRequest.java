package com.capstone.petropolis.api.user;

import com.capstone.petropolis.api.Request;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserVerifiedRequest extends Request {

    @Serial
    private static final long serialVersionUID = -4203533284683355783L;

    // [必填] 合法的邮箱
    // 理论海外系统支持不填写邮箱和手机号，这里简单做，必须要第一注册时候填好，减少工作量，不提供二次修改解绑和换绑
    private String userEmail;
}
