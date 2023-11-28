package com.toripizi.farmhub.category.view;

import com.toripizi.farmhub.category.model.CategoriesModel;
import com.toripizi.farmhub.category.model.function.CategoriesToModelFunction;
import com.toripizi.farmhub.category.service.CategoryService;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@RequestScoped
@Named
public class CategoryList {
    private CategoryService categoryService;
    private CategoriesModel categories;

    @Inject
    public CategoryList(CategoriesModel categories) {
        this.categories = categories;
    }

    @EJB
    public void setCategoryService(CategoryService categoryService) { this.categoryService = categoryService; }

    public CategoriesModel getCategories() {
        if (categories.getCategories() == null) {
            CategoriesToModelFunction function = new CategoriesToModelFunction();
            categories = function.apply(categoryService.findAll());
        }
        return categories;
    }

    public String deleteAction(CategoriesModel.Category category) {
        categoryService.delete(category.getId());
        return "category_list?faces-redirect=true";
    }
}
