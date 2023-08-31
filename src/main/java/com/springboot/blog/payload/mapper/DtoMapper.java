package com.springboot.blog.payload.mapper;

import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.PostDto;
import org.springframework.stereotype.Component;

@Component
public class DtoMapper {
    public PostDto mapToDTO(Post post) {
        return new PostDto.PostDtoBuilder()
                .id(post.getId())
                .title(post.getTitle())
                .description(post.getDescription())
                .content(post.getContent())
                .build();
    }

    public Post mapToEntity(PostDto postDto) {
        return new Post.PostBuilder()
                .title(postDto.getTitle())
                .description(postDto.getDescription())
                .content(postDto.getContent())
                .build();
    }
}
