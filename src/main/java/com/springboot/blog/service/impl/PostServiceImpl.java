package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post newPost = postRepository.save(new Post.PostBuilder()
                .title(postDto.getTitle())
                .description(postDto.getDescription())
                .content(postDto.getContent())
                .build());

        return new PostDto.PostDtoBuilder()
                .title(newPost.getTitle())
                .description(newPost.getDescription())
                .content(newPost.getContent())
                .build();
    }

    @Override
    public PostDto updatePost(PostDto postDto) {
        return null;
    }

    @Override
    public PostDto getPostById(Long id) {
        return null;
    }

    @Override
    public void deletePost(Long id) {

    }
}
