package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.payload.mapper.DtoMapper;
import com.springboot.blog.payload.mapper.PostResponseMapper;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;


@Service
public class PostServiceImpl implements PostService {
    private final PostResponseMapper postResponseMapper;
    private final PostRepository postRepository;
    private final DtoMapper dtoMapper;

    public PostServiceImpl(
            PostRepository postRepository,
            PostResponseMapper postResponseMapper,
            DtoMapper dtoMapper
    ) {
        this.postRepository = postRepository;
        this.postResponseMapper = postResponseMapper;
        this.dtoMapper = dtoMapper;
    }

    @Override
    public PostDto getPostById(Long id) {
        return postRepository.findById(id)
                .map(this.dtoMapper::mapToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
    }

    @Override
    public PostResponse getAllPosts( int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(sortBy));
        Page<Post> postsPage = postRepository.findAll(pageable);
        List<PostDto> contentDto = postsPage.getContent()
                .stream()
                .map(this.dtoMapper::mapToDTO)
                .toList();

        return  this.postResponseMapper.getBuild(contentDto, postsPage);
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post newPost = postRepository.save(this.dtoMapper.mapToEntity(postDto));
        return this.dtoMapper.mapToDTO(newPost);
    }

    @Override
    public PostDto updatePost(Long id, PostDto postDto) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isEmpty()) return null;
        return this.dtoMapper.mapToDTO(postRepository.save(this.dtoMapper.mapToEntity(postDto)));
    }

    @Override
    public void deletePost(Long id) {
        postRepository.findById(id)
                .map(this.dtoMapper::mapToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.deleteById(id);
    }
}