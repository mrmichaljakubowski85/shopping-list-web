package com.tomtre.shoppinglist.web.service;

import com.tomtre.shoppinglist.web.dto.ProductDto;

import java.util.List;


public interface ProductService {

    List<ProductDto> findProductsOrderByCreateDateTime(long userId);

    ProductDto getProduct(long productId, long userId);

    void updateProduct(ProductDto productDto, long userId);

    ProductDto addProduct(ProductDto productDto, long userId);

    void removeProduct(long productId, long userId);

    void markProduct(long productId, long userId);

    void unmarkProduct(long productId, long userId);
}
