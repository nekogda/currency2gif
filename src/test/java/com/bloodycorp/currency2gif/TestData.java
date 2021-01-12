package com.bloodycorp.currency2gif;

import com.bloodycorp.currency2gif.services.Currency2GifServiceResponse;
import com.bloodycorp.currency2gif.services.exchange.ExchangeRateServiceResponse;
import com.bloodycorp.currency2gif.services.exchange.api.ExchangeRateApiResponse;
import com.bloodycorp.currency2gif.services.exchange.exceptions.ExchangeRateServiceUnauthorizedException;
import com.bloodycorp.currency2gif.services.exchange.exceptions.ExchangeRateServiceUnavailableException;
import com.bloodycorp.currency2gif.services.gif.GifServiceResponse;
import com.bloodycorp.currency2gif.services.gif.api.RandomGifApiResponse;
import com.bloodycorp.currency2gif.services.gif.exceptions.GifServiceUnauthorizedException;
import com.bloodycorp.currency2gif.services.gif.exceptions.GifServiceUnavailableException;
import feign.FeignException;
import feign.Request;
import org.springframework.http.HttpStatus;

import java.util.Map;

class TestData {

    static final double EXR_LATEST_RUB_RATE_INC = 75.5437;
    static final double EXR_LATEST_RUB_RATE_DEC = 74.5437;
    static final double EXR_HISTORICAL_RUB_RATE = 74.6631;
    static final String EXR_HISTORICAL_DATE = "2021-01-11";

    static final int EXR_LATEST_TIMESTAMP = 1610431196;
    static final int EXR_HISTORICAL_TIMESTAMP = 1610409557;

    static final String API_KEY = "42";
    static final String API_KEY_INVALID = "INVALID_APP_ID";
    static final String API_KEY_TOO_MANY_STUB = "TOO_MANY";
    static final String API_KEY_EXC_INVALID_BASE_STUB = "INVALID_BASE";
    static final String EXR_REQUEST_SYMBOLS = "RUB";

    static final String GIF_RATING_CODE = "r";
    static final String GIF_RATING_CODE_INVALID = "X";
    static final String GIF_RICH_FIXED_WIDTH_RESP_URL = "https://media3.giphy.com/media/3oKGzy0EmKsFczwZpe/200w.gif?cid=732f5fa449db327a0291eb7dc6a64c1f6bb776ad90e77c32&rid=200w.gif";
    static final String GIF_RICH_RESP_URL = "https://giphy.com/gifs/earwolf-throwing-shade-bryan-safi-3oKGzy0EmKsFczwZpe";
    static final String GIF_RICH_RESP_TITLE = "rich rich podcast GIF by Earwolf";

    static final String GIF_BROKE_FIXED_WIDTH_RESP_URL = "https://media1.giphy.com/media/KE4ZvbuaE8thWKU5om/200w.gif?cid=732f5fa4986098cb5f6e92fd0dd4ddd5b7507f56502c616b&rid=200w.gif";
    static final String GIF_BROKE_RESP_URL = "https://giphy.com/gifs/IntoAction-KE4ZvbuaE8thWKU5om";
    static final String GIF_BROKE_RESP_TITLE = "Corona Rent GIF by INTO ACTION";

    static class Gif {

        private static RandomGifApiResponse getRandomGifResponse(
                HttpStatus status, String message,
                String url, String fixedUrl, String title,
                int width, int height) {
            var fixedWidth = new RandomGifApiResponse.ImageData(fixedUrl, width, height);
            var images = new RandomGifApiResponse.Images(fixedWidth);
            var data = new RandomGifApiResponse.GifObject(url, title, images);
            var meta = new RandomGifApiResponse.MetaObject(message, status.value());
            return new RandomGifApiResponse(data, meta);
        }

        static RandomGifApiResponse apiRichResponseOk() {
            return getRandomGifResponse(HttpStatus.OK, "OK",
                    GIF_RICH_RESP_URL, GIF_RICH_FIXED_WIDTH_RESP_URL, GIF_RICH_RESP_TITLE,
                    200, 160);
        }

        static RandomGifApiResponse apiBrokeResponseOk() {
            return getRandomGifResponse(HttpStatus.OK, "OK",
                    GIF_BROKE_RESP_URL, GIF_BROKE_FIXED_WIDTH_RESP_URL, GIF_BROKE_RESP_TITLE,
                    200, 200);
        }

        static RandomGifApiResponse apiBrokeResponseWithBadRequestStatus() {
            return getRandomGifResponse(HttpStatus.BAD_REQUEST, "NON_OK",
                    GIF_BROKE_RESP_URL, GIF_BROKE_FIXED_WIDTH_RESP_URL, GIF_BROKE_RESP_TITLE,
                    200, 200);
        }

        static GifServiceUnavailableException serviceUnavailableException() {
            return new GifServiceUnavailableException("test message");
        }

        static GifServiceUnauthorizedException serviceUnauthorizedException() {
            return new GifServiceUnauthorizedException("test message");
        }

        static GifServiceResponse serviceResponseForRichType() {
            return new GifServiceResponse(GIF_RICH_FIXED_WIDTH_RESP_URL, GIF_RICH_RESP_TITLE);
        }

        static GifServiceResponse serviceResponseForBrokeType() {
            return new GifServiceResponse(GIF_BROKE_FIXED_WIDTH_RESP_URL, GIF_BROKE_RESP_TITLE);
        }
    }

    static class ExchangeRate {

        static ExchangeRateApiResponse apiLatestResponseIncreaseRate() {
            var ratesMap = Map.of(EXR_REQUEST_SYMBOLS, EXR_LATEST_RUB_RATE_INC);
            return new ExchangeRateApiResponse(EXR_LATEST_TIMESTAMP, ratesMap);
        }

        static ExchangeRateApiResponse apiLatestResponseDecreaseRate() {
            var ratesMap = Map.of(EXR_REQUEST_SYMBOLS, EXR_LATEST_RUB_RATE_DEC);
            return new ExchangeRateApiResponse(EXR_LATEST_TIMESTAMP, ratesMap);
        }

        static ExchangeRateApiResponse apiLatestResponseWithEmptyRatesMap() {
            Map<String, Double> ratesMap = Map.of();
            return new ExchangeRateApiResponse(EXR_LATEST_TIMESTAMP, ratesMap);
        }

        static ExchangeRateApiResponse apiHistoricalResponse() {
            Map<String, Double> ratesMap = Map.of(EXR_REQUEST_SYMBOLS, EXR_HISTORICAL_RUB_RATE);
            return new ExchangeRateApiResponse(EXR_HISTORICAL_TIMESTAMP, ratesMap);
        }

        static ExchangeRateApiResponse apiHistoricalResponseWithEmptyRatesMap() {
            Map<String, Double> ratesMap = Map.of();
            return new ExchangeRateApiResponse(EXR_HISTORICAL_TIMESTAMP, ratesMap);
        }

        static ExchangeRateServiceResponse serviceResponseIncrease() {
            return new ExchangeRateServiceResponse(
                    EXR_LATEST_TIMESTAMP, EXR_REQUEST_SYMBOLS, EXR_LATEST_RUB_RATE_INC,
                    EXR_HISTORICAL_TIMESTAMP, EXR_HISTORICAL_RUB_RATE);
        }

        static ExchangeRateServiceResponse serviceResponseDecrease() {
            return new ExchangeRateServiceResponse(
                    EXR_LATEST_TIMESTAMP, EXR_REQUEST_SYMBOLS, EXR_LATEST_RUB_RATE_DEC,
                    EXR_HISTORICAL_TIMESTAMP, EXR_HISTORICAL_RUB_RATE);
        }

        static ExchangeRateServiceUnavailableException exchangeRateServiceUnavailableException() {
            return new ExchangeRateServiceUnavailableException("test message");
        }

        static ExchangeRateServiceUnauthorizedException exchangeRateServiceUnauthorizedException() {
            return new ExchangeRateServiceUnauthorizedException("test message");
        }
    }

    static Currency2GifServiceResponse currency2GifServiceRichResponse() {
        return new Currency2GifServiceResponse(GIF_RICH_FIXED_WIDTH_RESP_URL, EXR_LATEST_RUB_RATE_INC);
    }

    static Currency2GifServiceResponse currency2GifServiceBrokeResponse() {
        return new Currency2GifServiceResponse(GIF_BROKE_FIXED_WIDTH_RESP_URL, EXR_LATEST_RUB_RATE_DEC);
    }

    private static Request makeEmptyRequest(Request.HttpMethod method) {
        return Request.create(method, "", Map.of(), Request.Body.create(new byte[]{}), null);
    }

    static FeignException.BadRequest apiBadRequestResponse() {
        return new FeignException.BadRequest("", makeEmptyRequest(Request.HttpMethod.GET), null);
    }

    static FeignException.Unauthorized apiUnauthorizedResponse() {
        return new FeignException.Unauthorized("", makeEmptyRequest(Request.HttpMethod.GET), null);
    }

    static FeignException.TooManyRequests apiTooManyResponse() {
        return new FeignException.TooManyRequests("", makeEmptyRequest(Request.HttpMethod.GET), null);
    }

}
