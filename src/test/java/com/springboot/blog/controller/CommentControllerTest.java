package com.springboot.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommentController.class)
class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    Post postFake;
    CommentDto commentFake;
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        postFake = new Post.PostBuilder()
                .title("title-test-post-repository")
                .description("description-test-post-repository")
                .content("content-test-post-repository")
                .build();

        commentFake = new CommentDto.CommentDtoBuilder()
                .name("comment_name")
                .email("comment_email")
                .body("comment_body")
                .post(postFake)
                .build();

    }

    @Test
    void createComment() throws Exception {
        when(commentService.createComment(
                any(Long.class),
                any(CommentDto.class)))
                .thenReturn(commentFake);

        mockMvc.perform(post("/api/posts/1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentFake)))
                .andExpect((status().isOk()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(commentFake.getName()))
                .andExpect(jsonPath("$.email").value(commentFake.getEmail()))
                .andExpect(jsonPath("$.body").value(commentFake.getBody()))
                .andExpect(jsonPath("$.post").value(commentFake.getPost()));

    }
}