package com.capstone.petropolis.api;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Data
public class Response implements Serializable {
    @Serial
    private static final long serialVersionUID = -2062576136628466981L;

    private String traceID;
    private String message;

    // [required] 0 or 2xx is success code not 0 && not 2xx is error code
    private long code;

    public String set(Request request) {
        // trace id set
        if (request != null && StringUtils.isNotBlank(request.getTraceID())) {
            this.traceID = request.getTraceID();
        } else {
            this.traceID = UUID.randomUUID().toString();
        }

        return this.traceID;
    }
}
