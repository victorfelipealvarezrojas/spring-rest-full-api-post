package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Esta clase representa una implementación de servicio para gestionar comentarios.
 */
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    /**
     * Constructor para CommentServiceImpl.
     *
     * @param commentRepository Un repositorio para interactuar con entidades Comment.
     * @param postRepository
     */
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    /**
     * Crea un nuevo comentario para un Post especificado y devuelve su representación DTO.
     *
     * @param postId     El ID del Post al que pertenece el comentario.
     * @param commentDto El objeto CommentDto que representa el nuevo comentario.
     * @return Un objeto CommentDto que representa el comentario creado.
     */
    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

        Comment comment = mapToComment(commentDto);

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        comment.setPost(post);

        Comment savedComment = commentRepository.save(comment);

        return mapToCommentDto(savedComment);
    }

    /**
     * Retorna una lista de comentarios por Id de Post y devuelve su representación DTO.
     *
     * @param postId El ID del Post al que pertenece el comentario.
     * @return Un objeto CommentDto que representa el comentario creado.
     */
    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(this::mapToCommentDto).collect(Collectors.toList());
    }

    /**
     * Retorna un comentario por Id de Post y Id de Comentario y devuelve su representación DTO.
     *
     * @param postId    El ID del Post al que pertenece el comentario.
     * @param commentId El ID del comentario.
     * @return Un objeto CommentDto que representa el comentario creado.
     */
    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment with id " + commentId +
                    " does not belong to post with id " + postId);
        }

        return mapToCommentDto(comment);

    }

    /**
     * Modifica un comentario por Id de Post y Id de Comentario y devuelve su representación DTO.
     *
     * @param postId    El ID del Post al que pertenece el comentario.
     * @param commentId El ID del comentario.
     * @return Un objeto CommentDto que representa el comentario a modificar.
     */
    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment with id " + commentId +
                    " does not belong to post with id " + postId);
        }

        comment.setBody(commentDto.getBody());
        Comment savedComment = commentRepository.save(comment);
        return mapToCommentDto(savedComment);
    }

    /**
     * Elimina un comentario por Id de Post y Id de Comentario y devuelve su representación DTO.
     *
     * @param postId    El ID del Post al que pertenece el comentario.
     * @param commentId El ID del comentario.
     * @return Un objeto CommentDto que representa el comentario creado.
     */
    @Override
    public void deleteComment(long postId, long commentId) {
        Post post = postRepository
                .findById(postId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to Post");
        }

        commentRepository.delete(comment);
    }


    private CommentDto mapToCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .name(comment.getName())
                .email(comment.getEmail())
                .body(comment.getBody())
                .build();
    }

    private Comment mapToComment(CommentDto commentDto) {
        return Comment.builder()
                .id(commentDto.getId())
                .name(commentDto.getName())
                .email(commentDto.getEmail())
                .body(commentDto.getBody())
                .build();
    }
}
