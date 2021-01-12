package com.bloodycorp.currency2gif.services.exchange.exceptions;

public class ExchangeRateServiceBadRequestException extends RuntimeException {
    public ExchangeRateServiceBadRequestException() {
    }

    public ExchangeRateServiceBadRequestException(String message) {
        super(message);
    }

    public ExchangeRateServiceBadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExchangeRateServiceBadRequestException(Throwable cause) {
        super(cause);
    }
}
