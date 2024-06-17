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

    // [可选] 请求链路唯一标识码
    private String traceID;
    // [可选] 额外信息
    private String message;
    // [必填] 默认 0 表示 success
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
