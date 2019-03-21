package com.tomtre.shoppinglist.web.exception;


public abstract class BaseDataException extends RuntimeException {
    private final String data;

    BaseDataException(String data) {
        this.data = data;
    }

    BaseDataException(String message, String data) {
        super(message);
        this.data = data;
    }

    BaseDataException(String message, Throwable cause, String data) {
        super(message, cause);
        this.data = data;
    }

    BaseDataException(Throwable cause, String data) {
        super(cause);
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
