CREATE DATABASE IF NOT EXISTS `petropolis` CHARACTER SET UTF8MB4;

USE petropolis;

# create table user
CREATE TABLE IF NOT EXISTS `t_user`
(
    `id`                 BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'Primary key, user id',

    `user_name`          VARCHAR(64)     NOT NULL COMMENT 'User Name',
    `password`           VARCHAR(32)     NOT NULL COMMENT 'Encrypted login password; Convention all caps; MD5(MD5(source password) + password_salt)',
    `password_salt`      VARCHAR(32)     NOT NULL COMMENT '32 CHAR UUID; Password encrypted salt; SELECT UPPER(REPLACE(UUID(),"-",""))',
    `email_not_verified` VARCHAR(320)    NOT NULL DEFAULT '' COMMENT 'Unverified Email',
    `user_email`         VARCHAR(320)    NOT NULL COMMENT 'Verified Email can be used for login; First Set UPPER(REPLACE(UUID(),"-",""))',

    `update_time`        TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time',
    `create_time`        TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time',
    `delete_time`        BIGINT          NOT NULL DEFAULT 0 COMMENT 'Soft delete timestamp',

    UNIQUE KEY `unique_user_name` (`user_name`, `delete_time`),
    UNIQUE KEY `unique_user_email` (`user_email`, `delete_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8MB4 COMMENT = 'user table';

# First Insert
# INSERT INTO t_user (user_name, password, password_salt, email_not_verified, user_email) VALUES (?1, ?2, ?3, ?4, UPPER(REPLACE(UUID(),"-","")))

# email check success
# UPDATE t_user SET user_email = email_not_verified WHERE id = ?1 AND delete_time = 0 AND email_not_verified = ?2 AND user_email <> email_not_verified
# UPDATE t_user SET email_not_verified = ?2 WHERE id = ?1 AND delete_time = 0 AND email_not_verified = ''
# UPDATE t_user SET email_not_verified = '', user_email = UPPER(REPLACE(UUID(),"-","")) WHERE id = ?1 AND delete_time = 0 AND email_not_verified <> ''

# soft delete
# UPDATE t_user SET delete_time = UNIX_TIMESTAMP(NOW()), user_email = UPPER(REPLACE(UUID(),"-","")) WHERE id = ?1 AND delete_time = 0

# select user
# SELECT id, password, password_salt, user_name, email_not_verified, user_email, update_time, create_time FROM t_user WHERE (user_name = ?1 OR user_email = ?2) AND delete_time = 0 = true)
