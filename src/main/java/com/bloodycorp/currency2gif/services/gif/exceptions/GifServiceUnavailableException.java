package com.bloodycorp.currency2gif.services.gif.exceptions;

public class GifServiceUnavailableException extends RuntimeException {
    public GifServiceUnavailableException() {
    }

    public GifServiceUnavailableException(String message) {
        super(message);
    }

    public GifServiceUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public GifServiceUnavailableException(Throwable cause) {
        super(cause);
    }

}
