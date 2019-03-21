package com.tomtre.shoppinglist.web.controller;

import com.tomtre.shoppinglist.web.exception.BadRequestException;
import com.tomtre.shoppinglist.web.exception.ProductNotFoundException;
import com.tomtre.shoppinglist.web.util.ErrorUtils;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.logging.Level;
import java.util.logging.Logger;


@ControllerAdvice(basePackages = "com.tomtre.shoppinglist.web.controller")
public class WebExceptionHandler {

    private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleProductNotFoundException(ProductNotFoundException ex, Model model) {
        model.addAttribute("productId", ex.getData());
        return ErrorUtils.getProductErrorPage("Product not found - ID:", ex.getData(), model);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadRequestException(BadRequestException ex, Model model) {
        logger.log(Level.INFO ,"Exception occurred:", ex);
        return ErrorUtils.getErrorPage("Bad request", model);
    }

}
