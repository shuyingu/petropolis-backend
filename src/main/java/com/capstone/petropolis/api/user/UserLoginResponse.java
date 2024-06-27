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

    private String token;
    private String captcha;
}
