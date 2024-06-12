package com.capstone.petropolis.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/*
 * petropolis::t_user 业务 entity
 */
@Data
@Entity
@Table(name = "t_user", uniqueConstraints = {
        @UniqueConstraint(name = "unique_user_name", columnNames = {"user_name", "delete_time"}),
        @UniqueConstraint(name = "unique_user_email", columnNames = {"user_email", "delete_time"})
})
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, columnDefinition = "BIGINT UNSIGNED COMMENT 'Primary Key, user id'")
    private long id;

    @Column(name = "user_name", nullable = false, length = 64, columnDefinition = "VARCHAR(64) COMMENT 'User Name'")
    private String userName;

    @Column(name = "password", nullable = false, length = 32, columnDefinition = "VARCHAR(32) COMMENT 'Encrypted login password; Convention all caps; MD5(MD5(source password) + password_salt)'")
    private String password;

    @Column(name = "password_salt", nullable = false, length = 32, columnDefinition = "VARCHAR(32) COMMENT '32 CHAR UUID; Password encrypted salt; SELECT UPPER(REPLACE(UUID(),\"-\",\"\"))'")
    private String passwordSalt;

    @Column(name = "email_not_verified", nullable = false, length = 320, columnDefinition = "VARCHAR(320) DEFAULT '' COMMENT 'Unverified Email'")
    private String emailNotVerified = "";

    @Column(name = "user_email", nullable = false, length = 320, columnDefinition = "VARCHAR(320) COMMENT 'Verified Email can be used for login; First Set UPPER(REPLACE(UUID(),\"-\",\"\"))'")
    private String userEmail;

    @Column(name = "update_time", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time'")
    private Timestamp updateTime;

    @Column(name = "create_time", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'create time'")
    private Timestamp createTime;

    @Column(name = "delete_time", nullable = false, columnDefinition = "BIGINT DEFAULT 0 COMMENT 'Soft delete timestamp'")
    private long deleteTime = 0;

}
