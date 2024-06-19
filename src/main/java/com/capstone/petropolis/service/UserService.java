package com.capstone.petropolis.service;

import com.capstone.petropolis.api.user.UserCreateRequest;
import com.capstone.petropolis.api.user.UserCreateResponse;
import com.capstone.petropolis.api.user.UserLoginRequest;
import com.capstone.petropolis.api.user.UserLoginResponse;
import com.capstone.petropolis.common.session.SessionService;
import com.capstone.petropolis.entity.UserEntity;
import com.capstone.petropolis.error.BizError;
import com.capstone.petropolis.repository.UserRepository;
import com.capstone.petropolis.utils.CheckUtils;
import com.capstone.petropolis.utils.IDUtils;
import com.capstone.petropolis.utils.JSON;
import com.capstone.petropolis.utils.UserUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class UserService {
    private static final Logger log = LogManager.getLogger();

    @Autowired
    private UserRepository userRepository;

    private void createCheck(UserCreateRequest request) throws Exception {
        if (request == null) {
            throw BizError.Empty;
        }

        UserUtils.checkUserName(request.getUserName());
        UserUtils.checkUserEmail(request.getUserEmail());
        UserUtils.checkUserPassword(request.getPassword());
    }

    public void create(UserCreateRequest request, UserCreateResponse response) throws Exception {
        this.createCheck(request);

        // 数据库查验是否重复
        int cut = this.userRepository.count(request.getUserName(), request.getUserEmail());
        if (cut > 0) {
            throw BizError.user("user already exists");
        }

        // 数据正常开始处理
        String salt = IDUtils.getUpper32UUID();
        String passwd = UserUtils.password(request.getPassword(), salt);

        // entity 😂我们这里，默认用户填入邮箱直接通过验证，怎么简单怎么来
        // 正常流程， 这里后续应该发送验证邮件，走验证流程
        UserEntity entity = new UserEntity();
        entity.setUserName(request.getUserName());
        entity.setUserEmail(request.getUserEmail());
        entity.setPassword(passwd);
        entity.setPasswordSalt(salt);
        entity.setEmailNotVerified(request.getUserEmail());
        entity.setUserEmail(request.getUserEmail());
        entity.setCreateTime(new Timestamp(new Date().getTime()));
        entity.setUpdateTime(entity.getCreateTime());
        UserEntity res = this.userRepository.save(entity);

        log.debug("UserService_create_debug | entity:{}", JSON.to(entity));

        // 用户创建成功，开始给用户生成 token 和 session 用于后续持续会话
        String token = SessionService.put(res.getId(), request.getUserName());

        // 返回最终生成的 token
        response.setToken(token);
    }

    private void loginCheck(UserLoginRequest request) throws Exception {
        if (request == null) {
            throw BizError.Empty;
        }

        // 用户登录可能是邮箱，也可能用户， 或者未来更多等 这里统一去 check
        boolean isEmail = CheckUtils.checkEmail(request.getUserName());
        if (!isEmail) {
            UserUtils.checkUserName(request.getUserName());
        }

        // 哪怕是邮箱，也可能是用户名，后面交给业务层
    }

    // 有的复杂也会额外带个参数 request context 这里怎么简单怎么来
    public void login(UserLoginRequest request, UserLoginResponse response) throws Exception {
        this.loginCheck(request);

        UserEntity entity = this.userRepository.get(request.getUserName(), request.getUserName());
        if (entity == null || entity.getId() <= 0) {
            throw BizError.UserNotFound;
        }

        // 用户存在，开始比对 passwd
        String passwd = UserUtils.password(request.getPassword(), entity.getPasswordSalt());
        // debug 环境测试，线上生产环境不应该暴露用户隐私和明文密码
        log.debug("UserService_debug | entity:{}, passwd:{}, request.password:{}", JSON.to(entity), passwd, request.getPassword());

        if (!passwd.equals(entity.getPassword())) {
            // 这里正常情况还有 验证码 逻辑，这里暂时省略

            throw BizError.UserPassword;
        }

        // 用户正常登录 开始生产 token
        String token = SessionService.put(entity.getId(), request.getUserName());

        // 返回数据
        response.setToken(token);
    }
}
