package com.toripizi.farmhub.category.repository;

import com.toripizi.farmhub.category.entity.Category;
import com.toripizi.farmhub.datastore.DataStore;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class CategoryInMemoryRepository implements CategoryRepository {
    private final DataStore dataStore;

    @Inject
    public CategoryInMemoryRepository(DataStore dataStore){
        this.dataStore = dataStore;
    }

    @Override
    public List<Category> findAll() {
        return dataStore.findAllCategories();
    }

    @Override
    public Optional<Category> find(UUID id) {
        return dataStore.findAllCategories().stream()
                .filter(category -> category.getId().equals(id))
                .findFirst();
    }

    @Override
    public void create(Category category) {
        dataStore.createCategory(category);
    }

    @Override
    public void update(Category category) {
        dataStore.updateCategory(category);
    }

    @Override
    public void delete(Category category) {
        dataStore.deleteCategory(category.getId());
    }
}
