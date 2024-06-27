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

        int cut = this.userRepository.count(request.getUserName(), request.getUserEmail());
        if (cut > 0) {
            throw BizError.user("user already exists");
        }

        String salt = IDUtils.getUpper32UUID();
        String passwd = UserUtils.password(request.getPassword(), salt);

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

        String token = SessionService.put(res.getId(), request.getUserName());

        response.setToken(token);
    }

    private void loginCheck(UserLoginRequest request) throws Exception {
        if (request == null) {
            throw BizError.Empty;
        }

        boolean isEmail = CheckUtils.checkEmail(request.getUserName());
        if (!isEmail) {
            UserUtils.checkUserName(request.getUserName());
        }

    }

    public void login(UserLoginRequest request, UserLoginResponse response) throws Exception {
        this.loginCheck(request);

        UserEntity entity = this.userRepository.get(request.getUserName(), request.getUserName());
        if (entity == null || entity.getId() <= 0) {
            throw BizError.UserNotFound;
        }

        String passwd = UserUtils.password(request.getPassword(), entity.getPasswordSalt());
        log.debug("UserService_debug | entity:{}, passwd:{}, request.password:{}", JSON.to(entity), passwd, request.getPassword());

        if (!passwd.equals(entity.getPassword())) {

            throw BizError.UserPassword;
        }

        String token = SessionService.put(entity.getId(), request.getUserName());

        response.setToken(token);
    }
}
