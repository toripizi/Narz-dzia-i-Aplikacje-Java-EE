package com.toripizi.farmhub.machine.dto;

import lombok.*;

import java.util.Collection;
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
public class GetMachineryResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Machine {
        private UUID id;
        private String name;
        private Integer horsepower;
    }

    @Singular("machine")
    private List<Machine> machinery;

    public static Function<Collection<com.toripizi.farmhub.machine.entity.Machine>, GetMachineryResponse> entityToDtoMapper() {
        return machinery -> {
            GetMachineryResponse.GetMachineryResponseBuilder responseBuilder = GetMachineryResponse.builder();
            machinery.stream()
                    .map(machine -> Machine.builder()
                            .id(machine.getId())
                            .name(machine.getName())
                            .horsepower(machine.getHorsepower())
                            .build())
                    .forEach(responseBuilder::machine);
            return responseBuilder.build();
        };
    }
}
