package com.capstone.petropolis.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

/*
 * petropolis::t_user 业务 entity @see src/main/resources/sql/petropolis.user.sql
 */
@Data
@Entity
@Table(name = "t_user", uniqueConstraints = {
        @UniqueConstraint(name = "unique_user_name", columnNames = {"user_name", "delete_time"}),
        @UniqueConstraint(name = "unique_user_email", columnNames = {"user_email", "delete_time"})
})
public class UserEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -5535267384082714508L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "id", nullable = false, unique = true, columnDefinition = "BIGINT UNSIGNED COMMENT 'Primary Key, user id'")
    private long id;

    @Column(name = "user_name", nullable = false, length = 64, columnDefinition = "VARCHAR(64) COMMENT 'User Name'")
    private String userName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", nullable = false, length = 32, columnDefinition = "VARCHAR(32) COMMENT 'Encrypted login password; Convention all caps; MD5(MD5(source password) + password_salt)'")
    private String password;

    @JsonIgnore
    @Column(name = "password_salt", nullable = false, length = 32, columnDefinition = "VARCHAR(32) COMMENT '32 CHAR UUID; Password encrypted salt; SELECT UPPER(REPLACE(UUID(),\"-\",\"\"))'")
    private String passwordSalt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "email_not_verified", nullable = false, length = 320, columnDefinition = "VARCHAR(320) DEFAULT '' COMMENT 'Unverified Email'")
    private String emailNotVerified = "";

    @Column(name = "user_email", nullable = false, length = 320, columnDefinition = "VARCHAR(320) COMMENT 'Verified Email can be used for login; First Set UPPER(REPLACE(UUID(),\"-\",\"\"))'")
    private String userEmail;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "update_time", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time'")
    private Timestamp updateTime;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "create_time", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'create time'")
    private Timestamp createTime;

    // 业务无感知， 不应该去使用
    // Fix bug entity 中多了这个字段后，读取时候也需要加上这个字段 select column name -> select *
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "delete_time", nullable = false, columnDefinition = "BIGINT DEFAULT 0 COMMENT 'Soft delete timestamp'")
    private long deleteTime = 0;
}
