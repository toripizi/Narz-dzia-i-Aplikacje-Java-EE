package com.toripizi.farmhub.category.dto;

import com.toripizi.farmhub.category.entity.Category;
import lombok.*;

import java.util.UUID;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreateCategoryRequest {

    private UUID id;
    private String name;
    private Boolean isDrivable;
    private String machinery;

    public static Function<CreateCategoryRequest, Category> dtoToEntityMapper() {
        return req -> Category.builder()
                .id(req.getId())
                .name(req.getName())
                .isDrivable(req.getIsDrivable())
//                .machinery(req.getMachinery())
                .build();
    }
}
