package com.tomtre.shoppinglist.web.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public abstract class BaseDao {
    protected final SessionFactory sessionFactory;

    public BaseDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
