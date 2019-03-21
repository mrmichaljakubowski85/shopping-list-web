package com.tomtre.shoppinglist.web.dto.converter;

import com.tomtre.shoppinglist.web.dto.UserDto;
import com.tomtre.shoppinglist.web.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserConverter implements BiDirectionalConverter<User, UserDto> {

    private final PasswordEncoder passwordEncoder;

    public UserConverter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto convertToDto(User from) {
        UserDto userDto = new UserDto();
        userDto.setUsername(from.getUsername());
        userDto.setFirstName(from.getFirstName());
        userDto.setLastName(from.getLastName());
        userDto.setEmail(from.getEmail());
        //don't set password filed
        userDto.setPassword(null);
        userDto.setMatchingPassword(null);
        return userDto;
    }

    @Override
    public User convertToEntity(UserDto from) {
        User user = new User();
        user.setUsername(from.getUsername());
        user.setFirstName(from.getFirstName());
        user.setLastName(from.getLastName());
        user.setEmail(from.getEmail());
        //encode password
        user.setPassword(passwordEncoder.encode(from.getPassword()));
        return user;
    }

}
