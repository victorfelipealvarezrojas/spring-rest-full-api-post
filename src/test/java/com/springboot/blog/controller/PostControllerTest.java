package com.springboot.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WebMvcTest(PostController.class)
class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void createPost() throws Exception {

        PostDto postDto = new PostDto.PostDtoBuilder()
                .title("title-test-post-service")
                .description("description-test-post-service")
                .content("content-test-post-service")
                .build();

        when(postService.createPost(any(PostDto.class))).thenReturn(postDto);

        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect((status().isCreated()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("title-test-post-service"))
                .andExpect(jsonPath("$.description").value("description-test-post-service"))
                .andExpect(jsonPath("$.content").value("content-test-post-service"))
                .andExpect(content().json(objectMapper.writeValueAsString(postDto)));
    }


    @Test
    void getPostPagination() throws Exception {
        List<PostDto> postDtoList = List.of(
                new PostDto.PostDtoBuilder()
                        .title("title-test-post-service")
                        .description("description-test-post-service")
                        .content("content-test-post-service")
                        .build(),
                new PostDto.PostDtoBuilder()
                        .title("title-test-post-service2")
                        .description("description-test-post-service2")
                        .content("content-test-post-service2")
                        .build(),
                new PostDto.PostDtoBuilder()
                        .title("title-test-post-service3")
                        .description("description-test-post-service3")
                        .content("content-test-post-service3")
                        .build(),
                new PostDto.PostDtoBuilder()
                        .title("title-test-post-service4")
                        .description("description-test-post-service4")
                        .content("content-test-post-service4")
                        .build()
        );

        Page<PostDto> postDtoPage = new PageImpl<>(postDtoList);

        PostResponse contentPageResponse = PostResponse.builder()
                .content(postDtoPage.getContent())
                .page(0)
                .size(1)
                .totalElements(4)
                .totalPages(1)
                .last(true)
                .build();

        when(postService.getAllPosts(0, 1, "title","asc")).thenReturn(contentPageResponse);

        mockMvc.perform(get("/api/posts?page=0&size=1&sortBy=title&sortDir=asc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("title-test-post-service"))
                .andExpect(jsonPath("$.content[0].description").value("description-test-post-service"))
                .andExpect(jsonPath("$.content[0].content").value("content-test-post-service"))
                .andExpect(jsonPath("$.content[2].title").value("title-test-post-service3"))
                .andExpect(content().contentType(APPLICATION_JSON));
    }

    @Test
    void getByIdPost()  throws Exception {
        PostDto postDto = new PostDto.PostDtoBuilder()
                .title("title-test-post-service")
                .description("description-test-post-service")
                .content("content-test-post-service")
                .build();

        when(postService.getPostById(1L)).thenReturn(postDto);

        mockMvc.perform(get("/api/posts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updatePost() throws Exception {
        PostDto postBodyParam = new PostDto.PostDtoBuilder()
                .title("title-test-post-service")
                .description("description-test-post-service")
                .content("content-test-post-service")
                .build();

        PostDto postDtoEditResults = new PostDto.PostDtoBuilder()
                .title("title-test-post-service-edit")
                .description("description-test-post-service-edit")
                .content("content-test-post-service-edit")
                .build();

        when(postService.updatePost(any(Long.class),any(PostDto.class))).thenReturn(postDtoEditResults);

        mockMvc.perform(put("/api/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postBodyParam)))
                .andExpect((status().isOk()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value("title-test-post-service-edit"))
                .andExpect(jsonPath("$.description").value("description-test-post-service-edit"))
                .andExpect(jsonPath("$.content").value("content-test-post-service-edit"))
                .andExpect(content().json(objectMapper.writeValueAsString(postDtoEditResults)));
    }

    @Test
    void deletePost() throws Exception {
        mockMvc.perform(delete("/api/posts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Post deleted successfully"));
    }
}