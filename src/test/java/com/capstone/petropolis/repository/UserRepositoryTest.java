package com.capstone.petropolis.repository;

import com.capstone.petropolis.entity.UserEntity;
import com.capstone.petropolis.utils.IDUtils;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private final static Gson gson = new Gson();

    public static void dump(Object o) {
        if (o == null) {
            System.out.println("null");
        } else {
            System.out.println(o.getClass().getName());
            System.out.println(gson.toJson(o));
        }
    }

    @Test
    void get() {
        // 1. 简单功能能否正常使用测试
        String name = "1";
        String email = "1@outlook.com";

        try {
            UserEntity user = this.userRepository.get(name, email);
            dump(user);
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    @Test
    void count() {
        String name = "2";
        String email = "2@outlook.com";

        int cut = this.userRepository.count(name, email);
        dump(cut);
    }

    @Test
    void insert() {
        String name = "1";
        String password = IDUtils.getUpper32UUID();
        String salt = IDUtils.getUpper32UUID();
        String emailNotVerified = "1@outlook.com";

        // MD5(MD5(source password) + password_salt)

        try {
            int result = this.userRepository.insert(name, password, salt, emailNotVerified);
            dump(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());

            if (e.getMessage().contains("unique")) {
                System.out.println("user unique");
            }
        }

        // first success result == 1

        // error result == X && throw Exception
        // 2024-06-10T15:45:26.256-07:00  WARN 6622 --- [           main] o.h.engine.jdbc.spi.SqlExceptionHelper   : SQL Error: 1062, SQLState: 23000
        // 2024-06-10T15:45:26.256-07:00 ERROR 6622 --- [           main] o.h.engine.jdbc.spi.SqlExceptionHelper   : Duplicate entry '1-0' for key 't_user.unique_user_name'
    }

    @Test
    void setEmailNotVerified() {
        // 数据库基础测试数据
        // {"id":1,"UserName":"1","password":"429B010A1D164F8DAD0EE4221F546534","passwordSalt":"731295DD5BCB466E918323B9CB77209F","EmailNotVerified":"1@outlook.com","UserEmail":"029D2824277B11EFBBAF0242AC110002","UpdateTime":"Jun 10, 2024, 10:44:34 PM","CreateTime":"Jun 10, 2024, 10:44:34 PM"}

        long id = 1;

        String email = "2@outlook.com";

        int result = this.userRepository.setEmailNotVerified(id, email);
        dump(result);
    }

    @Test
    void bindEmail() {
        long id = 1;

        String email = "2@outlook.com";

        int result = this.userRepository.bindEmail(id, email);
        dump(result);
    }

    @Test
    void unbindEmail() {
        long id = 1;

        int result = this.userRepository.unbindEmail(id);
        dump(result);
    }

    @Test
    void delete() {
        long id = 1;
        int result = this.userRepository.delete(id);
        dump(result);
    }
}