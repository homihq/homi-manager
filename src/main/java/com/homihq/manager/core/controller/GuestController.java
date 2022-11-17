package com.homihq.manager.core.controller;


import com.homihq.manager.core.domain.Role;
import com.homihq.manager.core.domain.User;
import com.homihq.manager.core.service.UserCommandUseCase;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Locale;


@Controller
@Slf4j
@RequiredArgsConstructor
public class GuestController {

    private final UserCommandUseCase userCommandUseCase;
    private final ModelMapper modelMapper;

    @Value("${app.signup.verify}")
    private String verifyUrl;

    @GetMapping("/signin")
    public String gotoLogin(Model model){
        return "signin";
    }

    @GetMapping("/signup")
    public String gotoRegister(Model model){
        log.info("User signup");
        model.addAttribute(new UserRegistrationForm());

        return "signup";
    }

    @GetMapping("/verification")
    public String verification(@RequestParam("cd") String code, Model model) {
        log.info("Email link verification - {}", code);

        this.userCommandUseCase.verify(new UserCommandUseCase.EmailLinkVerificationCommand(code));
        model.addAttribute("successKey", "success.signup.emailverified");
        return "linkverified";
    }

    @PostMapping("/signup")
    public String register(@Valid UserRegistrationForm userRegistrationForm, BindingResult bindingResult, Locale locale,
                           HttpServletRequest httpServletRequest,
                           Model model){
        log.info("Registering user - {}", userRegistrationForm);
        model.addAttribute(userRegistrationForm);

        if (bindingResult.hasErrors()) {
            log.info("Form validation error. - {}", bindingResult);
            log.info("Model - {}", model);
            return "signup";
        }

        UserCommandUseCase.UserRegistrationCommand userRegistrationCommand =
        modelMapper.map(userRegistrationForm, UserCommandUseCase.UserRegistrationCommand.class);
        userRegistrationCommand.setRole(Role.TENANT_ADMIN);

        userRegistrationCommand.setLocale(locale);
        userRegistrationCommand.setUrl(verifyUrl);

        User user = userCommandUseCase.register(userRegistrationCommand);

        model.addAttribute("successKey", "success.signup.completed");

        return "signup";
    }

    @Data
    private static class UserRegistrationForm {


        @NotBlank
        String firstName;

        @NotBlank
        String lastName;

        @NotBlank
        String password;

        @Email(message = "Email is not valid.")
        String email;


    }
}
