package org.example.linkshorter.controller;

import org.example.linkshorter.dto.RegistrationDto;
import org.example.linkshorter.service.control.AuthService;
import org.example.linkshorter.util.RegistrationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final RegistrationValidator registrationValidator;
    private final AuthService authService;

    @Autowired
    public AuthController(RegistrationValidator registrationValidator, AuthService authService) {
        this.registrationValidator = registrationValidator;
        this.authService = authService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user") RegistrationDto user) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String register(@Valid @ModelAttribute("user") RegistrationDto user, BindingResult bindingResult) {
        registrationValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/auth/registration";
        }
        authService.register(user);
        return "redirect:/auth/login";
    }
}
