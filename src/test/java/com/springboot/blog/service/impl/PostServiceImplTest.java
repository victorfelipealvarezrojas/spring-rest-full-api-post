package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class PostServiceImplTest {

    @MockBean
    PostRepository postRepository;

    @Autowired
    PostServiceImpl postService;

    @Test
    void testPostService() {
        when(postRepository.save(any(Post.class))).thenReturn(new Post.PostBuilder()
                .title("title-test-post-repository")
                .description("description-test-post-repository")
                .content("content-test-post-repository")
                .build());

        PostDto postDto = new PostDto.PostDtoBuilder()
                .title("title-test-post-service")
                .description("description-test-post-service")
                .content("content-test-post-service")
                .build();

        var response = postService.createPost(postDto);

        assertEquals("title-test-post-repository", response.getTitle());

        verify(postRepository,times(1)).save(any(Post.class));
    }
}