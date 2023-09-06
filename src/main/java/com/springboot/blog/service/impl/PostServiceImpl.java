package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.payload.mapper.PostDtoMapper;
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

/**
 * Implementaci&oacute;n del servicio para gestionar publicaciones (posts).
 * <p>
 * Esta clase proporciona m&eacute;todos para realizar operaciones CRUD en publicaciones.
 * <p>
 * @version 1.0
 * @since 2023-09-01
 * @author Victor Felipe Alvarez Rojas
 * @Modyfied: 2021-09-01 - documentación y legibilidad usando injección de dependencias de métodos
 * @Modyfiedby: Victor Felipe Alvarez Rojas
 */
@Service
public class PostServiceImpl implements PostService {
    private final PostResponseMapper postResponseMapper;
    private final PostRepository postRepository;
    private final PostDtoMapper postDtoMapper;

    public PostServiceImpl(
            PostRepository postRepository,
            PostResponseMapper postResponseMapper,
            PostDtoMapper postDtoMapper
    ) {
        this.postRepository = postRepository;
        this.postResponseMapper = postResponseMapper;
        this.postDtoMapper = postDtoMapper;
    }

    /**
     * Convierte un objeto de tipo Post en un objeto de tipo PostDto utilizando el mapeador (mapper) PostDtoMapper.
     *
     * @param post El objeto Post que se va a convertir en PostDto.
     * @return Un objeto PostDto resultado de la conversión.
     */
    private final PostDto mapToDTO(Post post) {
        return postDtoMapper.mapToDTO(post);
    }

    /**
     * Convierte un objeto de tipo PostDto en un objeto de tipo Post utilizando el mapeador (mapper) PostDtoMapper.
     *
     * @param postDto El objeto PostDto que se va a convertir en Post.
     * @return Un objeto Post resultado de la conversión.
     */
    private final Post mapToEntity(PostDto postDto) {
        return postDtoMapper.mapToEntity(postDto);
    }

    /**
     * Construye un objeto de tipo PostResponse utilizando un listado de objetos PostDto y una página de objetos Post.
     *
     * @param contentDto La lista de objetos PostDto que se va a incluir en la respuesta.
     * @param postsPage  La página de objetos Post que se va a incluir en la respuesta.
     * @return Un objeto PostResponse construido a partir de los datos proporcionados.
     */
    private final PostResponse getBuild(List<PostDto> contentDto, Page<Post> postsPage) {
        return postResponseMapper.getBuild(contentDto, postsPage);
    }

    /**
     * Obtiene una publicación por su ID.
     *
     * @param id El ID de la publicación que se desea obtener.
     * @return El objeto PostDto correspondiente al ID proporcionado.
     * @throws ResourceNotFoundException Si no se encuentra la publicación con el ID especificado.
     */
    @Override
    public PostDto getPostById(Long id) {
        return postRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
    }

    /**
     * Obtiene todas las publicaciones paginadas y ordenadas.
     *
     * @param page      El número de página.
     * @param size      El tamaño de la página.
     * @param sortBy    El campo por el que se desea ordenar.
     * @param sortOrder El sentido de la ordenación (ascendente o descendente).
     * @return Una respuesta que contiene las publicaciones paginadas y ordenadas.
     */
    @Override
    public PostResponse getAllPosts(int page, int size, String sortBy, String sortOrder) {

        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Post> postsPage = postRepository.findAll(pageable);

        List<PostDto> contentDto = postsPage.getContent()
                .stream()
                .map(this::mapToDTO)
                .toList();

        return getBuild(contentDto, postsPage);
    }

    /**
     * Crea una nueva publicación.
     *
     * @param postDto El objeto PostDto que contiene los datos de la publicación a crear.
     * @return El objeto PostDto de la publicación creada.
     */
    @Override
    public PostDto createPost(PostDto postDto) {
        Post newPost = postRepository.save(mapToEntity(postDto));
        return mapToDTO(newPost);
    }

    /**
     * Actualiza una publicación existente por su ID.
     *
     * @param id      El ID de la publicación que se desea actualizar.
     * @param postDto El objeto PostDto con los nuevos datos de la publicación.
     * @return El objeto PostDto actualizado.
     * @throws ResourceNotFoundException Si no se encuentra la publicación con el ID especificado.
     */
    @Override
    public PostDto updatePost(Long id, PostDto postDto) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isEmpty()) return null;
        return mapToDTO(postRepository.save(mapToEntity(postDto)));
    }

    /**
     * Elimina una publicación por su ID.
     *
     * @param id El ID de la publicación que se desea eliminar.
     * @throws ResourceNotFoundException Si no se encuentra la publicación con el ID especificado.
     */
    @Override
    public void deletePost(Long id) {
        postRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.deleteById(id);
    }
}