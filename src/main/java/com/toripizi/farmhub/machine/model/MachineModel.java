package com.toripizi.farmhub.machine.model;

import com.toripizi.farmhub.category.entity.Category;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class MachineModel {
    private String name;
    private LocalDate whenProduced;
    private Integer horsepower;
    private Category category;
}
