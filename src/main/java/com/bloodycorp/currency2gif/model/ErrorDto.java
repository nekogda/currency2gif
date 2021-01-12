package com.bloodycorp.currency2gif.model;

public class ErrorDto {
    private String message;

    public ErrorDto() {
    }

    public ErrorDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorDto{" +
                "message='" + message + '\'' +
                '}';
    }
}
