package com.homihq.manager.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.validation.constraints.NotBlank;


//@Controller
@Slf4j
public class GuestController {

    @GetMapping(value = {"/", "/signin"})
    public String showSignupForm(Model model) {
        log.info("Show registration");

        UserForm userForm = new UserForm();
        model.addAttribute(userForm);
        return "signin";
    }


    @GetMapping("/signup")
    public String showRegistrationForm(Model model) {
        log.info("Show registration");

        UserForm userForm = new UserForm();
        model.addAttribute(userForm);
        return "signup";
    }

    @Data
    public class UserForm {
        @NotBlank
        private String firstName;

        @NotBlank
        private String lastName;

        @NotBlank
        private String password;
        @NotBlank
        private String matchingPassword;

        @NotBlank
        private String email;

    }
}
