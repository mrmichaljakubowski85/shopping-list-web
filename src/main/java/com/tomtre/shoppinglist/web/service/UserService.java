package com.tomtre.shoppinglist.web.service;

import com.tomtre.shoppinglist.web.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {

    boolean checkIfUsernameExists(String username);

    boolean checkIfEmailExists(String email);

    UserDto findByUsername(String username);

    UserDto findById(long userId);

    UserDto save(UserDto userDto);

    void update(UserDto userDto);

    void remove(long userId);
}
