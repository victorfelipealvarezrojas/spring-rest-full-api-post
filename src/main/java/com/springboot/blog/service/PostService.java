package com.springboot.blog.service;

import com.springboot.blog.payload.PostDto;

public interface PostService {

    PostDto createPost(PostDto postDto);

    PostDto updatePost(PostDto postDto);

    PostDto getPostById(Long id);

    void deletePost(Long id);

}
