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

    private String token;
}
