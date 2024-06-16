package com.capstone.petropolis.api.user;

import com.capstone.petropolis.api.Request;

import java.io.Serial;

public class UserLoginRequest extends Request {

    @Serial
    private static final long serialVersionUID = -4173302646518728222L;

    // [必填] 用户名 Or 检验通过的邮箱首次输入的全称 默认区分大小写
    private String userName;

    // [必填] 加密后的密码
    // 算法 ： 用户输入密码后， 前端进行 MD5(用户输入的密码) 得到 32 字节字符 随后 upper 填入 password
    private String password;
}
