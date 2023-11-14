package com.toripizi.farmhub.category.dto;

import com.toripizi.farmhub.category.entity.Category;
import lombok.*;

import java.util.UUID;
import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdateCategoryRequest {

    private UUID id;
    private String name;
    private Boolean isDrivable;
    private String machinery;

    public static BiFunction<Category, UpdateCategoryRequest, Category> dtoToEntityMapper() {
        return (category, req) -> Category.builder()
                .id(req.getId())
                .name(req.getName())
                .isDrivable(req.getIsDrivable())
                .machinery(category.getMachinery())
                .build();
    }
}
