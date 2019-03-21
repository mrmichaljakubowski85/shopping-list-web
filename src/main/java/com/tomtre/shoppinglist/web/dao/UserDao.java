package com.tomtre.shoppinglist.web.dao;

import com.tomtre.shoppinglist.web.entity.User;

import java.util.Optional;

public interface UserDao {

    boolean checkIfUsernameExists(String username);

    boolean checkIfEmailExists(String email);

    Optional<User> findByUsername(String userName);

    User save(User user);

    Optional<User> findWithRolesByUsername(String username);

    void update(User user);

    void remove(long userId);

    Optional<User> findByUserId(long userId);
}
