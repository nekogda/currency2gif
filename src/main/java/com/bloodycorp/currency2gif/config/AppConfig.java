package com.bloodycorp.currency2gif.config;

import org.hibernate.validator.constraints.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ConstructorBinding
@ConfigurationProperties("currency2gif")
@Validated
public class AppConfig {

    @NotNull
    @URL
    private String gifServiceApiUrl;

    @NotNull
    @URL
    private String exchangeRateServiceApiUrl;

    @NotNull
    @Size(min = 3, max = 3)
    private String exchangeRateServiceCurrencyCode;

    @Min(1)
    @Max(300)
    private int gifServiceConnectionTimeoutSec;

    @Min(1)
    @Max(300)
    private int gifServiceReadTimeoutSec;

    @Min(1)
    @Max(300)
    private int exchangeRateServiceConnectionTimeoutSec;

    @Min(1)
    @Max(300)
    private int exchangeRateServiceReadTimeoutSec;

    @NotNull
    private String gifServiceAppId;

    @NotNull
    @Size(min = 1, max = 1)
    private String gifServiceRating;

    @NotNull
    private String exchangeRateServiceAppId;

    public String getGifServiceRating() {
        return gifServiceRating;
    }

    public void setGifServiceRating(String gifServiceRating) {
        this.gifServiceRating = gifServiceRating;
    }

    public String getGifServiceApiUrl() {
        return gifServiceApiUrl;
    }

    public void setGifServiceApiUrl(String gifServiceApiUrl) {
        this.gifServiceApiUrl = gifServiceApiUrl;
    }

    public String getExchangeRateServiceApiUrl() {
        return exchangeRateServiceApiUrl;
    }

    public void setExchangeRateServiceApiUrl(String exchangeRateServiceApiUrl) {
        this.exchangeRateServiceApiUrl = exchangeRateServiceApiUrl;
    }

    public String getExchangeRateServiceCurrencyCode() {
        return exchangeRateServiceCurrencyCode;
    }

    public void setExchangeRateServiceCurrencyCode(String exchangeRateServiceCurrencyCode) {
        this.exchangeRateServiceCurrencyCode = exchangeRateServiceCurrencyCode;
    }

    public int getGifServiceConnectionTimeoutSec() {
        return gifServiceConnectionTimeoutSec;
    }

    public void setGifServiceConnectionTimeoutSec(int gifServiceConnectionTimeoutSec) {
        this.gifServiceConnectionTimeoutSec = gifServiceConnectionTimeoutSec;
    }

    public int getGifServiceReadTimeoutSec() {
        return gifServiceReadTimeoutSec;
    }

    public void setGifServiceReadTimeoutSec(int gifServiceReadTimeoutSec) {
        this.gifServiceReadTimeoutSec = gifServiceReadTimeoutSec;
    }

    public int getExchangeRateServiceConnectionTimeoutSec() {
        return exchangeRateServiceConnectionTimeoutSec;
    }

    public void setExchangeRateServiceConnectionTimeoutSec(int exchangeRateServiceConnectionTimeoutSec) {
        this.exchangeRateServiceConnectionTimeoutSec = exchangeRateServiceConnectionTimeoutSec;
    }

    public int getExchangeRateServiceReadTimeoutSec() {
        return exchangeRateServiceReadTimeoutSec;
    }

    public void setExchangeRateServiceReadTimeoutSec(int exchangeRateServiceReadTimeoutSec) {
        this.exchangeRateServiceReadTimeoutSec = exchangeRateServiceReadTimeoutSec;
    }

    public String getGifServiceAppId() {
        return gifServiceAppId;
    }

    public void setGifServiceAppId(String gifServiceAppId) {
        this.gifServiceAppId = gifServiceAppId;
    }

    public String getExchangeRateServiceAppId() {
        return exchangeRateServiceAppId;
    }

    public void setExchangeRateServiceAppId(String exchangeRateServiceAppId) {
        this.exchangeRateServiceAppId = exchangeRateServiceAppId;
    }

    @Override
    public String toString() {
        return "AppConfig{" +
                "gifServiceApiUrl='" + gifServiceApiUrl + '\'' +
                ", exchangeRateServiceApiUrl='" + exchangeRateServiceApiUrl + '\'' +
                ", exchangeRateServiceCurrencyCode='" + exchangeRateServiceCurrencyCode + '\'' +
                ", gifServiceConnectionTimeoutSec=" + gifServiceConnectionTimeoutSec +
                ", gifServiceReadTimeoutSec=" + gifServiceReadTimeoutSec +
                ", exchangeRateServiceConnectionTimeoutSec=" + exchangeRateServiceConnectionTimeoutSec +
                ", exchangeRateServiceReadTimeoutSec=" + exchangeRateServiceReadTimeoutSec +
                ", gifServiceAppId='" + gifServiceAppId + '\'' +
                ", gifServiceRating='" + gifServiceRating + '\'' +
                ", exchangeRateServiceAppId='" + exchangeRateServiceAppId + '\'' +
                '}';
    }
}
