package com.capstone.petropolis.error;

import java.io.Serial;

// BizException pet ro po lis 业务异常
public class BizException extends Exception {

    @Serial
    private static final long serialVersionUID = 3398622693585267820L;


    // 前端 js 的 number 类型的最大值是 9007199254740992，这个值是 16 位。 如果超过这个值，js 会出现不精确的现象
    // 后端有些 json 库编码用的 int 类型 [-2,147,483,648，2,147,483,647]。如果超出了范围，可能会导致编码错误。
    // 我们这里的错误码 code 暂定取 9 位
    // 错误码 number a {3} | number b {3} | number c {3}
    // number c is { 000 }  ->  { 999 }，默认自增
    // number b is { 000 }  ->  { 999 } ，分配给每个业务模块，唯一
    // number a is { 000 }  ->  { 999 }  ，但默认多以 400 500 300
    //  400 参数错误
    //  500 系统内部错误
    //  300 内部业务错误
    //  200 or 0 都算成功， 一般只用 0， 默认不用 200 等
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
