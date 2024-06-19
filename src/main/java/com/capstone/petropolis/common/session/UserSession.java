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

    // 创建时间戳 单位秒
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
            // ByteArrayOutputStream 或 ByteArrayInputStream 比较特殊是内存读写流，不同于指向硬盘的流，
            // 它内部是使用字节数组读内存的，这个字节数组是它的成员变量，当这个数组不再使用变成垃圾的时候，
            // Java 的垃圾回收机制会将它回收。所以不需要关流。但默认 stream 都需要关闭
            ByteArrayOutputStream byteStream = new  ByteArrayOutputStream();
            objectStream = new ObjectOutputStream(byteStream);
            objectStream.writeObject(this);
            byte []bytes = byteStream.toByteArray();

            // base64 编码，目标 object -> string
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
            // 解码
            byte[] bytes = Base64.getDecoder().decode(encryptStr);

            //字节码转换成对象
            ByteArrayInputStream byteInputSteam = new ByteArrayInputStream(bytes);
            inputStream = new ObjectInputStream(byteInputSteam);
            result = (UserSession)inputStream.readObject();
        } catch (Exception e) {
            // 可能发生坏蛋们会攻击和实验脚本，这个错误非后端引起的 info or warn 日志就行，用于后期观察
            log.warn("UserSession_decrypt_warn | encryptStr:{}", encryptStr);

            // 存在内部错误可能，只能告诉前端 token 过期，需要重新登录获取 token
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
