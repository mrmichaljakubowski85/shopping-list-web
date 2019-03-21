package com.tomtre.shoppinglist.web.controller;

import com.tomtre.shoppinglist.web.dto.CustomSecurityUser;
import com.tomtre.shoppinglist.web.dto.ProductDto;
import com.tomtre.shoppinglist.web.exception.BadRequestException;
import com.tomtre.shoppinglist.web.exception.ProductNotFoundException;
import com.tomtre.shoppinglist.web.service.ProductService;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/list")
    public String listProducts(@AuthenticationPrincipal CustomSecurityUser customSecurityUser, Model model) {
        List<ProductDto> productsDto = productService.findProductsOrderByCreateDateTime(customSecurityUser.getId());
        model.addAttribute("products", productsDto);
        return "product-list";
    }

    @GetMapping("/add")
    public String addProductForm(Model model) {
        ProductDto productDto = new ProductDto();
        model.addAttribute("product", productDto);
        return "add-edit-product";
    }

    @PostMapping("/edit")
    public String editProduct(@AuthenticationPrincipal CustomSecurityUser customSecurityUser, @RequestParam("productId") long productId, Model model)
            throws ProductNotFoundException {
        ProductDto productDto = productService.getProduct(productId, customSecurityUser.getId());
        model.addAttribute("product", productDto);
        return "add-edit-product";
    }

    @PostMapping("/save")
    public String saveProduct(@AuthenticationPrincipal CustomSecurityUser customSecurityUser, @ModelAttribute("product") @Valid ProductDto productDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add-edit-product";
        }
        if (productDto.getId() == null) {
            productService.addProduct(productDto, customSecurityUser.getId());
        } else {
            productService.updateProduct(productDto, customSecurityUser.getId());
        }
        return "redirect:/product/list";
    }

    @PostMapping("/delete")
    public String deleteProduct(@AuthenticationPrincipal CustomSecurityUser customSecurityUser, @RequestParam("productId") long productId) {
        productService.removeProduct(productId, customSecurityUser.getId());
        return "redirect:/product/list";
    }

    @PostMapping("/details")
    public String productDetails(@AuthenticationPrincipal CustomSecurityUser customSecurityUser, @RequestParam("productId") long productId, Model model) {
        ProductDto productDto = productService.getProduct(productId, customSecurityUser.getId());
        model.addAttribute("product", productDto);
        return "product-details";
    }

    @PostMapping("/mark")
    public String markProduct(@AuthenticationPrincipal CustomSecurityUser customSecurityUser, @RequestParam("productId") long productId, @RequestParam("action") String actionType) {
        switch (actionType) {
            case "mark":
                productService.markProduct(productId, customSecurityUser.getId());
                break;
            case "unmark":
                productService.unmarkProduct(productId, customSecurityUser.getId());
                break;
            default:
                throw new BadRequestException();
        }
        return "redirect:/product/list";
    }

}
