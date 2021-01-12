package com.bloodycorp.currency2gif.services.exceptions;

public class Currency2GifUnavailableException extends RuntimeException {
    public Currency2GifUnavailableException() {
    }

    public Currency2GifUnavailableException(String message) {
        super(message);
    }

    public Currency2GifUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public Currency2GifUnavailableException(Throwable cause) {
        super(cause);
    }

    public Currency2GifUnavailableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
