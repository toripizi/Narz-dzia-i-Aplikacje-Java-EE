package com.toripizi.farmhub.machine.model;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class MachineryModel implements Serializable {
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
        private Long version;
        private LocalDateTime creationDateTime;
    }

    @Singular("machine")
    private List<Machine> machinery;
}
