package com.toripizi.farmhub.category.repository;


import com.toripizi.farmhub.category.entity.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository {

    List<Category> findAll();

    Optional<Category> find(UUID id);

    void create(Category category);

    void update(Category category);

    void delete(Category category);

    void detach(Category category);
}
