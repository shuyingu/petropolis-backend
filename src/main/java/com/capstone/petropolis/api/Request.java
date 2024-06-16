package com.capstone.petropolis.api;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class Request implements Serializable {
    @Serial
    private static final long serialVersionUID = 5116719163399878897L;

    // 下面这些 trace id， token， user id 理论都应该存在 http request cookie 信息里，
    // 随后通过拦截器 or 注解 统一处理， 然后放入 java 用户线程私有变量里，这里比较原始，怎么快速怎么来

    // [必填] 请求链路唯一标识码, 默认 uuid()
    private String traceID;

    // [可选] 用户 99% 操作需要携带 token 验证
    private String token;

    // 基于 token 解析的用户少量信息
    private long userID;
}
