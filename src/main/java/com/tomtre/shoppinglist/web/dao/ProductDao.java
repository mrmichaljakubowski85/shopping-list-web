package com.tomtre.shoppinglist.web.dao;

import com.tomtre.shoppinglist.web.entity.Product;

import java.util.List;
import java.util.Optional;


public interface ProductDao {

    List<Product> findOrderByCreateDateTime(long userId);

    Optional<Product> find(long productId, long userId);

    void remove(long productId, long userId);

    Product save(Product product);

    void update(Product product);

    void mark(long productId, long userId);

    void unmark(long productId, long userId);

}
