package com.toripizi.farmhub.category.controller;

import com.toripizi.farmhub.category.dto.CreateCategoryRequest;
import com.toripizi.farmhub.category.dto.GetCategoryResponse;
import com.toripizi.farmhub.category.dto.GetCategoriesResponse;
import com.toripizi.farmhub.category.dto.UpdateCategoryRequest;
import com.toripizi.farmhub.category.entity.Category;
import com.toripizi.farmhub.category.service.CategoryService;
import com.toripizi.farmhub.controller.servlet.exception.BadRequestException;
import com.toripizi.farmhub.controller.servlet.exception.NotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@ApplicationScoped
public class CategorySimpleController implements CategoryController {
    private final CategoryService service;

    @Inject
    public CategorySimpleController(CategoryService service) {
        this.service = service;
    }

    @Override
    public GetCategoriesResponse getCategories() {
        List<Category> all = service.findAll();
        Function<Collection<Category>, GetCategoriesResponse> mapper = GetCategoriesResponse.entityToDtoMapper();
        return mapper.apply(all);
    }

    @Override
    public GetCategoryResponse getCategory(UUID id) {
        Function<Category, GetCategoryResponse> mapper = GetCategoryResponse.entityToDtoMapper();
        Category category = service.find(id).orElseThrow(
                () -> new NotFoundException("Could not find category of id: " + id.toString())
        );
        return mapper.apply(category);
    }

    @Override
    public void createCategory(CreateCategoryRequest req) {
        try {
            service.create(
                    CreateCategoryRequest.dtoToEntityMapper().apply(req)
            );
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void updateCategory(UUID id, UpdateCategoryRequest req) {
        service.find(id).ifPresentOrElse(
                entity -> service.update(
                        UpdateCategoryRequest.dtoToEntityMapper().apply(entity, req)
                ),
                () -> {
                    throw new NotFoundException("Could not find category of id: " + id.toString());
                }
        );
    }

    @Override
    public void deleteCategory(UUID id) {
        service.find(id).ifPresentOrElse(
                entity -> service.delete(id),
                () -> {
                    throw new NotFoundException("Could not find category of id: " + id.toString());
                }
        );
    }
}