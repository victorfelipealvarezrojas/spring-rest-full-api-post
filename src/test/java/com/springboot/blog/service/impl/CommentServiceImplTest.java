package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.payload.mapper.CommentDtoMapper;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class CommentServiceImplTest {

    // los servicios que se llamaran dentro del repositorio de comments
    @MockBean
    PostRepository postRepository;

    @MockBean
    CommentRepository commentRepository;

    // el servicio que se va a probar
    @Autowired
    CommentServiceImpl commentService;

    @Autowired
    CommentDtoMapper commentDtoMapper;

    Post postFake;
    Comment commentFake, commentFake2;
    List<Comment> commentFakeList;

    @BeforeEach
    void setUp() {
        postFake = new Post.PostBuilder()
                .title("title-test-post-repository")
                .description("description-test-post-repository")
                .content("content-test-post-repository")
                .build();

        commentFake = new Comment.CommentBuilder()
                .name("comment_name")
                .email("comment_email")
                .body("comment_body")
                .post(postFake)
                .build();
        commentFake2 = new Comment.CommentBuilder()
                .name("comment_name")
                .email("comment_email")
                .body("comment_body")
                .post(postFake)
                .build();

        commentFakeList = List.of(commentFake, commentFake2);

    }

    @Test
    void createComment() {
        when(postRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(postFake));
        when(commentRepository.save(any(Comment.class))).thenReturn(commentFake);

        CommentDto response = commentService.createComment(1L, commentDtoMapper.mapToDTO(commentFake));

        assertEquals("comment_name", response.getName());
        assertEquals("comment_email", response.getEmail());
        assertEquals("comment_body", response.getBody());

        assertEquals("title-test-post-repository", response.getPost().getTitle());
        assertEquals("description-test-post-repository", response.getPost().getDescription());
        assertEquals("content-test-post-repository", response.getPost().getContent());
    }

    @Test
    void getCommentsByPostId() {
        when(commentRepository.findByPostId(any(Long.class))).thenReturn(commentFakeList);

        List<CommentDto> response = commentService.getCommentsByPostId(1L);

        assertEquals("comment_name", response.get(0).getName());
        assertEquals("comment_email", response.get(0).getEmail());
        assertEquals("comment_body", response.get(0).getBody());
        assertEquals(2, response.size());


    }
}