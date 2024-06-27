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

    private String token;
}
