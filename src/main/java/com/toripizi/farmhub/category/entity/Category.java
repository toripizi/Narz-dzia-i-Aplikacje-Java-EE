package com.toripizi.farmhub.category.entity;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Category {
    private UUID id;

}
