package com.tomtre.shoppinglist.web.exception;

import java.util.UUID;

public class ProductNotFoundException extends BaseDataException {


    public ProductNotFoundException(String data) {
        super(data);
    }

    public ProductNotFoundException(String message, String data) {
        super(message, data);
    }

    public ProductNotFoundException(String message, Throwable cause, String data) {
        super(message, cause, data);
    }

    public ProductNotFoundException(Throwable cause, String data) {
        super(cause, data);
    }
}
