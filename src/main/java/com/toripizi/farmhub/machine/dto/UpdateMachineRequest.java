package com.toripizi.farmhub.machine.dto;

import com.toripizi.farmhub.category.entity.Category;
import com.toripizi.farmhub.farmer.entity.Farmer;
import com.toripizi.farmhub.machine.entity.Machine;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;
import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdateMachineRequest {

    private UUID id;
    private String name;
    private LocalDate whenProduced;
    private Integer horsepower;
    private Category category;
    private Farmer farmer;

    public static BiFunction<Machine, UpdateMachineRequest, Machine> dtoToEntityMapper() {
        return (machine, req) -> Machine.builder()
                .id(req.getId())
                .name(req.getName())
                .whenProduced(req.getWhenProduced())
                .horsepower(req.getHorsepower())
                .category(req.getCategory())
                .farmer(req.getFarmer())
                .build();
    }
}
