package com.bloodycorp.currency2gif.services.gif.api;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class RandomGifApiResponse {
    private final GifObject data;
    private final MetaObject meta;

    @JsonCreator
    public RandomGifApiResponse(
            @JsonProperty("data") GifObject data,
            @JsonProperty("meta") MetaObject meta) {
        Objects.requireNonNull(data);
        Objects.requireNonNull(meta);
        this.data = data;
        this.meta = meta;
    }

    public GifObject getData() {
        return data;
    }

    public MetaObject getMeta() {
        return meta;
    }

    public boolean isOk() {
        return meta.msg.equals("OK");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RandomGifApiResponse response = (RandomGifApiResponse) o;
        return data.equals(response.data) && meta.equals(response.meta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, meta);
    }

    @Override
    public String toString() {
        return "RandomGifResponse{" +
                "data=" + data +
                ", meta=" + meta +
                '}';
    }

    public static class GifObject {
        private final String url;
        private final Images images;
        private final String title;

        @JsonCreator
        public GifObject(
                @JsonProperty("url") String url,
                @JsonProperty("title") String title,
                @JsonProperty("images") Images images) {
            Objects.requireNonNull(url);
            Objects.requireNonNull(images);
            Objects.requireNonNull(title);
            this.url = url;
            this.images = images;
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public Images getImages() {
            return images;
        }

        public String getTitle() {
            return title;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GifObject gifObject = (GifObject) o;
            return url.equals(gifObject.url) && images.equals(gifObject.images) && title.equals(gifObject.title);
        }

        @Override
        public int hashCode() {
            return Objects.hash(url, images, title);
        }

        @Override
        public String toString() {
            return "GifObject{" +
                    "url='" + url + '\'' +
                    ", images=" + images +
                    ", title='" + title + '\'' +
                    '}';
        }
    }

    public static class MetaObject {

        private final String msg; // HTTP Response Message. (required) "OK"
        private final int status; // HTTP Response Code. (required)

        @JsonCreator
        public MetaObject(@JsonProperty("msg") String msg, @JsonProperty("status") int status) {
            Objects.requireNonNull(msg);
            this.msg = msg;
            this.status = status;
        }

        @Override
        public String toString() {
            return "MetaObject{" +
                    "msg='" + msg + '\'' +
                    ", status=" + status +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MetaObject that = (MetaObject) o;
            return status == that.status && msg.equals(that.msg);
        }

        @Override
        public int hashCode() {
            return Objects.hash(msg, status);
        }

        public String getMsg() {
            return msg;
        }

        public int getStatus() {
            return status;
        }


    }

    public static class Images {
        private final ImageData fixedWidth;

        @JsonCreator
        public Images(@JsonProperty("fixed_width") ImageData fixedWidth) {
            Objects.requireNonNull(fixedWidth);
            this.fixedWidth = fixedWidth;
        }

        public ImageData getFixedWidth() {
            return fixedWidth;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Images images = (Images) o;
            return fixedWidth.equals(images.fixedWidth);
        }

        @Override
        public int hashCode() {
            return Objects.hash(fixedWidth);
        }

        @Override
        public String toString() {
            return "Images{" +
                    "fixed_width=" + fixedWidth +
                    '}';
        }
    }

    public static class ImageData {
        private final String url;      // The publicly-accessible direct URL for this GIF.
        private final int width;       // The width of this GIF in pixels.
        private final int height;      // The height of this GIF in pixels. "200"

        @JsonCreator
        public ImageData(
                @JsonProperty("url") String url,
                @JsonProperty("width") int width,
                @JsonProperty("height") int height) {
            Objects.requireNonNull(url);
            this.url = url;
            this.width = width;
            this.height = height;
        }

        public String getUrl() {
            return url;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ImageData imageData = (ImageData) o;
            return width == imageData.width && height == imageData.height && url.equals(imageData.url);
        }

        @Override
        public int hashCode() {
            return Objects.hash(url, width, height);
        }

        @Override
        public String toString() {
            return "ImageData{" +
                    "url='" + url + '\'' +
                    ", width=" + width +
                    ", height=" + height +
                    '}';
        }
    }

}
