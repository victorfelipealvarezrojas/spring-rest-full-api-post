package com.springboot.blog.payload.mapper;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.payload.CommentDto;
import org.springframework.stereotype.Component;

@Component
public class CommentDtoMapper {

    public CommentDto mapToDTO(Comment comment) {
        return new CommentDto.CommentDtoBuilder()
                .id(comment.getId())
                .name(comment.getName())
                .email(comment.getEmail())
                .body(comment.getBody())
                .post(comment.getPost())
                .build();
    }

    public Comment mapToEntity(CommentDto commentDto) {
        return new Comment.CommentBuilder()
                .name(commentDto.getName())
                .email(commentDto.getEmail())
                .body(commentDto.getBody())
                .post(commentDto.getPost())
                .build();
    }


}
