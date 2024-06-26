package com.capstone.petropolis.service.impl;

import com.capstone.petropolis.entity.Post;
import com.capstone.petropolis.repository.PostRepository;
import com.capstone.petropolis.service.PostService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public Post getPostById(Integer id) {
        return postRepository.findById(id).orElse(null);
    }

    @Override
    public void deletePostById(Integer id) {
        postRepository.deleteById(id);
    }

    @Override
    public Page<Post> getPosts(String title, String sex, String species, String postType, LocalDate dateFrom, Pageable pageable) {
        Specification<Post> spec = new Specification<Post>() {
            @Override
            public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate p = cb.conjunction();

                if (title != null && !title.isEmpty()) {
                    p = cb.and(p, cb.like(root.get("title"), "%" + title + "%"));
                }
                if (sex != null && !sex.isEmpty()) {
                    p = cb.and(p, cb.equal(root.get("sex"), sex));
                }
                if (species != null && !species.isEmpty()) {
                    p = cb.and(p, cb.equal(root.get("species"), species));
                }
                if (postType != null && !postType.isEmpty()) {
                    p = cb.and(p, cb.equal(root.get("postType"), postType));
                }
                if (dateFrom != null) {
                    p = cb.and(p, cb.greaterThanOrEqualTo(root.get("createTime"), dateFrom));
                }

                return p;
            }
        };

        return postRepository.findAll(spec, pageable);
    }

    @Override
    public List<Post> getPostsByUser(Long userId) {
        return postRepository.findByUserId(userId);
    }

}
