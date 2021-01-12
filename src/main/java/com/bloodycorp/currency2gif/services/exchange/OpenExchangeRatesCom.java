package com.bloodycorp.currency2gif.services.exchange;

import com.bloodycorp.currency2gif.services.exchange.api.ExchangeRateApi;
import com.bloodycorp.currency2gif.services.exchange.api.ExchangeRateApiResponse;
import com.bloodycorp.currency2gif.services.exchange.exceptions.ExchangeRateServiceUnauthorizedException;
import com.bloodycorp.currency2gif.services.exchange.exceptions.ExchangeRateServiceBadRequestException;
import com.bloodycorp.currency2gif.services.exchange.exceptions.ExchangeRateServiceUnavailableException;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.function.Supplier;

public class OpenExchangeRatesCom implements ExchangeRateService {
    private final static Logger log = LoggerFactory.getLogger(OpenExchangeRatesCom.class);

    private final String appId;

    private final ExchangeRateApi exchangeRateApi;

    public OpenExchangeRatesCom(ExchangeRateApi api, String appId) {
        Objects.requireNonNull(api);
        Objects.requireNonNull(appId);

        this.appId = appId;
        this.exchangeRateApi = api;
    }

    @Override
    public ExchangeRateServiceResponse getExchangeRate(String currencyCode) {
        log.info("requested ExchangeRate for the currencyCode = '{}'", currencyCode);

        var latestResponse = checkedApiRequest(() ->
                exchangeRateApi.getLatest(appId, currencyCode));

        var prevDateTime = getPrevDateFrom(latestResponse.getTimestamp(),
                DateTimeFormatter.ISO_DATE);

        var prevDayResponse = checkedApiRequest(() ->
                exchangeRateApi.getHistorical(prevDateTime, appId, currencyCode));

        var currencyRate = latestResponse.getRate(currencyCode)
                .orElseThrow(() -> new ExchangeRateServiceBadRequestException(
                        "There is no such currencyCode in the latest response"));

        var prevCurrencyRate = prevDayResponse.getRate(currencyCode)
                .orElseThrow(() -> new ExchangeRateServiceBadRequestException(
                        "There is no such currencyCode in the historical response"));

        return new ExchangeRateServiceResponse(
                latestResponse.getTimestamp(),
                currencyCode,
                currencyRate,
                prevDayResponse.getTimestamp(),
                prevCurrencyRate);
    }

    private static String getPrevDateFrom(int epochSecond, DateTimeFormatter dateFormat) {
        log.debug("computing previous date from epochSecond = {}, and dateFormat = {}", epochSecond, dateFormat);
        var date = Instant.ofEpochSecond(epochSecond)
                .atZone(ZoneId.systemDefault()).toLocalDate().minusDays(1);
        return date.format(dateFormat);
    }

    private ExchangeRateApiResponse checkedApiRequest(Supplier<ExchangeRateApiResponse> block) {
        ExchangeRateApiResponse apiResponse;
        try {
            apiResponse = block.get();
        } catch (FeignException.Unauthorized e) {
            log.error("Remote host return exception: ", e);
            throw new ExchangeRateServiceUnauthorizedException("AppId malformed or expire");
        } catch (FeignException.BadRequest e) {
            log.error("Remote host return reject request: ", e);
            throw new ExchangeRateServiceBadRequestException("Request rejected, check parameters");
        } catch (RuntimeException e) {
            log.error("ExchangeRateServiceApi exception occurred: ", e);
            throw new ExchangeRateServiceUnavailableException("ExchangeRateServiceApi unavailable");
        }
        return apiResponse;
    }
}
