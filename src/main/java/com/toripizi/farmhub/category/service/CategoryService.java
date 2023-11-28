package com.toripizi.farmhub.category.service;

import com.toripizi.farmhub.category.entity.Category;
import com.toripizi.farmhub.category.repository.CategoryRepository;
import com.toripizi.farmhub.farmer.entity.FarmerRoles;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Inject
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @PermitAll
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @PermitAll
    public Optional<Category> find(UUID id) {
        return categoryRepository.find(id);
    }

    @RolesAllowed(FarmerRoles.ADMIN)
    public void create(Category category) {
        categoryRepository.create(category);
    }

    @RolesAllowed(FarmerRoles.ADMIN)
    public void update(Category category) {
        categoryRepository.update(category);
    }

    @RolesAllowed(FarmerRoles.ADMIN)
    public void delete(UUID id) {
        categoryRepository.delete(categoryRepository.find(id).orElseThrow(
                () -> new NotFoundException("Could not find category of id: " + id.toString())
        ));
    }
}
