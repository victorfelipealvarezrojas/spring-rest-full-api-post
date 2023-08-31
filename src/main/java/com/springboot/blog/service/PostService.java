package com.springboot.blog.service;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;

import java.util.List;

public interface PostService {

    PostResponse getAllPosts(int page, int size, String sortBy);

    PostDto createPost(PostDto postDto);

    PostDto updatePost(Long id, PostDto postDto);

    PostDto getPostById(Long id);

    void deletePost(Long id);

}
