package com.bloodycorp.currency2gif.services;

import java.util.Objects;

public class Currency2GifServiceResponse {
    private final String url;
    private final double currentRate;

    public Currency2GifServiceResponse(String url, double currentRate) {
        Objects.requireNonNull(url, "should be not Null");
        this.url = url;
        this.currentRate = currentRate;
    }

    public String getUrl() {
        return url;
    }

    public double getCurrentRate() {
        return currentRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency2GifServiceResponse that = (Currency2GifServiceResponse) o;
        return Double.compare(that.currentRate, currentRate) == 0 && url.equals(that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, currentRate);
    }

    @Override
    public String toString() {
        return "Currency2GifServiceResponse{" +
                "url='" + url + '\'' +
                ", currentRate=" + currentRate +
                '}';
    }
}
