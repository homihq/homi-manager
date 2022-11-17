package com.homihq.manager.core.config;



import com.homihq.manager.core.service.UserCommandUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserCommandUseCase userCommandUseCase;
    private final PasswordEncoder passwordEncoder;

    final CustomAuthenticationEntryPoint customAuthenticationEntryPoint =
            new CustomAuthenticationEntryPoint("/signin");

    final CustomExceptionTranslationFilter customExceptionTranslationFilter =
            new CustomExceptionTranslationFilter(customAuthenticationEntryPoint);

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userCommandUseCase);
        auth.setPasswordEncoder(passwordEncoder);
        return auth;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAfter(customExceptionTranslationFilter, ExceptionTranslationFilter.class)
                .authorizeRequests().antMatchers("/signin","/authenticate","/webjars/**",
                        "/register","/actuator/health", "/","/signup","/verification","/waitlist",
                        "/v2/play/**",
                        "/js/**","/assets/**","/billing/calculator",
                        "/css/**","/sitemap.txt","/robots.txt",
                        "/fonts/**",
                        "/images/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginProcessingUrl("/authenticate")
                .usernameParameter("username").passwordParameter("password")
                .loginPage("/signin").defaultSuccessUrl("/dashboard")
                .permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .logoutRequestMatcher(new AntPathRequestMatcher("/signout"))
                .logoutSuccessUrl("/signin?logout")
                .and().csrf().and().exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint);

        http.headers().frameOptions().sameOrigin();
    }

}
