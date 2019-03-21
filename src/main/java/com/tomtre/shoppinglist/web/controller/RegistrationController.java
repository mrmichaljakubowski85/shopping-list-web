package com.tomtre.shoppinglist.web.controller;

import com.tomtre.shoppinglist.web.dto.UserDto;
import com.tomtre.shoppinglist.web.service.UserService;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/form")
    public String registerForm(Model model) {
        model.addAttribute("registerUser", new UserDto());
        return "registration-form";
    }

    @PostMapping("/processRegistration")
    public String processRegistration(
            @ModelAttribute("registerUser") @Valid UserDto userDto,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "registration-form";
        }

        String userName = userDto.getUsername();
        boolean userNameExists = userService.checkIfUsernameExists(userName);
        if (userNameExists) {
            return handleUserOrEmailExists(model);
        }

        String email = userDto.getEmail();
        boolean emailExists = userService.checkIfEmailExists(email);
        if (emailExists) {
            return handleUserOrEmailExists(model);
        }

        //create new account
        userService.save(userDto);

        return "registration-confirmation";
    }

    private String handleUserOrEmailExists(Model model) {
        model.addAttribute("registerUser", new UserDto());
        model.addAttribute("registrationError", "User name or email already exists.");
        return "registration-form";
    }
}
