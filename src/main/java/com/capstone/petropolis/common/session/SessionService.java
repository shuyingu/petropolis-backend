package com.capstone.petropolis.common.session;

import com.capstone.petropolis.error.BizError;
import com.capstone.petropolis.utils.JSON;
import com.capstone.petropolis.utils.TimeUtils;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class SessionService {

    private static final Logger log = LogManager.getLogger();

    private static final int SessionServiceMaxLength = 4096;

    // key : user id ; value : UserSessionManage
    private static final Cache<Long, UserSessionManage> sessionCache = CacheBuilder.newBuilder()
            .maximumSize(SessionServiceMaxLength)
            .expireAfterWrite(UserSessionManage.UserSessionMaxLength, TimeUnit.DAYS)
            .build();

    public static String put(long uid, String name) throws Exception {
        return put(new UserSession(uid, name));
    }

    public static String put(UserSession session) throws Exception {
        long uid = session.getUserID();

        // 判断 user session 是否时间过期
        long currentTime = TimeUtils.currentTime();
        if (session.getCreateTime() + UserSessionManage.UserSessionMaxLength * 60 * 60 * 1000 < currentTime) {
            log.error("SessionService_put_error | session:{} expired:{}", JSON.to(session), currentTime);
            throw BizError.Internal;
        }

        String token = session.encrypt();

        // 第一轮校验这个人是否有过 token
        UserSessionManage userSessionManage = sessionCache.getIfPresent(uid);
        if (userSessionManage != null) {
            userSessionManage.put(token);
        } else {
            // 首次添加开始初始化数据
            userSessionManage = new UserSessionManage();
            userSessionManage.put(token);
            sessionCache.put(uid, userSessionManage);
        }

        return token;
    }

    public static UserSession get(String token) throws Exception {
        UserSession user = UserSession.decrypt(token);
        // 判断 token 是否时间过期
        long currentTime = TimeUtils.currentTime();
        if (user.getCreateTime() + UserSessionManage.UserSessionMaxLength * 60 * 60 * 1000 < currentTime) {
            log.info("SessionService_get_info | session:{} expired:{}", JSON.to(user), currentTime);
            throw BizError.TokenExpire;
        }

        long uid = user.getUserID();

        // 第一轮校验这个人是否有过 token
        UserSessionManage userSessionManage = sessionCache.getIfPresent(uid);
        if (userSessionManage == null) {
            throw BizError.TokenExpire;
        }

        // 第二轮 这个 token 是否被持久化保存
        if (!userSessionManage.get(token)) {
            throw BizError.TokenExpire;
        }

        // 验证通过
        return  user;
    }

    // 这里应该提供 token 更换。 为了简单，暂时不提供了
}

