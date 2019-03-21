package com.tomtre.shoppinglist.web.dao;

import com.tomtre.shoppinglist.web.entity.User;
import com.tomtre.shoppinglist.web.util.DbUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserDaoImpl extends BaseDao implements UserDao {


    public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public boolean checkIfUsernameExists(String username) {
        return DbUtils.checkIfValueExists(getSession(), User.class, User.USER_NAME_COLUMN_NAME, username);
    }

    @Override
    public boolean checkIfEmailExists(String email) {
        return DbUtils.checkIfValueExists(getSession(), User.class, User.EMAIL_COLUMN_NAME, email);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Session session = getSession();
        Query<User> query = session.createQuery("FROM User u WHERE u.username = :username", User.class);
        query.setParameter("username", username);
        return query.uniqueResultOptional();
    }

    @Override
    public Optional<User> findWithRolesByUsername(String username) {
        Session session = getSession();
        Query<User> query = session.createQuery("FROM User u JOIN FETCH u.roles r WHERE u.username = :username", User.class);
        query.setParameter("username", username);
        return query.uniqueResultOptional();
    }

    @Override
    public Optional<User> findByUserId(long userId) {
        Session session = getSession();
        User user = session.get(User.class, userId);
        return Optional.of(user);
    }

    @Override
    public void update(User user) {
        Session session = getSession();
        session.update(user);
    }

    @Override
    public void remove(long userId) {
        Session session = getSession();
        Query queryUserRole = session.createQuery("DELETE FROM User u WHERE u.id = :userId");
        queryUserRole.setParameter("userId", userId);
        queryUserRole.executeUpdate();
        Query queryUser = session.createQuery("DELETE FROM User u WHERE u.id = :userId");
        queryUser.setParameter("userId", userId);
        queryUser.executeUpdate();
    }


    @Override
    public User save(User user) {
        Session session = getSession();
        session.save(user);
        return user;
    }


}
