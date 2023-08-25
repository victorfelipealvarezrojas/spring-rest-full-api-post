package com.springboot.blog.repository;

import com.springboot.blog.entity.Post;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Test
    @Order(1)
    void testInsertPost() {
        Post post = new Post.PostBuilder()
                .title("title")
                .description("description")
                .content("content")
                .build();
        postRepository.save(post);
        assertTrue(post.getId() > 0);
    }

    @Test
    @Order(2)
    void testFindById() {
        Post post = new Post.PostBuilder()
                .title("title")
                .description("description")
                .content("content")
                .build();
        postRepository.save(post);
        Optional<Post> postOptional = postRepository.findById(post.getId());
        assertTrue(postOptional.isPresent());
    }

    @Test
    @Order(3)
    void testUpdatePost() {
        Post post = new Post.PostBuilder()
                .title("title")
                .description("description")
                .content("content")
                .build();
        postRepository.save(post);
        post.setTitle("title2");
        postRepository.save(post);
        Optional<Post> postOptional = postRepository.findById(post.getId());
        assertTrue(postOptional.isPresent());
        assertTrue(postOptional.get().getTitle().equals("title2"));
    }

    @Test
    @Order(4)
    void testDeletePost() {
        Post post = new Post.PostBuilder()
                .title("title")
                .description("description")
                .content("content")
                .build();
        postRepository.save(post);
        postRepository.delete(post);
        Optional<Post> postOptional = postRepository.findById(post.getId());
        assertTrue(postOptional.isEmpty());
    }
}