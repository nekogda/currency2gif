package com.bloodycorp.currency2gif.services.exceptions;

//@ResponseStatus(value = HttpStatus.NOT_FOUND, reason="No such Order")  // 404
public class Currency2GifUnauthorizedException extends RuntimeException {
    public Currency2GifUnauthorizedException() {
    }

    public Currency2GifUnauthorizedException(String message) {
        super(message);
    }

    public Currency2GifUnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public Currency2GifUnauthorizedException(Throwable cause) {
        super(cause);
    }

    public Currency2GifUnauthorizedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
