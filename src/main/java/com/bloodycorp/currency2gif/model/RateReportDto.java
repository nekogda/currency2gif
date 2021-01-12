package com.bloodycorp.currency2gif.model;

import java.util.Objects;

public class RateReportDto {
    private String url;
    private double currencyRate;

    public RateReportDto() {
    }

    public RateReportDto(String url, double currencyRate) {
        this.setUrl(url);
        this.setCurrencyRate(currencyRate);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        Objects.requireNonNull(url);
        this.url = url;
    }

    public double getCurrencyRate() {
        return currencyRate;
    }

    public void setCurrencyRate(double currencyRate) {
        this.currencyRate = currencyRate;
    }
}
