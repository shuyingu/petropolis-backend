package com.capstone.petropolis.controller;

import com.capstone.petropolis.api.user.UserCreateRequest;
import com.capstone.petropolis.api.user.UserCreateResponse;
import com.capstone.petropolis.api.user.UserLoginRequest;
import com.capstone.petropolis.api.user.UserLoginResponse;
import com.capstone.petropolis.common.session.SessionService;
import com.capstone.petropolis.entity.UserEntity;
import com.capstone.petropolis.error.BizError;
import com.capstone.petropolis.error.BizException;
import com.capstone.petropolis.repository.UserRepository;
import com.capstone.petropolis.service.UserService;
import com.capstone.petropolis.utils.JSON;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LogManager.getLogger();

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUserById(@CookieValue(value = "token", required = false) String token) {
        if (token == null) {
            return ResponseEntity.badRequest().body("No token provided.");
        }

        try {
            long id = SessionService.get(token).userID;
            UserEntity user = userRepository.findById(id).orElse(null);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("")
    public ResponseEntity<?> updateUser(@RequestBody UserEntity user,
                                        @CookieValue(value = "token", required = false) String token) {
        if (token == null) {
            return ResponseEntity.badRequest().body("No token provided.");
        }

        try {
            long userId = SessionService.get(token).userID;
            UserEntity updatedUser = userService.update(userId, user);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/create")
    public UserCreateResponse create(@RequestBody UserCreateRequest request) {
        StopWatch watch = new StopWatch();
        UserCreateResponse response = new UserCreateResponse();
        String traceID = "";

        try {
            watch.start();
            log.info("UserCreate_start_info | request:{}", JSON.to(request));

            traceID = response.set(request);
            this.userService.create(request, response);
        } catch (Throwable r) {
            response.setMessage(r.getMessage());

            if (r instanceof BizException b) {
                response.setCode(b.getCode());
                log.warn("UserCreate_BizException_warn | traceID:{}, b:{}", traceID, b);
            } else {
                response.setCode(BizError.Internal.getCode());
                log.error("UserCreate_BizException_error | traceID:{}, r:{}, stack:{}", traceID, r, ExceptionUtils.getStackTrace(r));
            }

        } finally {
            watch.stop();
            log.info("UserCreate_finish_info | traceID:{}, elapsed:{}ms, response:{}", traceID, watch.getTotalTimeMillis(), JSON.to(response));
        }

        return response;
    }

    @PostMapping("/login")
    public UserLoginResponse login(@RequestBody UserLoginRequest request, HttpServletResponse servletResponse) {
        StopWatch watch = new StopWatch();
        UserLoginResponse response = new UserLoginResponse();
        String traceID = "";

        try {
            watch.start();
            log.info("UserLogin_start_info | request:{}", JSON.to(request));

            traceID = response.set(request);
            this.userService.login(request, response);
            setCookie(servletResponse, response.getToken());
        } catch (Throwable r) {
            response.setMessage(r.getMessage());

            if (r instanceof BizException b) {
                response.setCode(b.getCode());
                log.warn("UserLogin_BizException_warn | traceID:{}, b:{}", traceID, b);
            } else {
                response.setCode(BizError.Internal.getCode());
                log.error("UserLogin_BizException_error | traceID:{}, r:{}, stack:{}", traceID, r, ExceptionUtils.getStackTrace(r));
            }
        } finally {
            watch.stop();
            log.info("UserLogin_finish_info | traceID:{}, elapsed:{}ms, response:{}", traceID, watch.getTotalTimeMillis(), JSON.to(response));
        }

        return response;
    }

    private static void setCookie(HttpServletResponse servletResponse, String token) {
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setAttribute("SameSite", "None");
        servletResponse.addCookie(cookie);
    }
}
