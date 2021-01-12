package com.bloodycorp.currency2gif;

import com.bloodycorp.currency2gif.services.Currency2GifService;
import com.bloodycorp.currency2gif.services.exceptions.Currency2GifUnauthorizedException;
import com.bloodycorp.currency2gif.services.exceptions.Currency2GifUnavailableException;
import com.bloodycorp.currency2gif.services.exchange.ExchangeRateService;
import com.bloodycorp.currency2gif.services.gif.GifService;
import com.bloodycorp.currency2gif.services.gif.GifType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@DisplayName("Currency2GifService should")
public class Currency2GifServiceTests {

    @MockBean
    ExchangeRateService exchangeRateService;

    @MockBean
    GifService gifService;

    @Autowired
    Currency2GifService currency2GifService;

    @Test
    @DisplayName("response with positive exchange rate delta and url to the 'rich' gif")
    void responseWithPositiveExchangeRateDeltaAndUrlToTheRichGif() {
        Mockito.when(exchangeRateService.getExchangeRate(TestData.EXR_REQUEST_SYMBOLS))
                .thenReturn(TestData.ExchangeRate.serviceResponseIncrease());

        Mockito.when(gifService.getGif(GifType.RICH))
                .thenReturn(TestData.Gif.serviceResponseForRichType());

        var actual = currency2GifService.getExchangeInfo(TestData.EXR_REQUEST_SYMBOLS);
        var expected = TestData.currency2GifServiceRichResponse();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("response with negative exchange rate delta and url to the 'broke' gif")
    void responseWithNegativeExchangeRateDeltaAndUrlToTheBrokeGif() {
        Mockito.when(exchangeRateService.getExchangeRate(TestData.EXR_REQUEST_SYMBOLS))
                .thenReturn(TestData.ExchangeRate.serviceResponseDecrease());

        Mockito.when(gifService.getGif(GifType.BROKE))
                .thenReturn(TestData.Gif.serviceResponseForBrokeType());

        var actual = currency2GifService.getExchangeInfo(TestData.EXR_REQUEST_SYMBOLS);
        var expected = TestData.currency2GifServiceBrokeResponse();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("throw 'Currency2GifUnavailableException' when service throw 'ExchangeRateServiceUnavailableException'")
    void throwCurrency2GifUnavailableExceptionWhenServiceThrowExchangeRateServiceUnavailableException() {
        Mockito.when(exchangeRateService.getExchangeRate(TestData.EXR_REQUEST_SYMBOLS))
                .thenThrow(TestData.ExchangeRate.exchangeRateServiceUnavailableException());

        assertThatThrownBy(() -> currency2GifService.getExchangeInfo(TestData.EXR_REQUEST_SYMBOLS))
                .isInstanceOf(Currency2GifUnavailableException.class)
                .hasMessageContaining("Temporary problem. Check logs.");
    }

    @Test
    @DisplayName("throw 'Currency2GifUnauthorizedException' when service throw 'ExchangeRateServiceUnauthorizedException'")
    void throwCurrency2GifUnauthorizedExceptionWhenServiceThrowExchangeRateServiceUnauthorizedException() {
        Mockito.when(exchangeRateService.getExchangeRate(TestData.EXR_REQUEST_SYMBOLS))
                .thenThrow(TestData.ExchangeRate.exchangeRateServiceUnauthorizedException());

        assertThatThrownBy(() -> currency2GifService.getExchangeInfo(TestData.EXR_REQUEST_SYMBOLS))
                .isInstanceOf(Currency2GifUnauthorizedException.class)
                .hasMessageContaining("Authorization failed. Check your ExchangeRate AppId token.");
    }

    @Test
    @DisplayName("throw 'Currency2GifUnavailableException' when service throw 'GifServiceUnavailableException'")
    void throwCurrency2GifUnavailableExceptionWhenServiceThrowGifServiceUnavailableException() {
        Mockito.when(exchangeRateService.getExchangeRate(TestData.EXR_REQUEST_SYMBOLS))
                .thenReturn(TestData.ExchangeRate.serviceResponseDecrease());

        Mockito.when(gifService.getGif(GifType.BROKE))
                .thenThrow(TestData.Gif.serviceUnavailableException());

        assertThatThrownBy(() -> currency2GifService.getExchangeInfo(TestData.EXR_REQUEST_SYMBOLS))
                .isInstanceOf(Currency2GifUnavailableException.class)
                .hasMessageContaining("Temporary problem. Check logs.");
    }

    @Test
    @DisplayName("throw 'Currency2GifUnauthorizedException' when service throw 'GifServiceUnauthorizedException'")
    void throwCurrency2GifUnauthorizedExceptionWhenServiceThrowGifServiceUnauthorizedException() {
        Mockito.when(exchangeRateService.getExchangeRate(TestData.EXR_REQUEST_SYMBOLS))
                .thenReturn(TestData.ExchangeRate.serviceResponseDecrease());

        Mockito.when(gifService.getGif(GifType.BROKE))
                .thenThrow(TestData.Gif.serviceUnauthorizedException());

        assertThatThrownBy(() -> currency2GifService.getExchangeInfo(TestData.EXR_REQUEST_SYMBOLS))
                .isInstanceOf(Currency2GifUnauthorizedException.class)
                .hasMessageContaining("Authorization failed. Check your GifService AppId token.");
    }
}
