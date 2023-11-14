package com.toripizi.farmhub.category.service;

import com.toripizi.farmhub.category.entity.Category;
import com.toripizi.farmhub.category.repository.CategoryRepository;
import com.toripizi.farmhub.controller.servlet.exception.NotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class CategoryService {
    private final CategoryRepository repository;

    @Inject
    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> findAll() {
        return repository.findAll();
    }

    public Optional<Category> find(UUID id) {
        return repository.find(id);
    }

    public void create(Category category) {
        repository.create(category);
    }

    public void update(Category category) {
        repository.update(category);
    }

    public void delete(UUID id) {
        repository.delete(repository.find(id).orElseThrow(
                () -> new NotFoundException("Could not find category of id: " + id.toString())
        ));
    }
}
