package com.bloodycorp.currency2gif.services.gif;

import java.util.Objects;

public enum GifType {
    RICH("rich"),
    BROKE("broke");

    private final String label;

    GifType(String label) {
        Objects.requireNonNull(label);
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
