package com.bloodycorp.currency2gif.services.exchange;

import java.util.Objects;

public class ExchangeRateServiceResponse {
    private final int timestamp;
    private final String currencyCode;
    private final double currencyRate;
    private final int prevTimestamp;
    private final double prevCurrencyRate;


    public ExchangeRateServiceResponse(int timestamp, String currencyCode, double currencyRate,
                                       int prevTimestamp, double prevCurrencyRate) {
        Objects.requireNonNull(currencyCode);

        this.timestamp = timestamp;
        this.currencyCode = currencyCode;
        this.currencyRate = currencyRate;
        this.prevTimestamp = prevTimestamp;
        this.prevCurrencyRate = prevCurrencyRate;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public double getCurrencyRate() {
        return currencyRate;
    }

    public int getPrevTimestamp() {
        return prevTimestamp;
    }

    public double getPrevCurrencyRate() {
        return prevCurrencyRate;
    }

    public double getRateDelta() {
        return currencyRate - prevCurrencyRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRateServiceResponse that = (ExchangeRateServiceResponse) o;
        return timestamp == that.timestamp && Double.compare(that.currencyRate, currencyRate) == 0 && prevTimestamp == that.prevTimestamp && Double.compare(that.prevCurrencyRate, prevCurrencyRate) == 0 && currencyCode.equals(that.currencyCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, currencyCode, currencyRate, prevTimestamp, prevCurrencyRate);
    }

    @Override
    public String toString() {
        return "ExchangeRateServiceResponse{" +
                "timestamp=" + timestamp +
                ", currencyCode='" + currencyCode + '\'' +
                ", currencyRate=" + currencyRate +
                ", prevTimestamp=" + prevTimestamp +
                ", prevCurrencyRate=" + prevCurrencyRate +
                '}';
    }
}
