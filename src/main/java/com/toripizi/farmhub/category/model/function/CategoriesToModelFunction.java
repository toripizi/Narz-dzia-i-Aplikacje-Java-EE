package com.toripizi.farmhub.category.model.function;

import com.toripizi.farmhub.category.entity.Category;
import com.toripizi.farmhub.category.model.CategoriesModel;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CategoriesToModelFunction implements Function<List<Category>, CategoriesModel> {

    @Override
    public CategoriesModel apply(List<Category> entity) {
        return CategoriesModel.builder()
                .categories(entity.stream()
                        .map(category -> CategoriesModel.Category.builder()
                                .id(category.getId())
                                .name(category.getName())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
