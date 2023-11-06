package com.toripizi.farmhub.controller.servlet.exception;

import lombok.Getter;

/**
 * Exception thrown when there is error with HTTP request.
 */
public class HttpRequestException extends RuntimeException {

    /**
     * HTTP response code.
     */
    @Getter
    private final int responseCode;

    /**
     * Optional description.
     */
    @Getter
    private String description = "";

    public HttpRequestException(int responseCode) {
        this.responseCode = responseCode;
    }

    /**
     * @param responseCode HTTP response code
     * @param description description of the exception
     */
    public HttpRequestException(int responseCode, String description) {
        this.responseCode = responseCode;
        this.description = description;
    }

    public HttpRequestException(int responseCode, Throwable cause) {
        super(cause);
        this.responseCode = responseCode;
    }

    public HttpRequestException(int responseCode, Throwable cause, String description) {
        super(cause);
        this.responseCode = responseCode;
        this.description = description;
    }

}
