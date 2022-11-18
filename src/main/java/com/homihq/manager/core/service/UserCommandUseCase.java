package com.homihq.manager.core.service;


import com.homihq.manager.core.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Locale;

public interface UserCommandUseCase extends UserDetailsService {

    User register(UserRegistrationCommand userRegistrationCommand);
    User verify(EmailLinkVerificationCommand emailLinkVerificationCommand);

    @Data
    @AllArgsConstructor
    class EmailLinkVerificationCommand {
        String code;
    }

    @Data
    class UserRegistrationCommand {

        String role;
        String firstName;
        String lastName;
        String company;
        String password;
        String email;

        String url;
        Locale locale;
    }
}
