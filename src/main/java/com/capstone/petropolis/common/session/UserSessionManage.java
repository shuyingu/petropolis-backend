package com.capstone.petropolis.common.session;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class UserSessionManage {
    public final static int UserSessionMaxExpireDay = 7;

    // 默认 user session 数量有上限，超过上限我们会清理最旧的
    public final static int UserSessionMaxLength = 16;

    // key : token ; value : 理论上存储 用户 UA 相关信息，例如登录时间，地点，IP，用于安全处理。这里简单写
    private Cache<String, Boolean> userSessions = CacheBuilder.newBuilder()
            .maximumSize(UserSessionMaxLength)
            .expireAfterWrite(7, TimeUnit.DAYS)
            .build();

    public void put(String token) {
        this.userSessions.put(token, true);
    }

    public boolean get(String token) {
        Boolean result = this.userSessions.getIfPresent(token);
        return result != null && result;
    }
}
