package com.bloodycorp.currency2gif.config;

import com.bloodycorp.currency2gif.services.BaseCurrency2GifService;
import com.bloodycorp.currency2gif.services.Currency2GifService;
import com.bloodycorp.currency2gif.services.exchange.ExchangeRateService;
import com.bloodycorp.currency2gif.services.exchange.OpenExchangeRatesCom;
import com.bloodycorp.currency2gif.services.exchange.api.ExchangeRateApi;
import com.bloodycorp.currency2gif.services.gif.GifService;
import com.bloodycorp.currency2gif.services.gif.GiphyComService;
import com.bloodycorp.currency2gif.services.gif.api.GifServiceApi;
import feign.Feign;
import feign.Request;
import feign.jackson.JacksonDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class BeanConfig {

    @Bean
    Currency2GifService currency2GifService(GifService gs, ExchangeRateService crs) {
        return new BaseCurrency2GifService(gs, crs);
    }

    @Bean
    ExchangeRateApi exchangeRateApi(AppConfig cfg) {
        return Feign.builder()
                .decoder(new JacksonDecoder())
                .options(new Request.Options(cfg.getExchangeRateServiceConnectionTimeoutSec(), TimeUnit.SECONDS,
                        cfg.getExchangeRateServiceReadTimeoutSec(), TimeUnit.SECONDS, true))
                .target(ExchangeRateApi.class, cfg.getExchangeRateServiceApiUrl());
    }

    @Bean
    GifServiceApi gifServiceApi(AppConfig cfg) {
        return Feign.builder()
                .decoder(new JacksonDecoder())
                .options(new Request.Options(cfg.getGifServiceConnectionTimeoutSec(), TimeUnit.SECONDS,
                        cfg.getGifServiceReadTimeoutSec(), TimeUnit.SECONDS, true))
                .target(GifServiceApi.class, cfg.getGifServiceApiUrl());
    }

    @Bean
    GifService gifService(AppConfig cfg, GifServiceApi api) {
        return new GiphyComService(api, cfg.getGifServiceAppId(), cfg.getGifServiceRating());
    }

    @Bean
    ExchangeRateService exchangeRateService(AppConfig cfg, ExchangeRateApi api) {
        return new OpenExchangeRatesCom(api, cfg.getExchangeRateServiceAppId());
    }

}
