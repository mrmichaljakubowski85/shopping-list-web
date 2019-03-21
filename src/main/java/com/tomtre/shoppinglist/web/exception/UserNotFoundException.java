package com.tomtre.shoppinglist.web.exception;

public class UserNotFoundException extends BaseDataException {
    public UserNotFoundException(String data) {
        super(data);
    }

    public UserNotFoundException(String message, String data) {
        super(message, data);
    }

    public UserNotFoundException(String message, Throwable cause, String data) {
        super(message, cause, data);
    }

    public UserNotFoundException(Throwable cause, String data) {
        super(cause, data);
    }
}
