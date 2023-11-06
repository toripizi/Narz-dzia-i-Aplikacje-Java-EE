package com.toripizi.farmhub.controller.servlet.exception;

/**
 * Exception indicates that client made bad request and 400 status code should be returned.
 */
public class BadRequestException extends HttpRequestException {

    private static final int RESPONSE_CODE = 400;
    public BadRequestException() {
        super(RESPONSE_CODE);
    }
    public BadRequestException(Throwable cause) {
        super(RESPONSE_CODE, cause);
    }

    public BadRequestException(Throwable cause, String desc) {
        super(RESPONSE_CODE, cause, desc);
    }
}
