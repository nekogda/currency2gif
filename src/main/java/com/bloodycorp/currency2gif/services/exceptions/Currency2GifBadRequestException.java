package com.bloodycorp.currency2gif.services.exceptions;

public class Currency2GifBadRequestException extends RuntimeException {
    public Currency2GifBadRequestException() {
    }

    public Currency2GifBadRequestException(String message) {
        super(message);
    }

    public Currency2GifBadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public Currency2GifBadRequestException(Throwable cause) {
        super(cause);
    }

    public Currency2GifBadRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
