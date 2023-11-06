package com.toripizi.farmhub.farmer.entity;

import com.toripizi.farmhub.machine.entity.Machine;
import lombok.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Farmer implements Serializable {
    private UUID id;
    private String login;
    private String password;
    private Role role;
    private List<Machine> machinery;
}
