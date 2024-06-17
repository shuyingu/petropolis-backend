package com.capstone.petropolis.api.user;

import com.capstone.petropolis.api.Request;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserCreateRequest extends Request {

    @Serial
    private static final long serialVersionUID = -8297943177101752485L;

    // [必填] 用户名 64 字符以内
    private String userName;

    // [必填] 合法的邮箱
    // 理论海外系统支持不填写邮箱和手机号，这里简单做，必须要第一注册时候填好，减少工作量，不提供二次修改解绑和换绑
    private String userEmail;

    // [必填] 加密后的密码
    // 算法 ： 用户输入密码后， 前端进行 MD5(用户输入的密码) 得到 32 字节字符 随后 upper 填入 password
    private String password;
}
