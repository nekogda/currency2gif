package com.bloodycorp.currency2gif.services.exchange.exceptions;

public class ExchangeRateServiceUnauthorizedException extends RuntimeException {
    public ExchangeRateServiceUnauthorizedException() {
    }

    public ExchangeRateServiceUnauthorizedException(String message) {
        super(message);
    }

    public ExchangeRateServiceUnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExchangeRateServiceUnauthorizedException(Throwable cause) {
        super(cause);
    }
}
