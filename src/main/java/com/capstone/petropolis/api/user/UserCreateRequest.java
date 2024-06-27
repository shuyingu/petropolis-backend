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

    private String userName;
    private String userEmail;
    private String password;
}
