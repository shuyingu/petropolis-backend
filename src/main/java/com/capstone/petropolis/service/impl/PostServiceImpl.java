package com.capstone.petropolis.service.impl;

import com.capstone.petropolis.entity.Post;
import com.capstone.petropolis.repository.PostRepository;
import com.capstone.petropolis.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post getPostById(Integer id) {
        return postRepository.findById(id).orElse(null);
    }

    @Override
    public void deletePostById(Integer id) {
        postRepository.deleteById(id);
    }
}
