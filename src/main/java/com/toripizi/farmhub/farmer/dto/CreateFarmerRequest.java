package com.toripizi.farmhub.farmer.dto;

import com.toripizi.farmhub.farmer.entity.Farmer;
import com.toripizi.farmhub.farmer.entity.Role;
import lombok.*;

import java.util.UUID;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CreateFarmerRequest {

    private UUID id;
    private String login;
    private String password;
    private String role;
    private String machinery;

    public static Function<CreateFarmerRequest, Farmer> dtoToEntityMapper() {
        return req -> Farmer.builder()
                .id(req.getId())
                .login(req.getLogin())
                .password(req.getPassword())
                .role(Role.valueOf(req.getRole()))
//                .machinery(req.getMachinery())
                .build();
    }
}
