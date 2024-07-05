package com.capstone.petropolis.controller;

import com.capstone.petropolis.common.session.SessionService;
import com.capstone.petropolis.entity.Comment;
import com.capstone.petropolis.entity.Post;
import com.capstone.petropolis.entity.UserEntity;
import com.capstone.petropolis.repository.UserRepository;
import com.capstone.petropolis.service.CommentService;
import com.capstone.petropolis.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts/{postId}/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> createComment(@PathVariable Integer postId,
                                                 @RequestBody Comment comment,
                                                 @CookieValue(value = "token", required = false) String token) {
        Post post = postService.getPostById(postId);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        if (token == null) {
            return ResponseEntity.badRequest().body("No token provided.");
        }
        try {
            long userId = SessionService.get(token).userID;
            UserEntity user = userRepository.findById(userId).orElse(null);
            if (user == null) {
                return ResponseEntity.badRequest().body("User not found.");
            }
            comment.setPost(post);
            comment.setUser(user);
            Comment createdComment = commentService.save(comment);
            return ResponseEntity.ok(createdComment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getAllComments(@PathVariable Integer postId) {
        List<Comment> comments = commentService.getAllCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Integer postId, @PathVariable Integer commentId) {
        Comment comment = commentService.getCommentById(commentId);
        if (comment != null) {
            return ResponseEntity.ok(comment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Integer postId,
                                                 @PathVariable Integer commentId,
                                                 @RequestBody Comment comment,
                                                 @CookieValue(value = "token", required = false) String token) {
        if (token == null) {
            return ResponseEntity.badRequest().body("No token provided.");
        }
        Post post = postService.getPostById(postId);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        try {
            long userId = SessionService.get(token).userID;
            UserEntity user = userRepository.findById(userId).orElse(null);
            if (user == null) {
                return ResponseEntity.badRequest().body("User not found.");
            }
            comment.setPost(post);
            comment.setUser(user);
            comment.setId(commentId);
            Comment updatedComment = commentService.save(comment);
            return ResponseEntity.ok(updatedComment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer postId,
                                              @PathVariable Integer commentId,
                                              @CookieValue(value = "token", required = false) String token) {
        if (token == null) {
            return ResponseEntity.badRequest().body("No token provided.");
        }
        try {
            long userId = SessionService.get(token).userID;
            UserEntity user = userRepository.findById(userId).orElse(null);
            if (user == null) {
                return ResponseEntity.badRequest().body("User not found.");
            }
            Comment comment = commentService.getCommentById(commentId);
            if (comment == null || comment.getUser().getId() != userId) {
                return ResponseEntity.badRequest().body("Comment not found.");
            }
            commentService.deleteCommentById(commentId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
