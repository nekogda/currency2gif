package com.bloodycorp.currency2gif.services.exchange.api;

import feign.Param;
import feign.RequestLine;


public interface ExchangeRateApi {

    @RequestLine("GET /latest.json?app_id={appId}&symbols={symbols}")
    ExchangeRateApiResponse getLatest(
            @Param("appId") String appId,
            @Param("symbols") String symbols);

    @RequestLine("GET /historical/{date}.json?app_id={appId}&symbols={symbols}")
    ExchangeRateApiResponse getHistorical(
            @Param("date") String date,
            @Param("appId") String appId,
            @Param("symbols") String symbols);
}
