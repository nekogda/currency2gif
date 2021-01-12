package com.bloodycorp.currency2gif;

import com.bloodycorp.currency2gif.services.exchange.ExchangeRateService;
import com.bloodycorp.currency2gif.services.exchange.api.ExchangeRateApi;
import com.bloodycorp.currency2gif.services.exchange.exceptions.ExchangeRateServiceBadRequestException;
import com.bloodycorp.currency2gif.services.exchange.exceptions.ExchangeRateServiceUnauthorizedException;
import com.bloodycorp.currency2gif.services.exchange.exceptions.ExchangeRateServiceUnavailableException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@DisplayName("ExchangeRateService should")
public class ExchangeRateServiceTests {

    @MockBean
    private ExchangeRateApi exchangeRateApi;

    @Autowired
    private ExchangeRateService exchangeRateService;

    @Test
    @DisplayName("response with 'rich' gif when rates delta is positive")
    void responseWithRichGifWhenRatesDeltaIsPositive() {
        var expectedLatest = TestData.ExchangeRate
                .apiLatestResponseIncreaseRate();
        var expectedHistorical = TestData.ExchangeRate
                .apiHistoricalResponse();

        Mockito.when(exchangeRateApi.getLatest(TestData.API_KEY, TestData.EXR_REQUEST_SYMBOLS))
                .thenReturn(expectedLatest);
        Mockito.when(exchangeRateApi.getHistorical(
                TestData.EXR_HISTORICAL_DATE, TestData.API_KEY, TestData.EXR_REQUEST_SYMBOLS))
                .thenReturn(expectedHistorical);

        var expected = TestData.ExchangeRate.serviceResponseIncrease();
        var actual = exchangeRateService.getExchangeRate(TestData.EXR_REQUEST_SYMBOLS);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("response with 'rich' gif when rates delta is negative")
    void responseWithRichGifWhenRatesDeltaIsNegative() {
        var expectedLatest = TestData.ExchangeRate
                .apiLatestResponseDecreaseRate();
        var expectedHistorical = TestData.ExchangeRate
                .apiHistoricalResponse();

        Mockito.when(exchangeRateApi.getLatest(TestData.API_KEY, TestData.EXR_REQUEST_SYMBOLS))
                .thenReturn(expectedLatest);
        Mockito.when(exchangeRateApi.getHistorical(
                TestData.EXR_HISTORICAL_DATE, TestData.API_KEY, TestData.EXR_REQUEST_SYMBOLS))
                .thenReturn(expectedHistorical);

        var expected = TestData.ExchangeRate.serviceResponseDecrease();
        var actual = exchangeRateService.getExchangeRate(TestData.EXR_REQUEST_SYMBOLS);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("throw 'ExchangeRateServiceBadRequestException' when api responds with 'BadRequest'")
    void throwExchangeRateServiceBadRequestExceptionWhenApiRespondsWithBadRequest() {
        Mockito.when(exchangeRateApi.getLatest(TestData.API_KEY, TestData.EXR_REQUEST_SYMBOLS))
                .thenThrow(TestData.apiBadRequestResponse());

        assertThatThrownBy(() -> exchangeRateService.getExchangeRate(TestData.EXR_REQUEST_SYMBOLS))
                .isInstanceOf(ExchangeRateServiceBadRequestException.class)
                .hasMessageContaining("Request rejected, check parameters");
    }

    @Test
    @DisplayName("throw 'ExchangeRateServiceUnauthorizedException' when api responds with 'Unauthorized'")
    void throwExchangeRateServiceUnauthorizedExceptionWhenApiRespondsUnauthorized() {
        Mockito.when(exchangeRateApi.getLatest(TestData.API_KEY, TestData.EXR_REQUEST_SYMBOLS))
                .thenThrow(TestData.apiUnauthorizedResponse());

        assertThatThrownBy(() -> exchangeRateService.getExchangeRate(TestData.EXR_REQUEST_SYMBOLS))
                .isInstanceOf(ExchangeRateServiceUnauthorizedException.class)
                .hasMessageContaining("AppId malformed or expire");
    }

    @Test
    @DisplayName("throw 'ExchangeRateServiceUnavailableException' when api responds with 'TooManyRequests'")
    void throwExchangeRateServiceUnavailableExceptionWhenApiRespondsTooManyRequests() {
        Mockito.when(exchangeRateApi.getLatest(TestData.API_KEY, TestData.EXR_REQUEST_SYMBOLS))
                .thenThrow(TestData.apiTooManyResponse());

        assertThatThrownBy(() -> exchangeRateService.getExchangeRate(TestData.EXR_REQUEST_SYMBOLS))
                .isInstanceOf(ExchangeRateServiceUnavailableException.class)
                .hasMessageContaining("ExchangeRateServiceApi unavailable");
    }

    @Test
    @DisplayName("throw 'ExchangeRateServiceBadRequestException' when latest api response doesn't contains currency rate")
    void throwExchangeRateServiceBadRequestExceptionWhenLatestApiResponseDoesntContainsCurrencyRate() {
        var expectedLatest = TestData.ExchangeRate
                .apiLatestResponseWithEmptyRatesMap();

        Mockito.when(exchangeRateApi.getLatest(TestData.API_KEY, TestData.EXR_REQUEST_SYMBOLS))
                .thenReturn(expectedLatest);

        assertThatThrownBy(() -> exchangeRateService.getExchangeRate(TestData.EXR_REQUEST_SYMBOLS))
                .isInstanceOf(ExchangeRateServiceBadRequestException.class)
                .hasMessageContaining("There is no such currencyCode in the latest response");
    }

    @Test
    @DisplayName("throw 'ExchangeRateServiceBadRequestException' when historical api response doesn't contains currency rate")
    void throwExchangeRateServiceBadRequestExceptionWhenHistoricalApiResponseDoesntContainsCurrencyRate() {
        var expectedLatestExchangeRateApiResponse = TestData.ExchangeRate
                .apiLatestResponseDecreaseRate();
        var expectedHistoricalApiResponse = TestData.ExchangeRate
                .apiHistoricalResponseWithEmptyRatesMap();

        Mockito.when(exchangeRateApi.getLatest(TestData.API_KEY, TestData.EXR_REQUEST_SYMBOLS))
                .thenReturn(expectedLatestExchangeRateApiResponse);
        Mockito.when(exchangeRateApi.getHistorical(
                TestData.EXR_HISTORICAL_DATE, TestData.API_KEY, TestData.EXR_REQUEST_SYMBOLS))
                .thenReturn(expectedHistoricalApiResponse);

        assertThatThrownBy(() -> exchangeRateService.getExchangeRate(TestData.EXR_REQUEST_SYMBOLS))
                .isInstanceOf(ExchangeRateServiceBadRequestException.class)
                .hasMessageContaining("There is no such currencyCode in the historical response");
    }
}
