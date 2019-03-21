package com.tomtre.shoppinglist.web.util;

import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class DbUtils {
    public static boolean checkIfValueExists(Session session, Class<?> entityClass, String columnName, String value) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
        Root root = criteriaQuery.from(entityClass);
        criteriaQuery.select(root.get(columnName));
        criteriaQuery.where(criteriaBuilder.equal(root.get(columnName), value));
        Query query = session.createQuery(criteriaQuery);
        Object result = query.uniqueResult();
        return result != null;
    }
}
