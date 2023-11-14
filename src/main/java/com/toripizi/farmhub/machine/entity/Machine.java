package com.toripizi.farmhub.machine.entity;

import com.toripizi.farmhub.category.entity.Category;
import com.toripizi.farmhub.farmer.entity.Farmer;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;


@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Machine implements Serializable  {
    private UUID id;
    private String name;
    private LocalDate whenProduced;
    private Integer horsepower;
    private Category category;
    private Farmer farmer;
}
