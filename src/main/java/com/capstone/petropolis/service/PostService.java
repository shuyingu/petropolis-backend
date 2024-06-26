package com.capstone.petropolis.service;

import com.capstone.petropolis.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface PostService {

    Post save(Post post);

    Post getPostById(Integer id);

    void deletePostById(Integer id);

    Page<Post> getPosts(String title, String sex, String species, String postType, LocalDate dateFrom, Pageable pageable);

    List<Post> getPostsByUser(Long userId);

}
