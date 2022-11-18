package com.homihq.manager.core.service.impl;


import com.homihq.manager.core.domain.Role;
import com.homihq.manager.core.domain.SimpleUser;
import com.homihq.manager.core.domain.Tenant;
import com.homihq.manager.core.domain.User;
import com.homihq.manager.core.event.EventPublisher;
import com.homihq.manager.core.event.UserRegisteredEvent;
import com.homihq.manager.core.repository.RoleRepository;
import com.homihq.manager.core.repository.TenantRepository;
import com.homihq.manager.core.repository.UserRepository;
import com.homihq.manager.core.service.UserCommandUseCase;
import com.homihq.manager.exception.EmailLinkVerificationFailureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
class UserCommandService implements UserCommandUseCase {

    private final TenantService tenantService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final EventPublisher eventPublisher;

    @Transactional
    @Override
    public User register(UserRegistrationCommand userRegistrationCommand) {

        log.info("User registration command - {}", userRegistrationCommand.getEmail());

        Optional<Role> role = this.roleRepository.findByName(userRegistrationCommand.getRole());

        if(role.isEmpty()) {
            throw new RuntimeException("Role not found.");
        }

        //Tenant tenant = tenantService.save(userRegistrationCommand.getCompany());
        Tenant tenant = new Tenant();
        tenant.setName(userRegistrationCommand.getCompany());
        tenant.setTenantId(UUID.randomUUID().toString());

        log.info("## tenant - {}", tenant);

        User user = User.builder()
                .email(userRegistrationCommand.getEmail())
                .enabled(false)
                .tenant(tenant)
                .firstName(userRegistrationCommand.getFirstName())
                .lastName(userRegistrationCommand.getLastName())
                .verificationToken(UUID.randomUUID().toString())
                .verificationTokenExpiry(LocalDateTime.now().plusDays(1))
                .password(encoder.encode(userRegistrationCommand.getPassword()))
                .role(role.get())
                .build();

        userRepository.save(user);

        try {
            this.tenantService.initDatabase(tenant.getTenantId());
        }
        catch (Exception e) {
            log.error("Error creating tenant db - {}", e);
            throw new RuntimeException("Unable to create tenant database");
        }

        eventPublisher.publish(UserRegisteredEvent.builder()
                .locale(userRegistrationCommand.getLocale())
                .token(user.getVerificationToken())
                .url(userRegistrationCommand.getUrl())
                .user(user)
                .build()
        );

        return user;
    }

    @Transactional
    @Override
    public User verify(EmailLinkVerificationCommand emailLinkVerificationCommand) {
        Optional<User> user =
        userRepository.findByVerificationToken(emailLinkVerificationCommand.getCode());

        if(user.isPresent()) {
            User u = user.get();
            u.setEnabled(true);
            u.setVerificationTokenExpiry(null);
            u.setVerificationToken(null);
            this.userRepository.save(u);
        }
        else{
            throw new EmailLinkVerificationFailureException("Unable to verify.");
        }

        return user.get();
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByEmail(username);
        if(user.isEmpty()) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        log.info("Found user , is account enabled - {}", user.get().isEnabled());

        return new SimpleUser(user.get().getEmail(),
                user.get().getPassword(), user.get().isEnabled(), mapRolesToAuthorities(List.of(user.get().getRole())), user.get());
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

}
