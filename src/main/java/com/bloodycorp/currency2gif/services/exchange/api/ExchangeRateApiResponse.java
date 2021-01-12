package com.bloodycorp.currency2gif.services.exchange.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class ExchangeRateApiResponse {
    private final int timestamp;
    private final Map<String, Double> rates;

    @JsonCreator
    public ExchangeRateApiResponse(
            @JsonProperty("timestamp") int timestamp,
            @JsonProperty("rates") Map<String, Double> rates) {
        Objects.requireNonNull(rates);

        this.timestamp = timestamp;
        this.rates = rates;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public Optional<Double> getRate(String currencyCode) {
        return Optional.ofNullable(rates.get(currencyCode));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRateApiResponse that = (ExchangeRateApiResponse) o;
        return timestamp == that.timestamp && rates.equals(that.rates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, rates);
    }

    @Override
    public String toString() {
        return "ExchangeRateApiResponse{" +
                "timestamp=" + timestamp +
                ", rates=" + rates +
                '}';
    }
}
