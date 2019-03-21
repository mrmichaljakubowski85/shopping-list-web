package com.tomtre.shoppinglist.web.exception;


public class UnauthorizedUserException extends BaseDataException {


    public UnauthorizedUserException(String data) {
        super(data);
    }

    public UnauthorizedUserException(String message, String data) {
        super(message, data);
    }

    public UnauthorizedUserException(String message, Throwable cause, String data) {
        super(message, cause, data);
    }

    public UnauthorizedUserException(Throwable cause, String data) {
        super(cause, data);
    }
}
