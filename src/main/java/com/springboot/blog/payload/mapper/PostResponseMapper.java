package com.springboot.blog.payload.mapper;

import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostResponseMapper {
    public PostResponse getBuild(List<PostDto> contentDto, Page<Post> postsPage) {
        return PostResponse.builder()
                .content(contentDto)
                .page(postsPage.getNumber())
                .size(postsPage.getSize())
                .totalElements(postsPage.getTotalElements())
                .totalPages(postsPage.getTotalPages())
                .last(postsPage.isLast())
                .build();
    }
}