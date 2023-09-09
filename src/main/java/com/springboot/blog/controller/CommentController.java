package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<?> getCommentsByPostId(@PathVariable(value = "postId") Long id) {
        return new ResponseEntity<>(commentService.getCommentsByPostId(id), OK);
    }

    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<?> getCommentById(
            @PathVariable(value = "postId") Long postId,
            @PathVariable(value = "commentId") Long commentId
    ) {
        return new ResponseEntity<>(commentService.getCommentById(postId, commentId), OK);
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(
            @RequestBody CommentDto commentDto,
            @PathVariable(value = "postId") Long id
    ) {
        return new ResponseEntity<>(commentService.createComment(id, commentDto), OK);
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateCommentById(
            @PathVariable(value = "postId") Long postId,
            @PathVariable(value = "commentId") Long commentId,
            @RequestBody CommentDto commentDto
    ) {
        return new ResponseEntity<>(commentService.updateCommentById(postId, commentId, commentDto), OK);
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<?> deleteCommentById(
            @PathVariable(value = "postId") Long postId,
            @PathVariable(value = "commentId") Long commentId
    ) {
        return new ResponseEntity<>(commentService.deleteCommentById(postId, commentId), OK);
    }
}
