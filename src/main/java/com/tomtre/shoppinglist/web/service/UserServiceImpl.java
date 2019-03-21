package com.tomtre.shoppinglist.web.service;

import com.tomtre.shoppinglist.web.config.security.SecurityRoles;
import com.tomtre.shoppinglist.web.dao.RoleDao;
import com.tomtre.shoppinglist.web.dao.UserDao;
import com.tomtre.shoppinglist.web.dto.CustomSecurityUser;
import com.tomtre.shoppinglist.web.dto.UserDto;
import com.tomtre.shoppinglist.web.dto.converter.UserConverter;
import com.tomtre.shoppinglist.web.entity.Role;
import com.tomtre.shoppinglist.web.entity.User;
import com.tomtre.shoppinglist.web.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final UserConverter userConverter;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao, UserConverter userConverter) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.userConverter = userConverter;
    }

    @Override
    public boolean checkIfUsernameExists(String username) {
        return userDao.checkIfUsernameExists(username);
    }

    @Override
    public boolean checkIfEmailExists(String email) {
        return userDao.checkIfEmailExists(email);
    }

    @Override
    public UserDto findByUsername(String username) {
        return userDao.findByUsername(username).
                map(userConverter::convertToDto).
                orElseThrow(() -> new UserNotFoundException("User with username not found: " + username, username));
    }

    @Override
    public UserDto findById(long userId) {
        return userDao.findByUserId(userId)
                .map(userConverter::convertToDto)
                .orElseThrow(() -> new UserNotFoundException("User with ID not found: " + userId, String.valueOf(userId)));
    }

    @Override
    public UserDto save(UserDto userDto) {
        User user = userConverter.convertToEntity(userDto);
        setUserRoleRelation(user);
        user.setId(null);
        User savedUser = userDao.save(user);
        return userConverter.convertToDto(savedUser);
    }

    @Override
    public void update(UserDto userDto) {
        User user = userConverter.convertToEntity(userDto);
//        setUserRoleRelation(user);
        //We need persisted user first to correctly fill @CreationTimestamp and @UpdateTimestamp while updating.
        //todo check if roles are correct
        userDao.findByUserId(user.getId())
                .ifPresent(persistedUser -> {
                    persistedUser.setFirstName(user.getFirstName());
                    persistedUser.setLastName(user.getLastName());
                    persistedUser.setEmail(user.getEmail());
                    persistedUser.setPassword(user.getPassword());
                    userDao.update(persistedUser);
                });
    }

    @Override
    public void remove(long userId) {
        userDao.remove(userId);
    }

    //from UserDetailsService
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return userDao.findWithRolesByUsername(userName)
                .map(this::convertUserToCustomSecurityUser)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username"));
    }

    private CustomSecurityUser convertUserToCustomSecurityUser(User user) {
        return new CustomSecurityUser.Builder()
                .setUsername(user.getUsername())
                .setId(user.getId())
                .setPassword(user.getPassword())
                .setFullName((user.getFirstName() != null ? user.getFirstName() : "") + " " + user.getLastName())
                .setAuthorities(mapRolesToAuthorities(user.getRoles()))
                .build();
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    private void setUserRoleRelation(User user) {
        Role userRole = roleDao.findRoleByName(SecurityRoles.DB_ROLE_USER);
        user.setRoles(Collections.singletonList(userRole));
    }
}
