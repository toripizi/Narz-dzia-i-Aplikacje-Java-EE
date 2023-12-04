package com.toripizi.farmhub.category.service;

import com.toripizi.farmhub.category.entity.Category;
import com.toripizi.farmhub.category.repository.CategoryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Inject
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Optional<Category> find(UUID id) {
        return categoryRepository.find(id);
    }

    @Transactional
    public void create(Category category) {
        categoryRepository.create(category);
    }

    @Transactional
    public void update(Category category) {
        categoryRepository.update(category);
    }

    @Transactional
    public void delete(UUID id) {
        categoryRepository.delete(categoryRepository.find(id).orElseThrow(
                () -> new NotFoundException("Could not find category of id: " + id.toString())
        ));
    }
}
