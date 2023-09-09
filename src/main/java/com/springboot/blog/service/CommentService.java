package com.springboot.blog.service;

import com.springboot.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(Long PostId, CommentDto commentDto);

    List<CommentDto> getCommentsByPostId(Long postId);

    CommentDto getCommentById(Long postId, Long commentId);

    CommentDto deleteCommentById(Long postId, Long commentId);

    CommentDto updateCommentById(Long postId, Long commentId, CommentDto commentDto);
}
