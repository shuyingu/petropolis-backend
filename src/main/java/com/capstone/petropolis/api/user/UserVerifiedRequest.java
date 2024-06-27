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

    private String userEmail;
}
