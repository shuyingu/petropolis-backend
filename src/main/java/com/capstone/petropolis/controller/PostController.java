package com.capstone.petropolis.controller;

import com.capstone.petropolis.common.session.SessionService;
import com.capstone.petropolis.entity.Post;
import com.capstone.petropolis.entity.UserEntity;
import com.capstone.petropolis.repository.UserRepository;
import com.capstone.petropolis.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post,
                                           @CookieValue(value = "token", required = false) String token) {
        if (token == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            long userId = SessionService.get(token).userID;
            UserEntity user = userRepository.findById(userId).orElse(null);
            if (user == null) {
                return ResponseEntity.badRequest().build();
            }
            post.setUser(user);
            Post createdPost = postService.save(post);
            return ResponseEntity.ok(createdPost);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<Page<Post>> getPosts(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String sex,
            @RequestParam(required = false) String species,
            @RequestParam(required = false) String postType,
            @RequestParam(required = false) String dateFrom,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        LocalDate fromDate = dateFrom != null ? LocalDate.parse(dateFrom) : null;
        Page<Post> posts = postService.getPosts(title, sex, species, postType, fromDate, pageable);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Post>> getPostsByUserId(@PathVariable Long userId) {
        List<Post> posts = postService.getPostsByUser(userId);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Integer id) {
        Post post = postService.getPostById(id);
        if (post != null) {
            return ResponseEntity.ok(post);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Integer id,
                                           @RequestBody Post post,
                                           @CookieValue(value = "token", required = false) String token) {
        if (token == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            long userId = SessionService.get(token).userID;
            UserEntity user = userRepository.findById(userId).orElse(null);
            if (user == null) {
                return ResponseEntity.badRequest().build();
            }
            post.setUser(user);
            post.setId(id);
            Post updatedPost = postService.save(post);
            return ResponseEntity.ok(updatedPost);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePostById(@PathVariable Integer id,
                                               @CookieValue(value = "token", required = false) String token) {
        if (token == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            long userId = SessionService.get(token).userID;
            UserEntity user = userRepository.findById(userId).orElse(null);
            if (user == null) {
                return ResponseEntity.badRequest().build();
            }
            Post post = postService.getPostById(id);
            if (post == null || post.getUser().getId() != userId) {
                return ResponseEntity.badRequest().build();
            }
            postService.deletePostById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
