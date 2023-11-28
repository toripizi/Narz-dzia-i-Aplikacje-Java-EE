package com.toripizi.farmhub.machine.dto;

import com.toripizi.farmhub.category.entity.Category;
import com.toripizi.farmhub.farmer.entity.Farmer;
import com.toripizi.farmhub.machine.entity.Machine;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreateMachineRequest {

    private String name;
    private String whenProduced;
    private Integer horsepower;
    private UUID categoryId;
    private UUID farmerId;

    public static Function<CreateMachineRequest, Machine> dtoToEntityMapper() {
        return req -> Machine.builder()
                .id(UUID.randomUUID())
                .name(req.getName())
                .whenProduced(LocalDate.parse(req.getWhenProduced()))
                .horsepower(req.getHorsepower())
                .category(Category.builder()
                        .id(req.getCategoryId())
                        .build())
                .farmer(Farmer.builder()
                        .id(req.getFarmerId())
                        .build())
                .build();
    }
}
