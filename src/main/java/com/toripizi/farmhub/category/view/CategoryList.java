package com.toripizi.farmhub.category.view;

import com.toripizi.farmhub.category.model.CategoriesModel;
import com.toripizi.farmhub.category.model.function.CategoriesToModelFunction;
import com.toripizi.farmhub.category.service.CategoryService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;

@RequestScoped
@Named
public class CategoryList implements Serializable {
    private CategoryService categoryService;
    private CategoriesModel categories;

    @Inject
    public CategoryList(CategoryService categoryService, CategoriesModel categories) {
        this.categoryService = categoryService;
        this.categories = categories;
    }

    public CategoriesModel getCategories() {
        if (categories.getCategories() == null) {
            CategoriesToModelFunction function = new CategoriesToModelFunction();
            categories = function.apply(categoryService.findAll());
        }
        return categories;
    }

    public void deleteAction(CategoriesModel.Category category) {
        categoryService.delete(category.getId());
        categories.setCategories(null);
    }
}
