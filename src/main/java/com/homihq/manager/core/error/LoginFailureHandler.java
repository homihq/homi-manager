package com.homihq.manager.core.error;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private final String REDIRECT_URL = "/signin?error=true";
     
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        log.info("Login failed with error - {}", exception);


        String email = request.getParameter("username");
        String password = request.getParameter("password");

        String url = REDIRECT_URL;

        if (StringUtils.isBlank(email)) {
            url += "&emailError=true";
        }

        if (StringUtils.isBlank(password)) {
            url += "&passwordError=true";
        }

        request.setAttribute("errorKey", "error.signin");
         
        super.setDefaultFailureUrl(url);

        super.onAuthenticationFailure(request, response, exception);
    }
 
}
