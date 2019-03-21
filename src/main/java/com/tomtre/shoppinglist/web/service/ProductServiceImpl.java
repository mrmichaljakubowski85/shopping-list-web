package com.tomtre.shoppinglist.web.service;

import com.tomtre.shoppinglist.web.dao.ProductDao;
import com.tomtre.shoppinglist.web.dto.ProductDto;
import com.tomtre.shoppinglist.web.dto.converter.ProductConverter;
import com.tomtre.shoppinglist.web.entity.Product;
import com.tomtre.shoppinglist.web.entity.User;
import com.tomtre.shoppinglist.web.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;
    private final ProductConverter productConverter;

    @Autowired
    public ProductServiceImpl(ProductDao productDao, ProductConverter productConverter) {
        this.productDao = productDao;
        this.productConverter = productConverter;
    }

    @Override
    public List<ProductDto> findProductsOrderByCreateDateTime(long userId) {
        return productConverter.convertAllToDto(productDao.findOrderByCreateDateTime(userId));
    }

    @Override
    public ProductDto getProduct(long productId, long userId) {
        return productDao.find(productId, userId)
                .map(productConverter::convertToDto)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID not found: " + productId, String.valueOf(productId)));
    }

    @Override
    public void updateProduct(ProductDto productDto, long userId) {
        Product product = productConverter.convertToEntity(productDto);
//        setProductUserRelation(product, userId);
        //todo check if id on user is correct
        //We need persisted user first to correctly fill @CreationTimestamp and @UpdateTimestamp while updating.
        productDao.find(productDto.getId(), userId)
                .ifPresent(persistedProduct -> {
                    persistedProduct.setMarked(product.isMarked());
                    persistedProduct.setDescription(product.getDescription());
                    persistedProduct.setQuantity(product.getQuantity());
                    persistedProduct.setUnit(product.getUnit());
                    persistedProduct.setTitle(product.getTitle());
                    productDao.update(persistedProduct);
                });


        productDao.update(product);
    }

    @Override
    public ProductDto addProduct(ProductDto productDto, long userId) {
        Product product = productConverter.convertToEntity(productDto);
        setProductUserRelation(product, userId);
        product.setId(null);
        Product savedProduct = productDao.save(product);
        return productConverter.convertToDto(savedProduct);
    }

    @Override
    public void removeProduct(long productId, long userId) {
        productDao.remove(productId, userId);
    }

    @Override
    public void markProduct(long productId, long userId) {
        productDao.mark(productId, userId);
    }

    @Override
    public void unmarkProduct(long productId, long userId) {
        productDao.unmark(productId, userId);
    }

    private void setProductUserRelation(Product product, long userId) {
        User user = new User();
        user.setId(userId);
        product.setUser(user);
    }
}
