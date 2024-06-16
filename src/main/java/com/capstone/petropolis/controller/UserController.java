package com.capstone.petropolis.controller;

import com.capstone.petropolis.api.user.UserCreateRequest;
import com.capstone.petropolis.api.user.UserCreateResponse;
import com.capstone.petropolis.api.user.UserLoginRequest;
import com.capstone.petropolis.api.user.UserLoginResponse;
import com.capstone.petropolis.entity.UserEntity;
import com.capstone.petropolis.error.BizError;
import com.capstone.petropolis.error.BizException;
import com.capstone.petropolis.repository.UserRepository;
import com.capstone.petropolis.service.UserService;
import com.capstone.petropolis.utils.JSON;
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

    @PostMapping("")
    public UserEntity createUser(@RequestBody UserEntity user) {
        return userRepository.save(user);
    }

    @GetMapping("/{id}")
    public UserEntity getUserById(@PathVariable long id) {
        return userRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable long id, @RequestBody UserEntity user) {
        UserEntity existingUser = userRepository.findById(id).orElse(null);
        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }
        user.setId(id);
        UserEntity updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        userRepository.deleteById(id);
    }

    @PostMapping("/create")
    public UserCreateResponse create(@RequestBody UserCreateRequest request) {
        StopWatch watch = new StopWatch();
        UserCreateResponse response = new UserCreateResponse();

        // 简单写写 🪀
        // 正常走注解拦截器里统一处理
        try {
            watch.start();

            log.info("UserCreate_start_info | request:{}", JSON.to(request));

            response.set(request);

            this.userService.create(request, response);

        } catch (Throwable r) {
            response.setMessage(r.getMessage());

            if (r instanceof BizException b) {
                response.setCode(b.getCode());
                log.warn("UserCreate_BizException_warn | b:{}", b);
            } else {
                response.setCode(BizError.Internal.getCode());
                log.error("UserCreate_BizException_error | r:{}, stack:{}", r, ExceptionUtils.getStackTrace(r));
            }

            // 正常企业代码 会在这里处理各类异常并打点
        } finally {
            watch.stop();
            log.info("UserCreate_finish_info | elapsed:{}ms, response:{}", watch.getTotalTimeMillis(), JSON.to(response));
        }

        return response;
    }

    @PostMapping("/login")
    public UserLoginResponse login(@RequestBody UserLoginRequest request) {
        StopWatch watch = new StopWatch();
        UserLoginResponse response = new UserLoginResponse();

        // 简单写写 🪀
        // 正常走注解拦截器里统一处理
        try {
            watch.start();

            log.info("UserLogin_start_info | request:{}", JSON.to(request));

            response.set(request);
            this.userService.login(request, response);

        } catch (Throwable r) {
            response.setMessage(r.getMessage());

            response.setMessage(r.getMessage());

            if (r instanceof BizException b) {
                response.setCode(b.getCode());
                log.warn("UserLogin_BizException_warn | b:{}", b);
            } else {
                response.setCode(BizError.Internal.getCode());
                log.error("UserLogin_BizException_error | r:{}, stack:{}", r, ExceptionUtils.getStackTrace(r));
            }
        } finally {
            watch.stop();
            log.info("UserLogin_finish_info | elapsed:{}ms, response:{}", watch.getTotalTimeMillis(), JSON.to(response));
        }

        return response;
    }
}
