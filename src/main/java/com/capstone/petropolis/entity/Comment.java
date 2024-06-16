package com.capstone.petropolis.entity;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    private Integer id;

    private String content;

    private Date createTime;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @Hidden
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    @Hidden
    private Post post;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer commentId) {
        this.id = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

}
