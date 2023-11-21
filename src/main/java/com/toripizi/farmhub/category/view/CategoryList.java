package com.delinac.tourhub.category.view;

import com.toripizi.farmhub.category.model.CategoriesModel;
import com.toripizi.farmhub.category.model.function.CategoriesToModelFunction;
import com.toripizi.farmhub.category.service.CategoryService;
import com.toripizi.farmhub.machine.service.MachineService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@RequestScoped
@Named
public class CategoryList {
    private final CategoryService categoryService;
    private final MachineService machineService;
    private CategoriesModel categories;
//    private final ModelFunctionFactory factory;

    @Inject
    public CategoryList(CategoryService categoryService, MachineService machineService, CategoriesModel categories) {
        this.categoryService = categoryService;
        this.machineService = machineService;
        this.categories = categories;
    }

    public CategoriesModel getCategories() {
        if (categories.getCategories() == null) {
            CategoriesToModelFunction function = new CategoriesToModelFunction();
            categories = function.apply(categoryService.findAll());
        }
        return categories;
    }

    public String deleteAction(CategoriesModel.Category category) {
        machineService.deleteByCategoryId(category.getId());
        categoryService.delete(category.getId());
        return "category_list?faces-redirect=true";
    }
}
