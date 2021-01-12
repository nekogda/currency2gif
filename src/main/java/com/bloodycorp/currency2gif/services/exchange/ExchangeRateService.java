package com.bloodycorp.currency2gif.services.exchange;

public interface ExchangeRateService {
    ExchangeRateServiceResponse getExchangeRate(String currencyCode);
}
