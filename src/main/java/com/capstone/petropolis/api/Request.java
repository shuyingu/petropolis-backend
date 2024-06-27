package com.capstone.petropolis.api;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class Request implements Serializable {
    @Serial
    private static final long serialVersionUID = 5116719163399878897L;

    private String traceID;
    private String token;
    private long userID;
    private String UA;
}
