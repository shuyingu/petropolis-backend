package com.capstone.petropolis.service;

import com.capstone.petropolis.entity.Comment;

import java.util.List;

public interface CommentService {

    Comment save(Comment comment);

    List<Comment> getAllCommentsByPostId(Integer postId);

    Comment getCommentById(Integer commentId);

    void deleteCommentById(Integer commentId);
}
