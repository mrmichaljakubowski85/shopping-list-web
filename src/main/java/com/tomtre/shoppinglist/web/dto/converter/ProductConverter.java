package com.tomtre.shoppinglist.web.dto.converter;

import com.tomtre.shoppinglist.web.dto.ProductDto;
import com.tomtre.shoppinglist.web.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter implements BiDirectionalConverter<Product, ProductDto> {

    @Override
    public ProductDto convertToDto(Product from) {
        ProductDto productDto = new ProductDto();
        productDto.setId(from.getId());
        productDto.setTitle(from.getTitle());
        productDto.setDescription(from.getDescription());
        productDto.setQuantity(from.getQuantity());
        productDto.setUnit(from.getUnit());
        productDto.setMarked(from.isMarked());
        return productDto;
    }

    @Override
    public Product convertToEntity(ProductDto from) {
        Product product = new Product();
        product.setId(from.getId());
        product.setTitle(from.getTitle());
        product.setDescription(from.getDescription());
        product.setQuantity(from.getQuantity());
        product.setUnit(from.getUnit());
        product.setMarked(from.isMarked());
        return product;
    }
}
