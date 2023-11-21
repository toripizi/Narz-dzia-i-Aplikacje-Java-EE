package com.toripizi.farmhub.category.model;

import com.toripizi.farmhub.machine.entity.Machine;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CategoryModel {
    private UUID id;
    private String name;
    private Boolean isDrivable;
    private List<Machine> machinery;
}
