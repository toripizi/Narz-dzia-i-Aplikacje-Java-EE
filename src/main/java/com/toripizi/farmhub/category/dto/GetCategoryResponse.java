package com.toripizi.farmhub.category.dto;

import com.toripizi.farmhub.category.entity.Category;
import com.toripizi.farmhub.machine.entity.Machine;
import lombok.*;

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
public class GetCategoryResponse {

    private UUID id;
    private String name;
    private Boolean isDrivable;
    private List<Machine> machinery;

    public static Function<Category, GetCategoryResponse> entityToDtoMapper() {
        return category -> GetCategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .isDrivable(category.getIsDrivable())
                .machinery(category.getMachinery())
                .build();
    }
}
