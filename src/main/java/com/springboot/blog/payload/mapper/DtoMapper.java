package com.springboot.blog.payload.mapper;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.payload.PostDto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DtoMapper {
    public PostDto mapToDTO(Post post) {
        return new PostDto.PostDtoBuilder()
                .id(post.getId())
                .title(post.getTitle())
                .description(post.getDescription())
                .content(post.getContent())
                .comments(post.getComments().stream()
                        .map(this::mapToDTO)
                        .collect(Collectors.toSet()))
                .build();
    }

    public Post mapToEntity(PostDto postDto) {
        return new Post.PostBuilder()
                .title(postDto.getTitle())
                .description(postDto.getDescription())
                .content(postDto.getContent())
                .build();
    }

    private CommentDto mapToDTO(Comment comment) {
        return new CommentDto.CommentDtoBuilder()
                .id(comment.getId())
                .name(comment.getName())
                .email(comment.getEmail())
                .body(comment.getBody())
                .build();
    }
}