package com.tomtre.shoppinglist.web.dao;

import com.tomtre.shoppinglist.web.entity.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl extends BaseDao implements RoleDao {

    public RoleDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Role findRoleByName(String roleName) {
        Session session = getSession();
        Query<Role> query = session.createQuery("FROM Role r WHERE r.name = :roleName", Role.class);
        query.setParameter("roleName", roleName);
        return query.getSingleResult();
    }
}
