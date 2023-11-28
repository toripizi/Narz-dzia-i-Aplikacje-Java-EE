package com.toripizi.farmhub.farmer.dto;

import com.toripizi.farmhub.farmer.entity.Farmer;
import com.toripizi.farmhub.machine.entity.Machine;
import lombok.*;

import java.util.List;
import java.util.UUID;
import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UpdateFarmerRequest {

    private UUID id;
    private String login;
    private String password;
    private List<Machine> machinery;

    public static BiFunction<Farmer, UpdateFarmerRequest, Farmer> dtoToEntityMapper() {
        return (farmer, req) -> Farmer.builder()
                .id(req.getId())
                .login(req.getLogin())
                .password(req.getPassword())
                .machinery(farmer.getMachinery())
                .build();
    }
}
