package com.toripizi.farmhub.farmer.dto;

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
public class GetFarmersResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Farmer {
        private UUID id;
        private String login;
    }

    @Singular
    private List<Farmer> farmers;

    public static Function<Collection<com.toripizi.farmhub.farmer.entity.Farmer>, GetFarmersResponse> entityToDtoMapper() {
        return farmers -> {
            GetFarmersResponseBuilder responseBuilder = GetFarmersResponse.builder();
            farmers.stream()
                    .map(farmer -> Farmer.builder()
                            .id(farmer.getId())
                            .login(farmer.getLogin())
                            .build())
                    .forEach(responseBuilder::farmer);
            return responseBuilder.build();
        };
    }
}
