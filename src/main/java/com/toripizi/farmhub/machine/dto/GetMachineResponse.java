package com.toripizi.farmhub.machine.dto;

import com.toripizi.farmhub.category.entity.Category;
import com.toripizi.farmhub.farmer.entity.Farmer;
import com.toripizi.farmhub.machine.entity.Machine;
import lombok.*;

import java.time.LocalDate;
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
public class GetMachineResponse {

    private UUID id;
    private String name;
    private LocalDate whenProduced;
    private Integer horsepower;
    private Category category;
    private Farmer farmer;

    public static Function<Machine, GetMachineResponse> entityToDtoMapper() {
        return machine -> GetMachineResponse.builder()
                .id(machine.getId())
                .name(machine.getName())
                .whenProduced(machine.getWhenProduced())
                .horsepower(machine.getHorsepower())
                .category(machine.getCategory())
                .farmer(machine.getFarmer())
                .build();
    }
}
