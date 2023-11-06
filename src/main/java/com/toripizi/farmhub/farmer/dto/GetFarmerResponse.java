package com.toripizi.farmhub.farmer.dto;

import com.toripizi.farmhub.farmer.entity.Farmer;
import com.toripizi.farmhub.farmer.entity.Role;
import com.toripizi.farmhub.machine.entity.Machine;
import lombok.*;

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
public class GetFarmerResponse {
    private UUID id;
    private String login;
    private List<Machine> machinery;
    private Role role;

    public static Function<Farmer, GetFarmerResponse> entityToDtoMapper() {
        return farmer -> GetFarmerResponse.builder()
                .id(farmer.getId())
                .login(farmer.getLogin())
                .machinery(farmer.getMachinery())
                .role(farmer.getRole())
                .build();
    }
}
