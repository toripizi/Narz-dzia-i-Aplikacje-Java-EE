package com.toripizi.farmhub.machine.entity;

import com.toripizi.farmhub.category.entity.Category;
import com.toripizi.farmhub.farmer.entity.Farmer;
import lombok.*;
import java.time.LocalDate;
import java.util.UUID;


@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Machine {
    private UUID id;
    private String name;
    private LocalDate whenProduced;
    private int horsepower;
    private Category category;
    private Farmer farmer;
}
