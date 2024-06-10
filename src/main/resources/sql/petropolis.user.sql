CREATE DATABASE IF NOT EXISTS `petropolis` CHARACTER SET UTF8MB4;

USE petropolis;

# create table user
CREATE TABLE IF NOT EXISTS `t_user` (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '物理主键, user id',

    `user_name` VARCHAR(64) NOT NULL COMMENT '平台登陆用户名',
    `password` VARCHAR(32) NOT NULL COMMENT '加密后的登陆密码; 约定全大写; MD5(MD5(source password) + password_salt)',
    `password_salt` VARCHAR(32) NOT NULL COMMENT '32 CHAR UUID; 密码加密的盐; SELECT UPPER(REPLACE(UUID(),"-",""))',
    `email_not_verified` VARCHAR(320) NOT NULL DEFAULT '' COMMENT '未经验证的邮箱',
    `user_email` VARCHAR(320) NOT NULL COMMENT '验证通过的邮箱, 可以用于登陆; First Set UPPER(REPLACE(UUID(),"-",""))',

    `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `delete_time` BIGINT NOT NULL DEFAULT 0 COMMENT '软删除时间戳',

    UNIQUE KEY `unique_user_name` (`user_name`, `delete_time`),
    UNIQUE KEY `unique_user_email` (`user_email`, `delete_time`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4 COMMENT = '用户表';

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
