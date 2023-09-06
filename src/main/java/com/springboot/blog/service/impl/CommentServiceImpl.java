package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.payload.mapper.CommentDtoMapper;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Esta clase representa una implementación de servicio para gestionar comentarios.
 */
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentDtoMapper commentDtoMapper;

    /**
     * Constructor para CommentServiceImpl.
     *
     * @param commentRepository Un repositorio para interactuar con entidades Comment.
     * @param postRepository
     * @param commentDtoMapper  Una clase de mapeo para convertir entre objetos Comment y CommentDto.
     */
    @Autowired
    public CommentServiceImpl(
            CommentRepository commentRepository,
            PostRepository postRepository,
            CommentDtoMapper commentDtoMapper
    ) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.commentDtoMapper = commentDtoMapper;
    }

    /**
     * Mapea un objeto Comment a un objeto CommentDto.
     *
     * @param comment El objeto Comment que se va a mapear.
     * @return Un objeto CommentDto que representa el Comment mapeado.
     */
    public CommentDto mapToDTO(Comment comment) {
        return commentDtoMapper.mapToDTO(comment);
    }

    /**
     * Mapea un objeto CommentDto a un objeto Comment.
     *
     * @param commentDto El objeto CommentDto que se va a mapear.
     * @return Un objeto Comment que representa el CommentDto mapeado.
     */
    public Comment mapToEntity(CommentDto commentDto) {
        return commentDtoMapper.mapToEntity(commentDto);
    }

    /**
     * Crea un nuevo comentario para un Post especificado y devuelve su representación DTO.
     *
     * @param postId     El ID del Post al que pertenece el comentario.
     * @param commentDto El objeto CommentDto que representa el nuevo comentario.
     * @return Un objeto CommentDto que representa el comentario creado.
     */
    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);
        Post post = postRepository
                .findById(postId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Post", "id", postId));

        comment.setPost(post);
        Comment commentEntity = commentRepository.save(comment);

        return mapToDTO(commentEntity);
    }
}