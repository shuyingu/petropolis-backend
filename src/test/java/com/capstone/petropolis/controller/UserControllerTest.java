package com.capstone.petropolis.controller;

import com.capstone.petropolis.TestHelper;
import com.capstone.petropolis.api.user.UserCreateRequest;
import com.capstone.petropolis.api.user.UserCreateResponse;
import com.capstone.petropolis.api.user.UserLoginRequest;
import com.capstone.petropolis.api.user.UserLoginResponse;
import com.capstone.petropolis.utils.IDUtils;
import com.capstone.petropolis.utils.JSON;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class UserControllerTest {
    private static final Logger log = LogManager.getLogger();

    @Autowired
    private UserController userController;

    @Test
    void console() {
        log.debug("debug console test");
        log.info("info console test");
        log.warn("warn console test");
        log.error("error console test");
    }


    @Test
    void create() {
        UserCreateRequest request = new UserCreateRequest();
        request.setUserName("nihao4");
        // request.setPassword(IDUtils.getUpper32UUID());
        request.setPassword("863CD4344612425FA488C4A51BAC2625");
        request.setUserEmail("nihao@gmailcom4");

        UserCreateResponse response = this.userController.create(request);
        assertNotNull(response);

        TestHelper.dump(response);
    }

    @Test
    void login() {
        UserLoginRequest request = new UserLoginRequest();
        request.setUserName("nihao");
        request.setPassword("863CD4344612425FA488C4A51BAC2625");

        UserLoginResponse response = this.userController.login(request);
        assertNotNull(response);

        TestHelper.dump(response);
    }
}