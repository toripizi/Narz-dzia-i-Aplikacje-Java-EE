package com.toripizi.farmhub.farmer.entity;

import com.toripizi.farmhub.machine.entity.Machine;
import jakarta.persistence.*;
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
@Entity
@Table(name = "farmers")
public class Farmer implements Serializable {
    @Id
    private UUID id;

    private String login;

    @ToString.Exclude
    private String password;

    private Role role;

    @ToString.Exclude//It's common to exclude lists from toString
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "farmer", cascade = CascadeType.REMOVE)
    private List<Machine> machinery;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private byte[] avatar;
}
