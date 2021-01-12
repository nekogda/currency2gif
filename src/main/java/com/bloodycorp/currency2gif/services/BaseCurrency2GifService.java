package com.bloodycorp.currency2gif.services;

import com.bloodycorp.currency2gif.services.exceptions.Currency2GifBadRequestException;
import com.bloodycorp.currency2gif.services.exceptions.Currency2GifUnauthorizedException;
import com.bloodycorp.currency2gif.services.exceptions.Currency2GifUnavailableException;
import com.bloodycorp.currency2gif.services.exchange.ExchangeRateService;
import com.bloodycorp.currency2gif.services.exchange.ExchangeRateServiceResponse;
import com.bloodycorp.currency2gif.services.exchange.exceptions.ExchangeRateServiceBadRequestException;
import com.bloodycorp.currency2gif.services.exchange.exceptions.ExchangeRateServiceUnauthorizedException;
import com.bloodycorp.currency2gif.services.exchange.exceptions.ExchangeRateServiceUnavailableException;
import com.bloodycorp.currency2gif.services.gif.GifService;
import com.bloodycorp.currency2gif.services.gif.GifServiceResponse;
import com.bloodycorp.currency2gif.services.gif.GifType;
import com.bloodycorp.currency2gif.services.gif.exceptions.GifServiceBadRequestException;
import com.bloodycorp.currency2gif.services.gif.exceptions.GifServiceUnauthorizedException;
import com.bloodycorp.currency2gif.services.gif.exceptions.GifServiceUnavailableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class BaseCurrency2GifService implements Currency2GifService {
    private static final Logger log = LoggerFactory.getLogger(BaseCurrency2GifService.class);

    private final GifService gifService;
    private final ExchangeRateService exchangeRateService;

    public BaseCurrency2GifService(GifService gifService, ExchangeRateService exchangeRateService) {
        Objects.requireNonNull(gifService);
        Objects.requireNonNull(exchangeRateService);

        this.gifService = gifService;
        this.exchangeRateService = exchangeRateService;
    }

    @Override
    public Currency2GifServiceResponse getExchangeInfo(String currencyCode) {
        log.info("Requested exchange info for the currencyCode = '{}'", currencyCode);

        ExchangeRateServiceResponse exchangeRateResponse;
        GifServiceResponse gifServiceResponse;

        exchangeRateResponse = getExchangeRate(currencyCode);
        var gifType = getGifType(exchangeRateResponse.getRateDelta());
        gifServiceResponse = getGif(gifType);

        return new Currency2GifServiceResponse(gifServiceResponse.getUrl(), exchangeRateResponse.getCurrencyRate());
    }

    private ExchangeRateServiceResponse getExchangeRate(String currencyCode) {
        log.debug("getExchangeRate called for the currencyCode = '{}'", currencyCode);
        try {
            return exchangeRateService.getExchangeRate(currencyCode);
        } catch (ExchangeRateServiceUnauthorizedException e) {
            log.error("ExchangeRateService throws: ", e);
            throw new Currency2GifUnauthorizedException("Authorization failed. Check your ExchangeRate AppId token.");
        } catch (ExchangeRateServiceBadRequestException e) {
            log.error("ExchangeRateService throws: ", e);
            throw new Currency2GifBadRequestException("Bad request parameters. Check your configuration.");
        } catch (ExchangeRateServiceUnavailableException e) {
            log.error("ExchangeRateService throws: ", e);
            throw new Currency2GifUnavailableException("Temporary problem. Check logs.");
        }
    }

    private GifServiceResponse getGif(GifType gifType) {
        log.debug("getGif called for gifType = '{}'", gifType);
        try {
            return gifService.getGif(gifType);
        } catch (GifServiceUnauthorizedException e) {
            log.error("GifService throws: ", e);
            throw new Currency2GifUnauthorizedException("Authorization failed. Check your GifService AppId token.");
        } catch (GifServiceBadRequestException e) {
            log.error("GifService throws: ", e);
            throw new Currency2GifBadRequestException("Bad request parameters. Check your configuration.");
        } catch (GifServiceUnavailableException e) {
            log.error("GifService throws: ", e);
            throw new Currency2GifUnavailableException("Temporary problem. Check logs.");
        }
    }

    private static GifType getGifType(double rateDelta) {
        var gifType = rateDelta < 0 ? GifType.BROKE : GifType.RICH;
        log.debug("getGifType for rateDelta = {}, resulting GifType is '{}'", rateDelta, gifType.toString());
        return gifType;
    }

    @Override
    public String toString() {
        return "TextBasedC2GService{" +
                "gifService=" + gifService +
                ", currencyRateService=" + exchangeRateService +
                '}';
    }
}
