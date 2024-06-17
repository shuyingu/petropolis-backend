package com.capstone.petropolis.api.user;

import com.capstone.petropolis.api.Request;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserLoginRequest extends Request {

    @Serial
    private static final long serialVersionUID = -4173302646518728222L;

    // [必填] 用户名 Or 检验通过的邮箱首次输入的全称 默认区分大小写
    private String userName;

    // [必填] 加密后的密码
    // 算法 ： 用户输入密码后， 前端进行 MD5(用户输入的密码) 得到 32 字节字符 随后 upper 填入 password
    private String password;

    // 这里还应该有验证码功能，这里为了简单也没有添加
    // 用户首次 login 失败后，系统会存储失败次数，次数超过阙值，user login 所有请求必须携带 验证码
    // 验证码来源于新的请求，这里为了方便目前没有失败这块逻辑
    // [可选] 验证码
    private String code;
}
