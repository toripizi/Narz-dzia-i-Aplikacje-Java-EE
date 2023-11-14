package com.toripizi.farmhub.category.controller;

import com.toripizi.farmhub.category.dto.CreateCategoryRequest;
import com.toripizi.farmhub.category.dto.GetCategoriesResponse;
import com.toripizi.farmhub.category.dto.GetCategoryResponse;
import com.toripizi.farmhub.category.dto.UpdateCategoryRequest;

import java.util.UUID;

public interface CategoryController {
    GetCategoriesResponse getCategories();

    GetCategoryResponse getCategory(UUID id);

    void createCategory(CreateCategoryRequest req);

    void updateCategory(UUID id, UpdateCategoryRequest req);

    void deleteCategory(UUID id);

}
