package com.toripizi.farmhub.farmer.model;

import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * JSF view model class in order to not use entity classes. Represents list of farmers to be displayed.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class FarmersModel implements Serializable {

    /**
     * Represents single farmer in list.
     */
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Farmer {

        /**
         * Unique id identifying character.
         */
        private UUID id;

        /**
         * Name of the character.
         */
        private String login;

    }

    /**
     * List of farmers.
     */
    @Singular
    private List<Farmer> farmers;

}
