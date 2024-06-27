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

    private String userName;
    private String password;
    private String code;
}
