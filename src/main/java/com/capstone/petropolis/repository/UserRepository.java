package com.capstone.petropolis.repository;

import com.capstone.petropolis.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query(value = "SELECT * FROM t_user WHERE (user_name = ?1 OR user_email = ?2) AND delete_time = 0", nativeQuery = true)
    UserEntity get(String name, String email);

    @Query(value = "SELECT COUNT(1) FROM t_user WHERE (user_name = ?1 OR user_email = ?2) AND delete_time = 0", nativeQuery = true)
    int count(String name, String email);

    @Query(value = "SELECT COUNT(1) FROM t_user WHERE (user_name = ?1) AND delete_time = 0", nativeQuery = true)
    int countName(String name);

    @Query(value = "SELECT COUNT(1) FROM t_user WHERE (user_email = ?1) AND delete_time = 0", nativeQuery = true)
    int countEmail(String email);

    // inset or update result == 1 is success or result == 0 is no success or Exception
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO t_user (user_name, password, password_salt, email_not_verified, user_email) VALUES (?1, ?2, ?3, ?4, UPPER(REPLACE(UUID(),\"-\",\"\")))", nativeQuery = true)
    int insert(String name, String password, String salt, String emailNotVerified);

    // inset or update result == 1 is success or result == 0 is no success or Exception
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO t_user (user_name, password, password_salt, email_not_verified, user_email) VALUES (?1, ?2, ?3, ?4, ?4)", nativeQuery = true)
    int insertFull(String name, String password, String salt, String emailNotVerified);

    @Transactional
    @Modifying
    @Query(value = "UPDATE t_user SET email_not_verified = ?2 WHERE id = ?1 AND delete_time = 0 AND email_not_verified = '' ", nativeQuery = true)
    int setEmailNotVerified(long id, String email);

    @Transactional
    @Modifying
    @Query(value = "UPDATE t_user SET user_email = email_not_verified WHERE id = ?1 AND delete_time = 0 AND email_not_verified = ?2 AND user_email <> email_not_verified", nativeQuery = true)
    int bindEmail(long id, String email);

    @Transactional
    @Modifying
    @Query(value = "UPDATE t_user SET email_not_verified = '', user_email = UPPER(REPLACE(UUID(),\"-\",\"\")) WHERE id = ?1 AND delete_time = 0 AND email_not_verified <> ''", nativeQuery = true)
    int unbindEmail(long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE t_user SET delete_time = UNIX_TIMESTAMP(NOW()), user_email = UPPER(REPLACE(UUID(),\"-\",\"\")) WHERE id = ?1 AND delete_time = 0", nativeQuery = true)
    int delete(long id);
}
