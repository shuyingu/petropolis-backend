package com.capstone.petropolis.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.internet.InternetAddress;

public class CheckUtils {

    private static final Logger log = LogManager.getLogger(CheckUtils.class);

    public static boolean checkEmail(String email) {

        if (email == null || email.length() < 5 || email.length() > 320) {
            return false;
        }

        try {
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();
            return true;
        } catch (Exception e) {
            log.info("checkEmail_info | email is bad {}, message {}", email, e.getMessage());
        }
        return false;
    }
}
