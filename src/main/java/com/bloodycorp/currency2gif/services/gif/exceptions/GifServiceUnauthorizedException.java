package com.bloodycorp.currency2gif.services.gif.exceptions;

public class GifServiceUnauthorizedException extends RuntimeException {
    public GifServiceUnauthorizedException() {
    }

    public GifServiceUnauthorizedException(String message) {
        super(message);
    }

    public GifServiceUnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public GifServiceUnauthorizedException(Throwable cause) {
        super(cause);
    }

}
