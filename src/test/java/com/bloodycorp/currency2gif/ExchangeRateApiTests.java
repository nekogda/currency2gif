package com.bloodycorp.currency2gif;

import com.bloodycorp.currency2gif.services.exchange.api.ExchangeRateApi;
import com.github.tomakehurst.wiremock.WireMockServer;
import feign.FeignException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@DisplayName("ExchangeRateApi should")
public class ExchangeRateApiTests {

    private static final String STUB_PATH = "wireMockStubs/ExchangeRateApi";

    private static final WireMockServer wireMockServer = new WireMockServer(
            options().usingFilesUnderClasspath(STUB_PATH));

    @Autowired
    private ExchangeRateApi exchangeRateApi;

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
    @DisplayName("response with 'OK' when requested latest rates")
    void responseWithOkWhenRequestedLatestRates() {
        var actual = exchangeRateApi.getLatest(
                TestData.API_KEY, TestData.EXR_REQUEST_SYMBOLS);
        var expected = TestData.ExchangeRate.apiLatestResponseDecreaseRate();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("response with 'Unauthorized' when requested with invalid AppId")
    void responseWithUnauthorizedWhenApiRequestedWithInvalidAppId() {
        assertThatThrownBy(() -> exchangeRateApi.getLatest(
                TestData.API_KEY_INVALID, TestData.EXR_REQUEST_SYMBOLS))
                .isInstanceOf(FeignException.Unauthorized.class)
                .hasMessageContaining("Invalid App ID provided");
    }

    @Test
    @DisplayName("response with 'BadRequest' when requested with invalid base code")
    void responseWithBadRequestWhenApiRequestedWithInvalidBaseCode() {
        assertThatThrownBy(() -> exchangeRateApi.getLatest(
                TestData.API_KEY_EXC_INVALID_BASE_STUB, TestData.EXR_REQUEST_SYMBOLS))
                .isInstanceOf(FeignException.BadRequest.class)
                .hasMessageContaining("invalid_base");
    }

    @Test
    @DisplayName("response with 'TooManyRequests' when requested with insufficient access permissions")
    void responseWithTooManyRequestsWhenRequestedInsufficientAccessPermissions() {
        assertThatThrownBy(() -> exchangeRateApi.getLatest(
                TestData.API_KEY_TOO_MANY_STUB, TestData.EXR_REQUEST_SYMBOLS))
                .isInstanceOf(FeignException.TooManyRequests.class)
                .hasMessageContaining("not_allowed");
    }

    @Test
    @DisplayName("response with 'OK' when requested latest rates")
    void responseWithOkWhenRequestedHistoricalRates() {
        var actual = exchangeRateApi.getHistorical(
                TestData.EXR_HISTORICAL_DATE, TestData.API_KEY, TestData.EXR_REQUEST_SYMBOLS);
        var expected = TestData.ExchangeRate.apiHistoricalResponse();
        assertThat(actual).isEqualTo(expected);
    }

}
