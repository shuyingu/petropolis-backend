package com.capstone.petropolis.error;

import java.io.Serial;

public class BizException extends Exception {

    @Serial
    private static final long serialVersionUID = 3398622693585267820L;

    private int code;

    public BizException(String message) {
        super(message);
    }

    public BizException(int code, String message) {
        super(message);
        this.code = code;
    }

    public static BizException create(int a, int b, int c, String message) {
        BizException biz = new BizException(message);
        biz.setCode(a, b, c);
        return biz;
    }

    public long getCode() {
        return this.code;
    }

    public void setCode(int a, int b, int c) {
        this.code = (a * 1000 + b) * 1000 + c;
    }

    @Override
    public String toString() {
        return String.format("{\"code\":%d, \"message\":\"%s\"}", this.code, this.getMessage());
    }
}
