package com.bloodycorp.currency2gif.services.gif.api;

import feign.Param;
import feign.RequestLine;

public interface GifServiceApi {
    @RequestLine("GET /gifs/random?api_key={apiKey}&tag={tag}&rating={rating}")
    RandomGifApiResponse getRandom(
            @Param("apiKey") String apiKey,
            @Param("tag") String tag,
            @Param("rating") String rating);
}
