package com.tomtre.shoppinglist.web.restcontroller;

import com.tomtre.shoppinglist.web.config.RestServiceDescriptor;
import com.tomtre.shoppinglist.web.dto.CustomSecurityUser;
import com.tomtre.shoppinglist.web.dto.ProductDto;
import com.tomtre.shoppinglist.web.service.ProductService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RestServiceDescriptor.FULL_PATH)
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<ProductDto> getProducts(@AuthenticationPrincipal CustomSecurityUser customSecurityUser) {
        return productService.findProductsOrderByCreateDateTime(customSecurityUser.getId());
    }

    @GetMapping("/products/{productId}")
    public ProductDto getProduct(@AuthenticationPrincipal CustomSecurityUser customSecurityUser, @PathVariable long productId) {
        return productService.getProduct(productId, customSecurityUser.getId());
    }

    @PostMapping("/products")
    public ProductDto addProduct(@AuthenticationPrincipal CustomSecurityUser customSecurityUser, @RequestBody ProductDto productDto) {
        return productService.addProduct(productDto, customSecurityUser.getId());
    }

    @PutMapping("/products")
    public ProductDto updateProduct(@AuthenticationPrincipal CustomSecurityUser customSecurityUser, @RequestBody ProductDto productDto) {
        productService.updateProduct(productDto, customSecurityUser.getId());
        return productDto;
    }

    @DeleteMapping("/products/{productId}")
    public long deleteProduct(@AuthenticationPrincipal CustomSecurityUser customSecurityUser, @PathVariable long productId) {
        productService.removeProduct(productId, customSecurityUser.getId());
        return productId;
    }

}
