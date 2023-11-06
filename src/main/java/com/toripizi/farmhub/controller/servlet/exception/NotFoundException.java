package com.toripizi.farmhub.controller.servlet.exception;

/**
 * Exception indicates that requested resource was not found and 404 status code should be returned.
 */
public class NotFoundException extends HttpRequestException {

    private static final int RESPONSE_CODE = 404;
    public NotFoundException(String description) {
        super(RESPONSE_CODE, description);
    }

    public NotFoundException(Throwable cause, String desc) {
        super(RESPONSE_CODE, cause, desc);
    }
}
