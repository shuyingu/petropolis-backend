package com.capstone.petropolis.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/*
 * petropolis::t_user 业务 entity
 */
@Data
@Entity
@Table(name = "t_user")
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "user_name")
    private String UserName;

    @Column(name = "password")
    private String password;

    @Column(name = "password_salt")
    private String passwordSalt;

    @Column(name = "email_not_verified")
    private String EmailNotVerified;

    @Column(name = "user_email")
    private String UserEmail;

    @Column(name = "update_time")
    private Date UpdateTime;

    @Column(name = "create_time")
    private Date CreateTime;
}
