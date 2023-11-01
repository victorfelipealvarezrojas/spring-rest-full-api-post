package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Category;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CategoryDto;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Agrega una nueva categoría.
     *
     * @param categoryDto Objeto de tipo CategoryDto que representa la categoría a agregar.
     * @return Objeto de tipo CategoryDto que representa la categoría agregada.
     */
    @Transactional
    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        return mapToEntity.andThen(categoryRepository::save)
                .andThen(mapToDto)
                .apply(categoryDto);
    }

    /**
     * Obtiene una categoría por su identificador.
     *
     * @param id Identificador de la categoría a recuperar.
     * @return Objeto de tipo CategoryDto que representa la categoría recuperada.
     * @throws ResourceNotFoundException Si no se encuentra la categoría con el ID dado.
     */
    @Override
    public CategoryDto getCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category", "id", id));

        return mapToDto.apply(category);
    }

    /**
     * Obtiene todas las categorías.
     *
     * @return Lista de objetos de tipo CategoryDto que representan todas las categorías.
     */
    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> category = categoryRepository.findAll();
        if (category.isEmpty()) return null;
        return category.stream().map(mapToDto).toList();
    }

    /**
     * Actualiza una categoría existente.
     *
     * @param id          Identificador de la categoría a actualizar.
     * @param categoryDto Objeto de tipo CategoryDto que contiene los datos actualizados.
     * @return Objeto de tipo CategoryDto que representa la categoría actualizada.
     * @throws ResourceNotFoundException Si no se encuentra la categoría con el ID dado.
     */
    @Override
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", id));

        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        return mapToDto.apply(categoryRepository.save(category));
    }

    /**
     * Elimina una categoría por su identificador.
     *
     * @param id Identificador de la categoría a eliminar.
     * @throws ResourceNotFoundException Si no se encuentra la categoría con el ID dado.
     */
    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", id));

        categoryRepository.delete(category);
    }

    private final Function<CategoryDto, Category> mapToEntity = (CategoryDto dto) ->
            Category.builder()
                    .id(dto.getId())
                    .name(dto.getName())
                    .description(dto.getDescription())
                    .build();


    private final Function<Category, CategoryDto> mapToDto = (Category entity) ->
            CategoryDto.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .description(entity.getDescription())
                    .build();
}