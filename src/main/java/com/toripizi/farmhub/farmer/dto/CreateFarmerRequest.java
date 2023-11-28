package com.toripizi.farmhub.farmer.dto;

import com.toripizi.farmhub.farmer.entity.Farmer;
import com.toripizi.farmhub.farmer.entity.FarmerRoles;
import com.toripizi.farmhub.machine.entity.Machine;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
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
public class CreateFarmerRequest {

    private String id;
    private String login;
    private String password;
    private List<String> roles;

    public static Function<CreateFarmerRequest, Farmer> dtoToEntityMapper() {
        return req -> Farmer.builder()
                .id(UUID.fromString(req.getId()))
                .login(req.getLogin())
                .password(req.getPassword())
                .roles(req.getRoles())
                .build();
    }

}
