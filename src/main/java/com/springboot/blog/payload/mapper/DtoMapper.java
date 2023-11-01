package com.springboot.blog.payload.mapper;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.payload.PostDto;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DtoMapper {

    public Post mapToEntity(PostDto postDto) {
        return new Post.PostBuilder()
                .title(postDto.getTitle())
                .description(postDto.getDescription())
                .content(postDto.getContent())
                .build();
    }

    public PostDto mapToDTO(Post post) {
        return new PostDto.PostDtoBuilder()
                .id(post.getId())
                .title(post.getTitle())
                .description(post.getDescription())
                .content(post.getContent())
                .comments(mapCommentsToDto(post.getComments()))
                .categoryId(post.getCategory().getId())
                .build();
    }

    private Set<CommentDto> mapCommentsToDto(Set<Comment> comments) {
        return comments != null
                ? comments.stream().map(this::mapToDTO).collect(Collectors.toSet())
                : null;
    }

    private Set<CommentDto> mapCommentsToDto2(Set<Comment> comments) {
        return Optional.ofNullable(comments)
                .map(commentSet -> commentSet.stream().map(this::mapToDTO).collect(Collectors.toSet()))
                .orElse(null);
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