package com.sarkhan.backend.bendisseller.init;

import com.sarkhan.backend.bendisseller.model.User;
import com.sarkhan.backend.bendisseller.model.enums.Role;
import com.sarkhan.backend.bendisseller.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserDbInitializer {
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        User admin = User.builder()
                .fullName("Admin")
                .email("admin1234@gmail.com")
                .password(passwordEncoder.encode("Admin123"))
                .role(Role.ADMIN)
                .build();

        log.info("User created :" + userService.save(admin));
    }
}
