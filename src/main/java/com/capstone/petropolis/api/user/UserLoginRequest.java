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

    // [required] user name [1, 64] size
    private String userName;

    // [required] MD5(user password).upper() 32 size upper A-Z + 0-9
    private String password;

    // [optional]
    private String code;
}
