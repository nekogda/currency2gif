package com.bloodycorp.currency2gif;

import com.bloodycorp.currency2gif.services.gif.GifService;
import com.bloodycorp.currency2gif.services.gif.GifType;
import com.bloodycorp.currency2gif.services.gif.api.GifServiceApi;
import com.bloodycorp.currency2gif.services.gif.exceptions.GifServiceBadRequestException;
import com.bloodycorp.currency2gif.services.gif.exceptions.GifServiceUnauthorizedException;
import com.bloodycorp.currency2gif.services.gif.exceptions.GifServiceUnavailableException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@DisplayName("GifService should")
public class GifServiceTests {

    @MockBean
    private GifServiceApi gifServiceApi;

    @Autowired
    private GifService gifService;

    @Test
    @DisplayName("response when requested for 'rich' gif type")
    void responseWhenRequestedForRichGifType() {
        Mockito.when(gifServiceApi.getRandom(TestData.API_KEY, GifType.RICH.toString(), TestData.GIF_RATING_CODE))
                .thenReturn(TestData.Gif.apiRichResponseOk());

        var actual = gifService.getGif(GifType.RICH);
        var expected = TestData.Gif.serviceResponseForRichType();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("response when requested for 'broke' gif type")
    void getGifShouldReturnOkForBrokeRequestType() {
        Mockito.when(gifServiceApi.getRandom(TestData.API_KEY, GifType.BROKE.toString(), TestData.GIF_RATING_CODE))
                .thenReturn(TestData.Gif.apiBrokeResponseOk());

        var actual = gifService.getGif(GifType.BROKE);
        var expected = TestData.Gif.serviceResponseForBrokeType();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("throw GifServiceBadRequestException when api responds with 'BadRequest'")
    void throwGifServiceBadRequestExceptionWhenApiRespondsWithBadRequest() {
        Mockito.when(gifServiceApi.getRandom(TestData.API_KEY, GifType.BROKE.toString(), TestData.GIF_RATING_CODE))
                .thenThrow(TestData.apiBadRequestResponse());

        assertThatThrownBy(() -> gifService.getGif(GifType.BROKE))
                .isInstanceOf(GifServiceBadRequestException.class)
                .hasMessageContaining("Request rejected, check parameters");
    }

    @Test
    @DisplayName("throw GifServiceUnauthorizedException when api responds with 'Unauthorized'")
    void throwGifServiceUnauthorizedExceptionWhenApiRespondsWithUnauthorized() {
        Mockito.when(gifServiceApi.getRandom(TestData.API_KEY, GifType.BROKE.toString(), TestData.GIF_RATING_CODE))
                .thenThrow(TestData.apiUnauthorizedResponse());

        assertThatThrownBy(() -> gifService.getGif(GifType.BROKE))
                .isInstanceOf(GifServiceUnauthorizedException.class)
                .hasMessageContaining("AppId malformed or expire");
    }

    @Test
    @DisplayName("throw GifServiceUnavailableException when api responds with 'TooManyRequests'")
    void throwGifServiceUnavailableExceptionWhenApiRespondsWithTooManyRequests() {
        Mockito.when(gifServiceApi.getRandom(TestData.API_KEY, GifType.BROKE.toString(), TestData.GIF_RATING_CODE))
                .thenThrow(TestData.apiTooManyResponse());

        assertThatThrownBy(() -> gifService.getGif(GifType.BROKE))
                .isInstanceOf(GifServiceUnavailableException.class)
                .hasMessageContaining("GifService unavailable");
    }

    @Test
    @DisplayName("throw GifServiceUnavailableException when api responds with malformed status code")
    void throwGifServiceUnavailableExceptionWhenApiRespondsWithMalformedStatusCode() {
        Mockito.when(gifServiceApi.getRandom(TestData.API_KEY, GifType.BROKE.toString(), TestData.GIF_RATING_CODE))
                .thenReturn(TestData.Gif.apiBrokeResponseWithBadRequestStatus());

        assertThatThrownBy(() -> gifService.getGif(GifType.BROKE))
                .isInstanceOf(GifServiceUnavailableException.class)
                .hasMessageContaining("Bad GifServiceApi response");
    }
}
