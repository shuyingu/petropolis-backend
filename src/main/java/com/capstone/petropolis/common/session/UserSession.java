package com.capstone.petropolis.common.session;

import com.capstone.petropolis.error.BizError;
import com.capstone.petropolis.utils.JSON;
import com.capstone.petropolis.utils.TimeUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Base64;

@Data
// user session 存储信息
public class UserSession implements Serializable {
    @Serial
    private static final long serialVersionUID = 7267109826080492169L;

    private static final Logger log = LogManager.getLogger();

    public long userID;
    public String name;

    public long createTime;

    public UserSession() {
        this.createTime = TimeUtils.currentTime();
    }

    public UserSession(long userID, String name) {
        this.userID = userID;
        this.name = name;
        this.createTime = TimeUtils.currentTime();
    }

    public UserSession(long userID, String name, long createTime) {
        this.userID = userID;
        this.name = name;
        this.createTime = createTime;
    }

    public String encrypt() throws Exception {
        ObjectOutputStream objectStream = null;

        try {
            ByteArrayOutputStream byteStream = new  ByteArrayOutputStream();
            objectStream = new ObjectOutputStream(byteStream);
            objectStream.writeObject(this);
            byte []bytes = byteStream.toByteArray();

            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            log.error("UserSession_encrypt_error | this:{}, stack:{}", JSON.to(this), ExceptionUtils.getStackTrace(e));

            throw BizError.Internal;
        } finally {
            if (objectStream != null) {
                objectStream.close();
            }
        }
    }

    // UserSession.encrypt() string -> UserSession object
    public static UserSession decrypt(String encryptStr) throws Exception {
        if (StringUtils.isEmpty(encryptStr)) {
            throw BizError.Token;
        }

        UserSession result = null;
        ObjectInputStream inputStream = null;
        try {
            byte[] bytes = Base64.getDecoder().decode(encryptStr);

            ByteArrayInputStream byteInputSteam = new ByteArrayInputStream(bytes);
            inputStream = new ObjectInputStream(byteInputSteam);
            result = (UserSession)inputStream.readObject();
        } catch (Exception e) {
            log.warn("UserSession_decrypt_warn | encryptStr:{}", encryptStr);

            throw BizError.TokenExpire;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

        if (result == null || result.getUserID() <= 0 || StringUtils.isEmpty(result.getName())) {
            throw BizError.Token;
        }

        return result;
    }
}
