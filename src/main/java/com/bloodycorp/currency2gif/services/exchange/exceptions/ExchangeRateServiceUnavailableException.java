package com.bloodycorp.currency2gif.services.exchange.exceptions;

public class ExchangeRateServiceUnavailableException extends RuntimeException {
    public ExchangeRateServiceUnavailableException() {
    }

    public ExchangeRateServiceUnavailableException(String message) {
        super(message);
    }

    public ExchangeRateServiceUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExchangeRateServiceUnavailableException(Throwable cause) {
        super(cause);
    }
}
