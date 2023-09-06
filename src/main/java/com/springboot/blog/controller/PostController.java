package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;

import static com.springboot.blog.utils.AppConstants.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping()
    public ResponseEntity<PostResponse> getAllPost(
            @RequestParam(name = "page",      defaultValue = DEFAULT_PAGE_NUMBER, required = false) int page,
            @RequestParam(name = "size",      defaultValue = DEFAULT_PAGE_SIZE,   required = false) int size,
            @RequestParam(name = "sortBy",    defaultValue = DEFAULT_SORT_BY,     required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = DEFAULT_SORT_ORDER,  required = false) String sortOrder
    ) {
        return new ResponseEntity<>(postService.getAllPosts(page, size, sortBy, sortOrder), OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getByIdPost(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(postService.getPostById(id), OK);
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(
            @PathVariable(name = "id") Long id,
            @RequestBody               PostDto postDto
    ) {
        return new ResponseEntity<>(postService.updatePost(id, postDto), OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") Long id) {
        postService.deletePost(id);
        return new ResponseEntity<>("Post deleted successfully", OK);
    }
}