package com.tomtre.shoppinglist.web.restcontroller;

import com.tomtre.shoppinglist.web.config.RestServiceDescriptor;
import com.tomtre.shoppinglist.web.dto.CustomSecurityUser;
import com.tomtre.shoppinglist.web.dto.UserDto;
import com.tomtre.shoppinglist.web.exception.UnauthorizedUserException;
import com.tomtre.shoppinglist.web.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(RestServiceDescriptor.FULL_PATH)
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{userId}")
    public UserDto getUser(@AuthenticationPrincipal CustomSecurityUser customSecurityUser, @PathVariable long userId) {
        checkAuthorizationUserId(customSecurityUser, userId);
        return userService.findById(userId);
    }

    //todo REMOVE ROLES
    @PostMapping("/users")
    public UserDto addUser(@RequestBody UserDto userDto) {
        return userService.save(userDto);
    }

    @PutMapping("/users")
    public UserDto updateUser(@AuthenticationPrincipal CustomSecurityUser customSecurityUser, @RequestBody UserDto userDto) {
        checkAuthorizationUserId(customSecurityUser, userDto.getId());
        userService.update(userDto);
        return userDto;
    }

    @DeleteMapping("/users/{userId}")
    public long deleteUser(@AuthenticationPrincipal CustomSecurityUser customSecurityUser, @PathVariable long userId) {
        checkAuthorizationUserId(customSecurityUser, userId);
        userService.remove(userId);
        return userId;
    }

    private void checkAuthorizationUserId(CustomSecurityUser customSecurityUser, long userIdToCheck) {
        if (customSecurityUser.getId() != userIdToCheck) {
            throw new UnauthorizedUserException("Authentication principal ID is different than requested: " + userIdToCheck, String.valueOf(userIdToCheck));
        }
    }

    private void checkAuthorizationUsername(CustomSecurityUser customSecurityUser, String usernameToCheck) {
        if (!customSecurityUser.getUsername().equals(usernameToCheck)) {
            throw new UnauthorizedUserException("Authentication principal username is different than requested: " + usernameToCheck, usernameToCheck);
        }
    }
}
