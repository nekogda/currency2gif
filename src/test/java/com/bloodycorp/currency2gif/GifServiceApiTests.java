package com.bloodycorp.currency2gif;

import com.bloodycorp.currency2gif.services.gif.GifType;
import com.bloodycorp.currency2gif.services.gif.api.GifServiceApi;
import com.github.tomakehurst.wiremock.WireMockServer;
import feign.FeignException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@DisplayName("GifServiceApi should")
public class GifServiceApiTests {

    private static final String STUB_PATH = "wireMockStubs/GifServiceApi";

    private static final WireMockServer wireMockServer = new WireMockServer(
            options().usingFilesUnderClasspath(STUB_PATH));

    @Autowired
    private GifServiceApi gifServiceApi;

    @BeforeAll
    static void setUp() {
        wireMockServer.start();
    }

    @AfterAll
    static void tearDown() {
        wireMockServer.stop();
    }

    @AfterEach
    void after() {
        wireMockServer.resetAll();
    }

    @Test
    @DisplayName("response with 'OK' when requested 'rich' gif type")
    void responseWithOkWhenRequestedRichGifType() {
        var actual = gifServiceApi.getRandom(
                TestData.API_KEY, GifType.RICH.toString(), TestData.GIF_RATING_CODE);
        var expected = TestData.Gif.apiRichResponseOk();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("response with 'OK' when requested 'broke' gif type")
    void responseWithOkWhenRequestedBrokeGifType() {
        var actual = gifServiceApi.getRandom(
                TestData.API_KEY, GifType.BROKE.toString(), TestData.GIF_RATING_CODE);
        var expected = TestData.Gif.apiBrokeResponseOk();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("response with 'Forbidden' when requested with invalid AppId")
    void responseWithForbiddenWhenRequestedWithInvalidAppId() {
        assertThatThrownBy(() -> gifServiceApi.getRandom(
                TestData.API_KEY_INVALID, GifType.BROKE.toString(), TestData.GIF_RATING_CODE))
                .isInstanceOf(FeignException.Forbidden.class)
                .hasMessageContaining("Invalid authentication credentials");
    }

    @Test
    @DisplayName("response with 'BadRequest' when requested with invalid rating code")
    void responseWithBadRequestWhenRequestedWithInvalidRatingCode() {
        assertThatThrownBy(() -> gifServiceApi.getRandom(
                TestData.API_KEY, GifType.BROKE.toString(), TestData.GIF_RATING_CODE_INVALID))
                .isInstanceOf(FeignException.BadRequest.class)
                .hasMessageContaining("Invalid rating format");
    }

    @Test
    @DisplayName("response with 'TooManyRequests' when remote service is busy")
    void responseWithTooManyRequestsWhenRemoteServiceIsBusy() {
        assertThatThrownBy(() -> gifServiceApi.getRandom(
                TestData.API_KEY_TOO_MANY_STUB, GifType.BROKE.toString(), TestData.GIF_RATING_CODE))
                .isInstanceOf(FeignException.TooManyRequests.class)
                .hasMessageContaining("Service busy");
    }

}
