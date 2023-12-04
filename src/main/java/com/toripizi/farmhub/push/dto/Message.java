package com.toripizi.farmhub.push.dto;

import lombok.*;

/**
 * WebSocket message representation.
 */
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
public class Message {

    /**
     * Message author.
     */
    private String from;

    /**
     * Message content.
     */
    private String content;

}
