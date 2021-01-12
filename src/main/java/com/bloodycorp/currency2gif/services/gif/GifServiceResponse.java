package com.bloodycorp.currency2gif.services.gif;

import java.util.Objects;

public class GifServiceResponse {
    private final String url;
    private final String title;

    public GifServiceResponse(String url, String title) {
        Objects.requireNonNull(url);
        Objects.requireNonNull(title);

        this.url = url;
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GifServiceResponse that = (GifServiceResponse) o;
        return url.equals(that.url) && title.equals(that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, title);
    }

    @Override
    public String toString() {
        return "GifServiceResponse{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
