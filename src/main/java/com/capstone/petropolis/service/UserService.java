package com.capstone.petropolis.service;

import com.capstone.petropolis.api.user.UserCreateRequest;
import com.capstone.petropolis.api.user.UserCreateResponse;
import com.capstone.petropolis.api.user.UserLoginRequest;
import com.capstone.petropolis.api.user.UserLoginResponse;
import com.capstone.petropolis.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static final Logger log = LogManager.getLogger();

    @Autowired
    private UserRepository userRepository;

    public void create(UserCreateRequest request, UserCreateResponse response) {

    }

    public void login(UserLoginRequest request, UserLoginResponse response) {

    }
}
