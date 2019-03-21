package com.tomtre.shoppinglist.web.dao;

import com.tomtre.shoppinglist.web.entity.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class ProductDaoImpl extends BaseDao implements ProductDao {

    public ProductDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<Product> findOrderByCreateDateTime(long userId) {
        Session session = getSession();
        Query<Product> query = session.createQuery("FROM Product p WHERE p.user.id = :userId ORDER BY p.createDateTime", Product.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public Optional<Product> find(long productId, long userId) {
        Session session = getSession();
        Query<Product> query = session.createQuery("FROM Product p WHERE p.id = :productId AND p.user.id = :userId", Product.class);
        query.setParameter("productId", productId);
        query.setParameter("userId", userId);
        return query.uniqueResultOptional();
    }

    @Override
    public void remove(long productId, long userId) {
        Session session = getSession();
        Query query = session.createQuery("DELETE FROM Product p WHERE p.id = :productId AND p.user.id = :userId");
        query.setParameter("productId", productId);
        query.setParameter("userId", userId);
        query.executeUpdate();
    }

    @Override
    public Product save(Product product) {
        Session session = getSession();
        session.save(product);
        return product;
    }

    @Override
    public void update(Product product) {
        Session session = getSession();
        session.update(product);
    }

    @Override
    public void mark(long productId, long userId) {
        Session session = getSession();
        Query query = session.createQuery("UPDATE Product p SET p.checked = true WHERE p.id = :productId AND p.user.id = :userId");
        query.setParameter("productId", productId);
        query.setParameter("userId", userId);
        query.executeUpdate();
    }

    @Override
    public void unmark(long productId, long userId) {
        Session session = getSession();
        Query query = session.createQuery("UPDATE Product p SET p.checked = false WHERE p.id = :productId AND p.user.id = :userId");
        query.setParameter("productId", productId);
        query.setParameter("userId", userId);
        query.executeUpdate();
    }

}
