package com.capstone.petropolis.controller;

import com.capstone.petropolis.entity.Post;
import com.capstone.petropolis.entity.UserEntity;
import com.capstone.petropolis.repository.UserRepository;
import com.capstone.petropolis.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post, @RequestParam Long userId) {
        // fixme: user should be fetched from the session. This is just for testing before implementing authentication
        UserEntity user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        post.setUser(user);
        Post createdPost = postService.save(post);
        return ResponseEntity.ok(createdPost);
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
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
    public ResponseEntity<Post> updatePost(@PathVariable Integer id, @RequestBody Post post, @RequestParam Long userId) {
        Post existingPost = postService.getPostById(id);
        if (existingPost != null) {
            // fixme: user should be fetched from the session. This is just for testing before implementing authentication
            UserEntity user = userRepository.findById(userId).orElse(null);
            post.setUser(user);
            post.setId(id);
            Post updatedPost = postService.save(post);
            return ResponseEntity.ok(updatedPost);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePostById(@PathVariable Integer id) {
        postService.deletePostById(id);
        return ResponseEntity.noContent().build();
    }

}
