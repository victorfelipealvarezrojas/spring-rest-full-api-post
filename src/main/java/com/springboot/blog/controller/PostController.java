package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
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
            @RequestParam(name = "page", defaultValue = "0" , required = false) int page,
            @RequestParam(name = "size", defaultValue = "5", required = false) int size,
            @RequestParam(name = "sortBy", defaultValue = "id", required = false) String sortBy
    ) {
        return new ResponseEntity<>(postService.getAllPosts(page, size, sortBy), OK);
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
    public ResponseEntity<PostDto> updatePost(@PathVariable(name = "id") Long id,
                                              @RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.updatePost(id, postDto), OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") Long id) {
        postService.deletePost(id);
        return new ResponseEntity<>("Post deleted successfully", OK);
    }
}
