package com.toripizi.farmhub.category.model.function;

import com.toripizi.farmhub.category.entity.Category;
import com.toripizi.farmhub.category.model.CategoryModel;

import java.io.Serializable;
import java.util.function.Function;

public class CategoryToModelFunction implements Function<Category, CategoryModel>, Serializable {
    @Override
    public CategoryModel apply(Category entity) {
        return CategoryModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .isDrivable(entity.getIsDrivable())
                .machinery(entity.getMachinery())
                .build();
    }
}
