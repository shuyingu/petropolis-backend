package com.capstone.petropolis.common.session;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

public class UserSessionManage {
    public final static int UserSessionMaxExpireDay = 7;

    public final static int UserSessionMaxLength = 16;

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
