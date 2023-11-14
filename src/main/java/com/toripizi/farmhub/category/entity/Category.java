package com.toripizi.farmhub.category.entity;

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
public class Category implements Serializable {
    private UUID id;
    private String name;
    private Boolean isDrivable;
    private List<Machine> machinery;
}
