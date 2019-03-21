package com.tomtre.shoppinglist.web.util;

import org.springframework.ui.Model;

public class ErrorUtils {
    public static String getErrorPage(String message, Model model) {
        model.addAttribute("message", message);
        return "error";
    }

    public static String getProductErrorPage(String message, String productId, Model model) {
        model.addAttribute("message", message);
        model.addAttribute("productId", productId);
        return "product-error";
    }
}
