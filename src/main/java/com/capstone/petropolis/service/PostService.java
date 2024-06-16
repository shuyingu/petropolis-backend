package com.capstone.petropolis.service;

import com.capstone.petropolis.entity.Post;

import java.util.List;

public interface PostService {

    Post save(Post post);

    List<Post> getAllPosts();

    Post getPostById(Integer id);

    void deletePostById(Integer id);
}
