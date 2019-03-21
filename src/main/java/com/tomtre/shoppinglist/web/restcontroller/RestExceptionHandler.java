package com.tomtre.shoppinglist.web.restcontroller;

import com.tomtre.shoppinglist.web.controller.WebExceptionHandler;
import com.tomtre.shoppinglist.web.dto.ErrorResponse;
import com.tomtre.shoppinglist.web.exception.ProductNotFoundException;
import com.tomtre.shoppinglist.web.exception.UnauthorizedUserException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestControllerAdvice(basePackages = "com.tomtre.shoppinglist.web.restcontroller")
public class RestExceptionHandler {

    private static final Logger logger = Logger.getLogger(WebExceptionHandler.class.getName());

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleProductNotFoundException(ProductNotFoundException ex) {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                System.currentTimeMillis());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleExceptionAuthenticationException(AuthenticationException ex) {
        logger.log(Level.INFO, "REST Exception occurred:", ex);
        return new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized",
                System.currentTimeMillis());
    }

    @ExceptionHandler(UnauthorizedUserException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleUnauthorizedUserException(UnauthorizedUserException ex) {
        logger.log(Level.INFO, "REST Exception occurred:", ex);
        return new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized",
                System.currentTimeMillis());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(Exception ex) {
        logger.log(Level.INFO, "REST Exception occurred:", ex);
        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                System.currentTimeMillis());
    }

}
