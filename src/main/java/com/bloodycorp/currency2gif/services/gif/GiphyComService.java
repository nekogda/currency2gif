package com.bloodycorp.currency2gif.services.gif;

import com.bloodycorp.currency2gif.services.gif.api.GifServiceApi;
import com.bloodycorp.currency2gif.services.gif.api.RandomGifApiResponse;
import com.bloodycorp.currency2gif.services.gif.exceptions.GifServiceUnauthorizedException;
import com.bloodycorp.currency2gif.services.gif.exceptions.GifServiceBadRequestException;
import com.bloodycorp.currency2gif.services.gif.exceptions.GifServiceUnavailableException;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class GiphyComService implements GifService {
    private static final Logger log = LoggerFactory.getLogger(GiphyComService.class);
    private final GifServiceApi api;
    private final String appId;
    private final String rating;


    public GiphyComService(GifServiceApi api, String appId, String rating) {
        Objects.requireNonNull(api);
        Objects.requireNonNull(appId);
        this.appId = appId;
        this.api = api;
        this.rating = rating;
    }

    @Override
    public GifServiceResponse getGif(GifType tag) {
        log.info("requested gif for tag = '{}'", tag);

        RandomGifApiResponse apiResponse;

        try {
            apiResponse = api.getRandom(appId, tag.toString(), rating);
        } catch (FeignException.Forbidden | FeignException.Unauthorized e) {
            log.error("Remote host return exception: ", e);
            throw new GifServiceUnauthorizedException("AppId malformed or expire");
        } catch (FeignException.BadRequest e) {
            log.error("Remote host return reject request: ", e);
            throw new GifServiceBadRequestException("Request rejected, check parameters");
        } catch (RuntimeException e) {
            log.error("GifServiceApi exception occurred: ", e);
            throw new GifServiceUnavailableException("GifService unavailable");
        }

        if (!apiResponse.isOk()) {
            log.error("GifServiceApi returns error with wrong HTTP status");
            throw new GifServiceUnavailableException("Bad GifServiceApi response");
        }

        String url = apiResponse.getData().getImages().getFixedWidth().getUrl();

        return new GifServiceResponse(url, apiResponse.getData().getTitle());
    }
}
