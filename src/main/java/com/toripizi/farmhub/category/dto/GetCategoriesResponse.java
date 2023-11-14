package com.toripizi.farmhub.category.dto;

import lombok.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetCategoriesResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Category {
        private UUID id;
        private String name;
    }

    @Singular
    private List<Category> categories;

    public static Function<Collection<com.toripizi.farmhub.category.entity.Category>, GetCategoriesResponse> entityToDtoMapper() {
        return categories -> {
            GetCategoriesResponse.GetCategoriesResponseBuilder responseBuilder = GetCategoriesResponse.builder();
            categories.stream()
                    .map(category -> Category.builder()
                            .id(category.getId())
                            .name(category.getName())
                            .build())
                    .forEach(responseBuilder::category);
            return responseBuilder.build();
        };
    }
}
