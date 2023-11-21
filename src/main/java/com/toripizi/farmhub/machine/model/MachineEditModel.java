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
public class MachineEditModel {
    private String name;
    private String whenProduced;
    private Integer horsepower;
    private Category category;
}
