package com.bloodycorp.currency2gif.services.gif.exceptions;

public class GifServiceBadRequestException extends RuntimeException {
    public GifServiceBadRequestException() {
    }

    public GifServiceBadRequestException(String message) {
        super(message);
    }

    public GifServiceBadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public GifServiceBadRequestException(Throwable cause) {
        super(cause);
    }

}
