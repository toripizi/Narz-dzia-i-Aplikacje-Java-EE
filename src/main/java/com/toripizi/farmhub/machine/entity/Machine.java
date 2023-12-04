package com.toripizi.farmhub.machine.entity;

import com.toripizi.farmhub.category.entity.Category;
import com.toripizi.farmhub.entity.VersionAndCreationDateAuditable;
import com.toripizi.farmhub.farmer.entity.Farmer;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "machinery")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Machine extends VersionAndCreationDateAuditable implements Serializable {
    @Id
    private UUID id;

    private String name;

    private LocalDate whenProduced;

    private Integer horsepower;

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "farmer_login")
    private Farmer farmer;
}
