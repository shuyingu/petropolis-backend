package com.capstone.petropolis.utils;

import com.capstone.petropolis.error.BizError;
import org.apache.commons.lang3.StringUtils;

public class UserUtils {

    public final static int UserNameMaxLength = 64;

    public static void checkUserName(String userName) throws Exception {
        if (StringUtils.isBlank(userName)) {
            throw BizError.Empty;
        }

        if (userName.length() > UserNameMaxLength) {
            throw BizError.param("user name length exceed " + UserNameMaxLength);
        }
    }

    public static void checkUserEmail(String userEmail) throws Exception {
        if (StringUtils.isBlank(userEmail)) {
            throw BizError.Empty;
        }

        if (!CheckUtils.checkEmail(userEmail)) {
            throw BizError.param("user email not valid");
        }
    }

    public static void checkUserPassword(String userPassword) throws Exception {
        if (StringUtils.isBlank(userPassword)) {
            throw BizError.Empty;
        }

        if (userPassword.length() != 32) {
            throw BizError.param("user password length exceed 32");
        }

        for(int i = 0; i < userPassword.length(); i++){
            char ch = userPassword.charAt(i);
            if ((ch >= '0' && ch <= '9') || (ch >= 'A' &&  ch <= 'Z')) {
            } else {
                throw BizError.param("user password contains illegal character");
            }
        }
    }

    public static String password(String password, String salt) {
        // Upper MD5(MD5(source password) + password_salt)
        return IDUtils.getUpper32MD5(password+salt);
    }
}
