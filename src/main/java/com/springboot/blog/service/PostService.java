package com.springboot.blog.service;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;

import java.util.List;

/**
 * Interfaz para gestionar operaciones relacionadas con publicaciones (posts).
 */
public interface PostService {

    /**
     * Obtiene todas las publicaciones paginadas y ordenadas.
     *
     * @param page      El número de página.
     * @param size      El tamaño de la página.
     * @param sortBy    El campo por el que se desea ordenar.
     * @param sortOrder El sentido de la ordenación (ascendente o descendente).
     * @return Una respuesta que contiene las publicaciones paginadas y ordenadas.
     */
    PostResponse getAllPosts(int page, int size, String sortBy, String sortOrder);

    /**
     * Crea una nueva publicación.
     *
     * @param postDto El objeto PostDto que contiene los datos de la publicación a crear.
     * @return El objeto PostDto de la publicación creada.
     */
    PostDto createPost(PostDto postDto);

    /**
     * Actualiza una publicación existente por su ID.
     *
     * @param id      El ID de la publicación que se desea actualizar.
     * @param postDto El objeto PostDto con los nuevos datos de la publicación.
     * @return El objeto PostDto actualizado.
     */
    PostDto updatePost(Long id, PostDto postDto);

    /**
     * Obtiene una publicación por su ID.
     *
     * @param id El ID de la publicación que se desea obtener.
     * @return El objeto PostDto correspondiente al ID proporcionado.
     */
    PostDto getPostById(Long id);

    /**
     * Elimina una publicación por su ID.
     *
     * @param id El ID de la publicación que se desea eliminar.
     */
    void deletePost(Long id);
}
