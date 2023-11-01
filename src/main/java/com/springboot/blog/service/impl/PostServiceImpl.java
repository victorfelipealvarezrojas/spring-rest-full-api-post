package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.payload.mapper.DtoMapper;
import com.springboot.blog.payload.mapper.PostResponseMapper;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final PostResponseMapper postResponseMapper;
    private final DtoMapper dtoMapper;

    public PostServiceImpl(
            PostRepository postRepository,
            PostResponseMapper postResponseMapper,
            CategoryRepository categoryRepository,
            DtoMapper dtoMapper
    ) {
        this.postRepository = postRepository;
        this.postResponseMapper = postResponseMapper;
        this.categoryRepository = categoryRepository;
        this.dtoMapper = dtoMapper;
    }

    @Transactional(readOnly = true)
    @Override
    public PostDto getPostById(Long id) {
        return postRepository.findById(id)
                .map(this.dtoMapper::mapToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
    }

    @Transactional(readOnly = true)
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

    @Transactional
    @Override
    public PostDto createPost(PostDto postDto) {
        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

        Post post = this.dtoMapper.mapToEntity(postDto);
        post.setCategory(category);
        Post newPost = postRepository.save(post);

        return this.dtoMapper.mapToDTO(newPost);
    }

    @Transactional
    @Override
    public PostDto updatePost(Long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", id));

        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

        post.setTitle(postDto.getTitle() != null ? postDto.getTitle() : post.getTitle());
        post.setDescription(postDto.getDescription() != null ? postDto.getDescription() : post.getDescription());
        post.setContent(postDto.getContent() != null ? postDto.getContent() : post.getContent());
        post.setCategory(category != null ? category : post.getCategory());

        return this.dtoMapper.mapToDTO(postRepository.save(post));
    }

    @Transactional
    @Override
    public void deletePost(Long id) {
        postRepository.findById(id)
                .map(this.dtoMapper::mapToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.deleteById(id);
    }
}